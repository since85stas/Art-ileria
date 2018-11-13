package com.mygdx.game.classes;

public class SoundItem {
    private String name;
    private float  duration;
    private String fileName;
    private int    number;

     public SoundItem (String name,String fileName, int number ) {
         this.name = name;
         this.fileName = fileName;
         this.number   = number;
     }


    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public int getNumber() {
        return number;
    }

    public float getDuration() {
        return duration;
    }
}
