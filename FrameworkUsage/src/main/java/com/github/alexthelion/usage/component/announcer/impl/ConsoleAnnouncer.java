package com.github.alexthelion.usage.component.announcer.impl;

import com.github.alexthelion.framework.annotations.InjectByType;
import com.github.alexthelion.framework.annotations.Singleton;
import com.github.alexthelion.usage.component.announcer.Announcer;
import com.github.alexthelion.usage.component.recomender.Recommender;

@Singleton
public class ConsoleAnnouncer implements Announcer {

    public ConsoleAnnouncer() {
        System.out.println("ConsoleAnnouncer was created");
    }

    @InjectByType
    private Recommender recommender;

    @Override
    @Deprecated
    public void announce(String announcement) {
        System.out.println(announcement);
        recommender.recommend();
    }
}
