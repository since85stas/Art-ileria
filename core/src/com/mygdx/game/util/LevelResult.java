package com.mygdx.game.util;

import com.mygdx.game.classes.SoundItem;

public class LevelResult {
    boolean isWin;
    float levelTime;
    int correctAnsw;
    int mistakes;
    SoundItem[] sounds;
    int[][] answers;

    public LevelResult (int numSounds, int numAttemps, SoundItem[] sounds) {
        answers = new int[numSounds][numAttemps];
        for (int i = 0; i < numSounds  ; i++) {
            for (int j = 0; j < numAttemps ; j++) {
                answers[i][j] = -1;
            }
        }
        this.sounds = sounds;
    }

    private void initArray() {

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
        if (result){
            answers[iNum][iAttemp] = 1;
        } else {
            answers[iNum][iAttemp] = 0;
        }
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
        int mistakes = 0;
        for (int i = 0; i < answers[iSound].length; i++) {
            if (answers[iSound][i] == 1) {
                correct ++;
            }  else if (answers[iSound][i] == 0) {
                mistakes++;
            }
        }
        //float perc = correct/answers[iSound].length * 100;
//        answersStr = String.format("%2f", perc) + "%";
        answersStr = "mistakes " + String.valueOf(mistakes);
        return answersStr;
    }
}

