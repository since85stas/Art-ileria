package com.mygdx.game.entites;

import com.mygdx.game.classes.SoundItem;
import com.mygdx.game.util.LevelResult;

public class SoundSequence {
    private SoundItem[] sounds;
    private int numAttempts;
    private int i;  // текущий номер звука
    public LevelResult result;

    public SoundSequence (SoundItem[] sounds, SoundItem[] usedSounds, int numAttempts) {
        this.sounds = sounds;
//        result = new LevelResult(sounds.length,numAttempts,sounds, usedSounds);
    }

    public void playSound (int i) {
        this.i = i;
        if (i < sounds.length-1) {
            sounds[i].playSound();
        }
    }

    public void playCurrent() {
        sounds[i].playSound();
    }

    public boolean playNext() {
        if (i < sounds.length-1) {
            sounds[i].stopSound();
            i++;
            sounds[i].playSound();
            return true;
        } else return false;
    }

    // результаты уровня
//    public void answerCorrect (int iAttempt) {
//        result.setAnswer(i,iAttempt,true);
//    }
//
//    public void answerFalse(int iAttempt) {
//        result.setAnswer(i,iAttempt,false);
//    }

    public void addTime(float dt) {
        result.addTime(dt);
    }

    public SoundItem getCurrentSound() {
        return sounds[i];
    }

    public int getLength() {
        return sounds.length;
    }

    public int getI() {
        return i;
    }
}
