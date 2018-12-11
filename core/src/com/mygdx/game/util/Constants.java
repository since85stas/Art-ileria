package com.mygdx.game.util;

public class Constants {

    public Constants() {

    }

    // константы для настроек
    public static final String PREF_FILE_NAME = "levelPref";
    public static final String PREF_NUM_SOUNDS = "numSounds";
    public static final String PREF_SOUND_DURATIN = "durationSound";
    public static final String PREF_BETWEEN_DELAY = "betweenDelay";

    // константы для экрана предзагрузки
    public static final float HUD_PRE_UP_RATIO   = 0.02f;
    public static final float HUD_PRE_ITEM_SIZE   = 0.06f;

    // константы игрового экрана
    public static final float HUD_MARGIN_UP_RATIO   = 0.02f;
    public static final float HUD_MARGIN_DOWN_RATIO   = 0.02f;
    public static final float CONTROLS_HEIGHT_RATIO  = 0.2f;
    public static final float HUD_UP_SIZE  = 0.08f;
    public static final float HUD_MAIN_TEXT = 0.04f;
    public static final float HUD_BIG_TEXT = 0.1f;

    // константы экрана вывода результатов
    public static final float END_HUD_MARGIN   = 40.f;


    // константы пушек
    public static final float CANNON_DOWN_MARGIN = 30.f;
    public static final float CANNON_LATERAL_MARGIN = 20.f;
    public static final float TEXT_RATIO = 0.3f;

    // константы источника
    public static final float SOURCE_SIZE_X    = 40.f;
    public static final float SOURCE_SIZE_Y    = 40.f;

    // ограничение на шаг по времени
    public static final float  TIME_STEP = 0.01f;


    // константы для игры
    public static final float STAGE_PRELOAD_TIME = 3f;

    //константы для текстур пушек
    public static final float CANNON_WIDTH = 64;
    public static final float CANNON_HEIGHT = 64;

    // константы для текста
    public static final float BLINKING_PERIOD = 2.f;

    // константы для анимации Enemy
    public static final float ENEMY_SIZE  = 0.12f ;
    public static final float ENEMY_X_POSIT = 0.1f;
    public static final float ENEMY_Y_POSIT = 50.f;
    public static final float  WALK_LOOP_DURATION = 0.025f;
}
