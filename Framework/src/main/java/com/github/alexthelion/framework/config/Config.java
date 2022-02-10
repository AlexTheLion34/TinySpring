package com.github.alexthelion.framework.config;

import org.reflections.Reflections;

public interface Config {
    <T> Class<? extends T> getImplClass(Class<T> type);

    Reflections getFrameworkScanner();

    Reflections getClientScanner();
}
