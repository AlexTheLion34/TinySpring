package com.github.alexthelion.framework;

import com.github.alexthelion.framework.annotations.Singleton;
import com.github.alexthelion.framework.config.Config;
import com.github.alexthelion.framework.config.impl.JavaConfig;
import com.github.alexthelion.framework.context.ApplicationContext;

import java.util.Map;

public class Application {

    public static ApplicationContext run(String packageToScan, Map<Class, Class> interfacesToClasses) {
        Config javaConfig = new JavaConfig(packageToScan, interfacesToClasses);
        ApplicationContext context = new ApplicationContext(javaConfig);
        ObjectFactory objectFactory = new ObjectFactory(context);
        context.setObjectFactory(objectFactory);
        initSingletons(context);
        return context;
    }

    private static void initSingletons(ApplicationContext context) {

        context.getConfig()
                .getClientScanner()
                .getTypesAnnotatedWith(Singleton.class)
                .stream()
                .map(Class::getSuperclass)
                .forEach(context::getObject);
    }
}
