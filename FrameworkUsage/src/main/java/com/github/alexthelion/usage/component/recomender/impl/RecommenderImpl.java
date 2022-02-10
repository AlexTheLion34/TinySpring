package com.github.alexthelion.usage.component.recomender.impl;

import com.github.alexthelion.framework.annotations.InjectProperty;
import com.github.alexthelion.framework.annotations.Singleton;
import com.github.alexthelion.usage.component.recomender.Recommender;

@Singleton
public class RecommenderImpl implements Recommender {

    public RecommenderImpl() {
        System.out.println("Recommender was created");
    }

    @InjectProperty(value = "whisky")
    private String alcohol;

    @Deprecated
    @Override
    public void recommend() {
        System.out.printf("To protect from covid-19, drink %s%n", alcohol);
    }
}
