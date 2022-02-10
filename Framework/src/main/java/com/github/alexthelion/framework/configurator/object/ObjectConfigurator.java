package com.github.alexthelion.framework.configurator.object;

import com.github.alexthelion.framework.context.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object object, ApplicationContext applicationContext);
}
