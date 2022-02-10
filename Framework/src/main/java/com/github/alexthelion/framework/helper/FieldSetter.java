package com.github.alexthelion.framework.helper;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public interface FieldSetter {

    @SneakyThrows
    default <T, V> void setFieldForObject(T object, Field field, V value) {
        field.setAccessible(true);
        field.set(object, value);
    }
}
