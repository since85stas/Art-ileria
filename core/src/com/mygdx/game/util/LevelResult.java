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

    public boolean isWin() {
        return isWin;
    }

    public void addTime(float dt) {
        levelTime++;
    }

    public void setAnswer ( int iNum, int iAttemp, boolean result ) {
        answers[iNum][iAttemp] = result;
    }

    public String[] generateOutStrings() {
        String[] strings= new String[sounds.length];

        for (int i = 0; i < strings.length; i++) {
            String soundName = sounds[i].getName();
            String answersStr = getAnswers(i);
            strings[i] = soundName + "  " + answersStr;
        }

        return strings;
    }

    private String getAnswers(int iSound) {
        String answersStr;
        int correct = 0;
        for (int i = 0; i < answers[iSound].length; i++) {
            if (answers[iSound][0]) {
                return "100%";
            }  else {
                if (answers[iSound][i]) {
                    correct++;
                }
            }
        }
        float perc = correct/answers[iSound].length * 100;
        answersStr = String.format("%2f", perc) + "%";
        return answersStr;
    }
}

