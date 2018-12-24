package com.mygdx.game.util;

/**
 * Created by seeyo on 11.12.2018.
 */

public class LevelParameters {
    private int numSounds;
    private float soundDuration;
    private int numLines;

    private int gameTime;

    public int getNumSounds() {
        return numSounds;
    }

    public float getSoundDuration() {
        return soundDuration;
    }

    public int getNumLines() {
        return numLines;
    }

    public void setNumSounds(int numSounds) {
        this.numSounds = numSounds;
    }

    public void setSoundDuration(float soundDuration) {
        this.soundDuration = soundDuration;
    }

    public void setNumLines(int timeDelay) {
        this.numLines = timeDelay;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }
}
