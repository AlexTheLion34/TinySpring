package com.github.alexthelion.usage;

import com.github.alexthelion.usage.component.policeman.Policeman;
import com.github.alexthelion.usage.service.CoronaDisinfector;
import com.github.alexthelion.framework.Application;
import com.github.alexthelion.framework.context.ApplicationContext;
import com.github.alexthelion.usage.component.policeman.impl.AngryPoliceman;
import com.github.alexthelion.usage.model.Room;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = Application.run("com.github.alexthelion.usage",
                new HashMap<>(Map.of(Policeman.class, AngryPoliceman.class)));

        context.getObject(CoronaDisinfector.class)
                .start(new Room());
    }
}
