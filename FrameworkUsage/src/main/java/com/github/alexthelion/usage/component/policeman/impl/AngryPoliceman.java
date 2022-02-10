package com.github.alexthelion.usage.component.policeman.impl;

import com.github.alexthelion.framework.annotations.InjectByType;
import com.github.alexthelion.framework.annotations.Singleton;
import com.github.alexthelion.usage.component.policeman.Policeman;
import com.github.alexthelion.usage.component.recomender.Recommender;

import javax.annotation.PostConstruct;

@Singleton
public class AngryPoliceman implements Policeman {

    public AngryPoliceman() {
        System.out.println("AngryPoliceman was created");
    }

    @InjectByType
    private Recommender recommender;

    @PostConstruct
    public void init() {
        System.out.println(recommender.getClass());
    }

    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("I'll kill everyone! GET OUT!!!");
    }
}
