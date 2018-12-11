package com.mygdx.game.util;

/**
 * Created by seeyo on 11.12.2018.
 */

public class LevelParameters {
    private int numSounds;
    private float soundDuration;
    private float timeDelay;

    public int getNumSounds() {
        return numSounds;
    }

    public float getSoundDuration() {
        return soundDuration;
    }

    public float getTimeDelay() {
        return timeDelay;
    }

    public void setNumSounds(int numSounds) {
        this.numSounds = numSounds;
    }

    public void setSoundDuration(float soundDuration) {
        this.soundDuration = soundDuration;
    }

    public void setTimeDelay(float timeDelay) {
        this.timeDelay = timeDelay;
    }
}
