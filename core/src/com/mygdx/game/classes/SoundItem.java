package com.mygdx.game.classes;

public class SoundItem {
    private String name;
    private float  duration;
    private String fileName;

     public SoundItem (String name) {
         this.name = name;
     }

    public String getName() {
        return name;
    }

    public float getDuration() {
        return duration;
    }
}
