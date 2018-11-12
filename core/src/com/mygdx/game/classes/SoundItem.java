package com.mygdx.game.classes;

public class SoundItem {
    private String name;
    private float  duration;
    private String fileName;

     public SoundItem (String name,String fileName ) {
         this.name = name;
         this.fileName = fileName;
     }


    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public float getDuration() {
        return duration;
    }
}
