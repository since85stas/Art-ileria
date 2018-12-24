package com.mygdx.game.util;

import com.mygdx.game.classes.SoundItem;

import java.util.ArrayList;
import java.util.List;

public class LevelResult {
    boolean isWin;
    float   levelTime;
    int     correctAnsw;
    int     mistakes;
    List<SoundItem> sounds;
    SoundItem[] usedSounds;
    List<Integer> answers;

    public LevelResult ( SoundItem[] usedSounds) {

        this.usedSounds = usedSounds;
        sounds = new ArrayList<SoundItem>();
        answers = new ArrayList<Integer>();
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

    public void setAnswer ( boolean result, SoundItem soundItem ) {
        if (result){
            answers.add(1);
            sounds.add(soundItem);
        } else {
            answers.add(0);
            sounds.add(soundItem);
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
            for (int j = 0; j < sounds.size(); j++) {
                if(soundName == sounds.get(j).getName()){
                    if (answers.get(j) != -1) {
                        attempts++;
                    }
                    if (answers.get(j) == 0) {
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

