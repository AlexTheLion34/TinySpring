package com.github.alexthelion.framework;

import com.github.alexthelion.framework.configurator.object.ObjectConfigurator;
import com.github.alexthelion.framework.configurator.proxy.ProxyConfigurator;
import com.github.alexthelion.framework.context.ApplicationContext;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.*;

public class ObjectFactory {

    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    private final ApplicationContext context;

    public ObjectFactory(ApplicationContext context) {

        this.context = context;

        context.getConfig()
                .getFrameworkScanner()
                .getSubTypesOf(ObjectConfigurator.class)
                .stream()
                .map(this::create)
                .forEach(objectConfigurators::add);

        context.getConfig()
                .getFrameworkScanner()
                .getSubTypesOf(ProxyConfigurator.class)
                .stream()
                .map(this::create)
                .forEach(proxyConfigurators::add);
    }

    public <T> T createObject(Class<T> implClass) {

        T object = create(implClass);

        configure(object);

        invokeInit(object);

        object = wrapWithProxyIfNeeded(object, implClass);

        return object;
    }


    @SneakyThrows
    private <T> T create(Class<T> implClass) {
        return implClass.getDeclaredConstructor().newInstance();
    }

    private <T> void configure(T object) {
        objectConfigurators.forEach(objectConfigurator -> objectConfigurator.configure(object, context));
    }

    private <T> T wrapWithProxyIfNeeded(T object, Class<T> implClass) {

        for (ProxyConfigurator proxyConfigurator: proxyConfigurators)
            object = (T) proxyConfigurator.replaceWithProxyIfNeeded(object, implClass);

        return object;
    }

    private <T> void invokeInit(T object) {
        Arrays.stream(object.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(PostConstruct.class))
                .forEach(m -> invokeMethod(m, object));
    }

    @SneakyThrows
    private <T> void invokeMethod(Method method, T object) {
        method.invoke(object);
    }
}
