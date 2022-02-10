package com.github.alexthelion.framework.configurator.object.impl;

import com.github.alexthelion.framework.annotations.InjectByType;
import com.github.alexthelion.framework.configurator.object.ObjectConfigurator;
import com.github.alexthelion.framework.context.ApplicationContext;
import com.github.alexthelion.framework.helper.FieldSetter;

import java.util.Arrays;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator, FieldSetter {

    @Override
    public void configure(Object object, ApplicationContext context) {
        Arrays.stream(object.getClass()
                .getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(InjectByType.class))
                .forEach(f -> {
                    Object createdObject = context.getObject(f.getType());
                    setFieldForObject(object, f, createdObject);
                });
    }
}
