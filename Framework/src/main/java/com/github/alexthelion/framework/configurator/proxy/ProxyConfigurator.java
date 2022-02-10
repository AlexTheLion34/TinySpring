package com.github.alexthelion.framework.configurator.proxy;

public interface ProxyConfigurator {
    Object replaceWithProxyIfNeeded(Object object, Class implClass);
}
