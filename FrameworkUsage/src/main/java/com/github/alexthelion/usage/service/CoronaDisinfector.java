package com.github.alexthelion.usage.service;

import com.github.alexthelion.framework.annotations.InjectByType;
import com.github.alexthelion.framework.annotations.Singleton;
import com.github.alexthelion.usage.component.announcer.Announcer;
import com.github.alexthelion.usage.component.policeman.Policeman;
import com.github.alexthelion.usage.model.Room;

@Singleton
public class CoronaDisinfector {

    public CoronaDisinfector() {
        System.out.println("CoronaDisinfector was created");
    }

    @InjectByType
    private Announcer announcer;

    @InjectByType
    private Policeman policeman ;

    public void start(Room room) {
        announcer.announce("Start disinfection");
        policeman.makePeopleLeaveRoom();
        disinfect(room);
        announcer.announce("Try go back to the room");
    }

    private void disinfect(Room room) {
        System.out.println("Room is being disinfected...");
    }
}
