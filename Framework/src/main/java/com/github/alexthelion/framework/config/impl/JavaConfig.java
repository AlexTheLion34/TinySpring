package com.github.alexthelion.framework.config.impl;

import com.github.alexthelion.framework.config.Config;
import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {

    @Getter
    private final Reflections frameworkScanner;

    @Getter
    private final Reflections clientScanner;

    private final Map<Class, Class> interfacesToClasses;

    private static final String FRAMEWORK_BASE_PACKAGE = "com.github.alexthelion.framework";

    public JavaConfig(String packageToScan, Map<Class, Class> interfacesToClasses) {
        this.frameworkScanner = new Reflections(FRAMEWORK_BASE_PACKAGE);
        this.clientScanner = new Reflections(packageToScan);
        this.interfacesToClasses = interfacesToClasses;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> type) {

        return interfacesToClasses.computeIfAbsent(type, aClass -> {

            Set<Class<? extends T>> classes = clientScanner.getSubTypesOf(type);

            if (classes.size() != 1) {
                throw new RuntimeException(String.format("%s has 0 or more then 1 implementations or subclasses, " +
                        "please update your config", type));
            }

            return classes.iterator().next();
        });
    }
}
