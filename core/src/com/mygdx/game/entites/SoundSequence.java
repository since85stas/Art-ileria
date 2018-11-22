package com.mygdx.game.entites;

import com.mygdx.game.classes.SoundItem;

public class SoundSequence {
    private SoundItem[] sounds;
    private int i;  // текущий номер звука

    public SoundSequence (SoundItem[] sounds) {
        this.sounds = sounds;
    }

    public void playSound (int i) {
        this.i = i;
        if (i < sounds.length-1) {
            sounds[i].playSound();
        }
    }

    public boolean playNext() {
        if (i < sounds.length-1) {
            sounds[i].stopSound();
            i++;
            sounds[i].playSound();
            return true;
        } else return false;
    }

    public SoundItem getCurrentSound() {
        return sounds[i];
    }

    public int getLength() {
        return sounds.length;
    }



}
