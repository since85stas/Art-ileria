package com.mygdx.game.util;

import com.mygdx.game.classes.SoundItem;

public class LevelResult {
    boolean isWin;
    float levelTime;
    int correctAnsw;
    int mistakes;
    SoundItem[] sounds;
    boolean[][] answers;

    public LevelResult (int numSounds, int numAttemps, SoundItem[] sounds) {
        answers = new boolean[numSounds][numAttemps];
        this.sounds = sounds;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public void addTime(float dt) {
        levelTime++;
    }

    public void setAnswer ( int iNum, int iAttemp, boolean result ) {
        answers[iNum][iAttemp] = result;
    }
}
