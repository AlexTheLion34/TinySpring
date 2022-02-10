package com.github.alexthelion.framework.context;

import com.github.alexthelion.framework.ObjectFactory;
import com.github.alexthelion.framework.config.Config;
import com.github.alexthelion.framework.annotations.Singleton;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    @Setter
    private ObjectFactory objectFactory;

    private final Map<Class, Object> cache = new ConcurrentHashMap<>();

    @Getter
    private final Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public <T> T getObject(Class<T> type) {

        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        Class<? extends T> implClass = type;

        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            implClass = config.getImplClass(type);
        }

        T object = objectFactory.createObject(implClass);

        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, object);
        }

        return object;
    }
}
