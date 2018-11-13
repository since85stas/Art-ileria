package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.classes.SoundItem;

import java.util.ArrayList;
import java.util.List;

public class SoundBase {

    final int filesNumber = 10;

    public SoundBase() {
    }

    //      звуки для игры
    private List<SoundItem> gameSounds = new ArrayList<SoundItem>();

    public SoundItem[] getGameSounds() {
        return gameSounds.toArray( new SoundItem[gameSounds.size()]);
    }

//    public boolean generateGameSounds() {
//
//        // получаем звуки для игры
//        boolean result = generateSoundsBase();
//
//        // проверяем на валидность звуки
//        //boolean valid = checkNumbersForValid ( soundsNumbers );
//
//        if (result ) {
////            for (int i = 0; i < soundsNumbers.length; i++) {
////            }
//        }
//        return false;
//    }

    public boolean generateSoundsBase() {
        boolean result = false;
        String[] fileNames = getFileNames();
        String[] soundNames = getSoundNames();

        if ((fileNames.length == soundNames.length) &&(fileNames.length == filesNumber)) {
            for (int i = 0; i < 10; i++) {
                SoundItem soundItem = new SoundItem(soundNames[i],fileNames[i],i);
                gameSounds.add(soundItem);
            }
            return true;
        }  else {
            Gdx.app.log("SoundBase","error in names");
            return false;
        }
    }

    private String[] getFileNames() {
        //String[] fileNames = new String[filesNumber];
        String[] fileNames = {"c1pno0100.mp3", "c1pno0200.mp3", "c1pno0300.mp3", "c1pno0400.mp3",
                "c1pno0500.mp3", "c1pno0600.mp3", "c1pno0700.mp3", "c1pno0800.mp3", "c1pno0900.mp3", "c1pno01000.mp3"};
        return fileNames;
    }

    private String[] getSoundNames() {
        String[] soundNames = {"m2","b2","m3","b3","4p","3t","5p","m6","b6","m7"};
        return soundNames;
    }

    public SoundItem[] getGameSoundSequence(int[] soundsNumbers ) {
        SoundItem[] sounds = new SoundItem[soundsNumbers.length];
        for (int i = 0; i < soundsNumbers.length ; i++) {
            SoundItem sound = getSoundItemFromNumber(soundsNumbers[i]);
            if (sound!=null) {
                sounds[i] = sound;
            }  else {
                return null;
            }
        }
        return sounds;
    }

    private SoundItem getSoundItemFromNumber(int number) {
        for (int i = 0; i < gameSounds.size() ; i++) {
            if (gameSounds.get(i).getNumber() == number) return gameSounds.get(i);
        }
        return null;
    }

    private boolean checkNumbersForValid(int[] sounds) {
        boolean valid = true;
        for (int i = 0; i < sounds.length; i++) {

        }

        return valid;
    }
}
