package com.github.alexthelion.framework.configurator.proxy.impl;

import com.github.alexthelion.framework.configurator.proxy.ProxyConfigurator;
import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class DeprecatedHandlerProxyConfigurator implements ProxyConfigurator {

    @Override
    public Object replaceWithProxyIfNeeded(Object object, Class implClass) {

        if (Arrays.stream(implClass.getMethods()).anyMatch(m -> m.isAnnotationPresent(Deprecated.class))) {

            if (implClass.getInterfaces().length == 0) {
                return Enhancer.create(
                        implClass,
                        (InvocationHandler) (proxy, method, args) -> getInvocationHandlerLogic(object, method, args));
            }

            return Proxy.newProxyInstance(
                    implClass.getClassLoader(),
                    implClass.getInterfaces(),
                    (proxy, method, args) -> getInvocationHandlerLogic(object, method, args));
        }

        return object;
    }

    @SneakyThrows
    private Object getInvocationHandlerLogic(Object object, Method method, Object[] args) {

        if (args != null) {

            Class<?>[] argsTypes = Arrays.stream(args)
                    .map(Object::getClass)
                    .toArray(Class<?>[]::new);

            Method originalMethod = object.getClass()
                    .getMethod(method.getName(), argsTypes);


            beforeInvocationOfMethodLogic(originalMethod, method, argsTypes);

        } else {

            Method originalMethod = object.getClass()
                    .getMethod(method.getName());

            beforeInvocationOfMethodLogic(originalMethod, method);

        }

        return method.invoke(object, args);
    }

    private void beforeInvocationOfMethodLogic(Method originalMethod, Method method, Class<?>[] argsTypes) {

        if (originalMethod.isAnnotationPresent(Deprecated.class)) {
            System.out.println("*********** What the f*ck are you doing??? ***********");
            System.out.printf(
                    "*********** This shit: %s(%s) is deprecated!!!***********%n",
                    method.getName(),
                    Arrays.toString(argsTypes));
        }
    }

    private void beforeInvocationOfMethodLogic(Method originalMethod, Method method) {

        if (originalMethod.isAnnotationPresent(Deprecated.class)) {
            System.out.println("*********** What the f*ck are you doing??? ***********");
            System.out.printf(
                    "*********** This shit: %s() is deprecated!!!***********%n",
                    method.getName());
        }
    }
}
