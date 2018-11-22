package com.mygdx.game.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundItem {
    private String name;
    private String fileName;
    private int    number;
    private Sound sound;

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

    public String getFilePath() {
         return "sounds/" + fileName;
    }

    public int getNumber() {
        return number;
    }

//    public float getDuration() {
//        return duration;
//    }

    public void playSound () {
         sound = Gdx.audio.newSound(Gdx.files.internal(this.getFilePath()));
         sound.play();
    }

    public void stopSound( ) {
         if (sound!=null) {
            sound.stop();
         }
    }


}
