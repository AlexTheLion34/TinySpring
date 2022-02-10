package com.github.alexthelion.framework.configurator.object.impl;

import com.github.alexthelion.framework.annotations.InjectProperty;
import com.github.alexthelion.framework.configurator.object.ObjectConfigurator;
import com.github.alexthelion.framework.context.ApplicationContext;
import com.github.alexthelion.framework.helper.FieldSetter;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator, FieldSetter {

    private static final String PROPERTIES_SOURCE = "application.properties";

    private final Map<String, String> propertiesMap;

    public InjectPropertyAnnotationObjectConfigurator() {
        this.propertiesMap = readProperties();
    }

    @Override
    public void configure(Object object, ApplicationContext context) {

        Class<?> implClass = object.getClass();

        Arrays.stream(implClass.getDeclaredFields())
                .filter(f -> f.getAnnotation(InjectProperty.class) != null)
                .forEach(f -> {

                    InjectProperty annotation = f.getAnnotation(InjectProperty.class);

                    String value = annotation.value()
                            .isEmpty() ?
                            propertiesMap.get(f.getName()) :
                            propertiesMap.get(annotation.value());

                    setFieldForObject(object, f, value);
                });
    }

    @SneakyThrows
    private Map<String, String> readProperties() {

        String path = Objects.requireNonNull(ClassLoader.getSystemClassLoader()
                        .getResource(PROPERTIES_SOURCE))
                .getPath();

        if (path.startsWith("/")) {
            path = path.substring(1); // Bug in Windows 10
        }

        return Files.lines(Paths.get(path))
                .map(line -> line.split("="))
                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }
}
