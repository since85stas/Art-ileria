package com.mygdx.game.util;

import com.mygdx.game.classes.SoundItem;

public class LevelResult {
    boolean isWin;
    float levelTime;
    int correctAnsw;
    int mistakes;
    SoundItem[] sounds;
    SoundItem[] usedSounds;
    int[][] answers;

    public LevelResult (int numSounds, int numAttemps, SoundItem[] sounds, SoundItem[] usedSounds) {
        answers = new int[numSounds][numAttemps];
        for (int i = 0; i < numSounds  ; i++) {
            for (int j = 0; j < numAttemps ; j++) {
                answers[i][j] = -1;
            }
        }
        this.sounds = sounds;
        this.usedSounds = usedSounds;
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

//    public String[] generateOutStrings() {
//        String[] strings= new String[sounds.length];
//
//        for (int i = 0; i < strings.length; i++) {
//            String soundName = sounds[i].getName();
////            String answersStr = getAnswers(i);
////            strings[i] = soundName + "  " + answersStr;
//        }
//
//        return strings;
//    }

    public String[] generateUniqueOutStrings() {
        String[] strings= new String[usedSounds.length];

        for (int i = 0; i < strings.length; i++) {
            int mistakes = 0;
            int attempts = 0;
            String soundName = usedSounds[i].getName();
            for (int j = 0; j < sounds.length; j++) {
                if(soundName == sounds[j].getName()){
                    if (answers[j][0] != -1) {
                        attempts++;
                    }
                    if (answers[j][0] == 0) {
                        mistakes ++;
                    }
                }
            }

            String answersStr = getAnswerString(attempts,mistakes);
            strings[i] = soundName + "  " + answersStr;
        }
        return strings;
    }

    private String getAnswerString (int attempts, int mistakes) {
        String answer = "mistakes " + mistakes + " of " + attempts;
        return answer;
    }

//    private String getAnswers(int iSound) {
//        String answersStr;
//        int correct = 0;
//        int mistakes = 0;
//        for (int i = 0; i < answers[iSound].length; i++) {
//            if (answers[iSound][i] == 1) {
//                correct ++;
//            }  else if (answers[iSound][i] == 0) {
//                mistakes++;
//            }
//        }
//        //float perc = correct/answers[iSound].length * 100;
////        answersStr = String.format("%2f", perc) + "%";
//        answersStr = "mistakes " + String.valueOf(mistakes);
//        return answersStr;
//    }
}

