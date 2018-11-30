package com.mygdx.game.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import java.util.Comparator;

public class SoundItem {
    private String name;
    private String fileName;
    private int    number;
    private Sound sound;
    private AssetManager assetManager;

    public SoundItem() {

    }

     public SoundItem (String name,String fileName, int number ) {
         this.name = name;
         this.fileName = fileName;
         this.number   = number;
         assetManager = new AssetManager();
         assetManager.load(this.getFilePath(), Sound.class);
         assetManager.finishLoading(); //Important!
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

    public boolean playSound () {
         if (assetManager.isLoaded(this.getFilePath(),Sound.class)) {
             sound = assetManager.get(this.getFilePath(), Sound.class);
             sound.play();
             return true;
         }  else {
             Gdx.app.log("soundItem","error loading sound");
             return false;
         }
    }

    public void stopSound( ) {
         if (sound!=null) {
            sound.stop();
         }
    }

}
