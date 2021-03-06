package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ArtGame;
import com.mygdx.game.classes.SoundItem;
import com.mygdx.game.entites.Cannon;
import com.mygdx.game.entites.SoundSequence;
import com.mygdx.game.overlays.GameScreenHud;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.SoundBase;

public class GameScreen implements Screen {

    private static final String TAG = GameScreen.class.getName();

    private float accumulator = 0;

    private ArtGame game;
    private  SpriteBatch batch ;
    private GameScreenHud hud;

    // параметры уровня
    private int numSounds;     // число возможных звуков
    private SoundItem[] usedSounds ; // число используемых звуков
    private int[]  gameSoundsNumbers; // номера звуков уровня
    private SoundItem soundsSequence[];      // последовательность звуков уровня
    private int numAttempts;  // число попыток на каждый звук
    private float timeOfGame;
    private float durationOfAttempt;
    private int lives;
    private float stageDuration = 2;
    private SoundSequence sequence;

    // переменные уровня
    int scores ;
    int currentAttempt;
    float gameTime;
    float stageTime;
    float pauseTime;
    float attemptTime;
    private boolean isStart;
    private SoundItem currentSound; // играющий в данный момент звук
    boolean isEnd = false; // конец последовательности
    private Cannon clickedCannon; // выбранная пушка

    boolean clickResult ;

    // screen dimensions
    private int width ;
    private int height;

    // положения пушек
    Vector2[] cannonsCoord;
    Cannon[]  cannons;

    // положение позиций
    Vector2[] positionCoord;

    // Add ScreenViewport for HUD
    ScreenViewport hudViewport;



    // экземпляр со звуками уровня
    SoundBase soundBase;

    public GameScreen(
                       int numSounds,
                       int[] gameSoundsNumbers,
                       float timeOfGame,
                       float durationOfAttempt,
                       int numAttempts,
                       int lives){
        batch = new SpriteBatch();
        hud = new GameScreenHud();
		this.numSounds  = numSounds;
		this.gameSoundsNumbers = gameSoundsNumbers;
		this.timeOfGame = timeOfGame;
		this.durationOfAttempt = durationOfAttempt;
		this.numAttempts = numAttempts;
		this.lives      = lives;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		cannonsCoord = new Vector2[numSounds];
		cannons      = new Cannon[numSounds];
		positionCoord = new Vector2[numAttempts];
		getInitialCoordinates();
		getSounds();
        sequence =new SoundSequence(soundsSequence);



        isStart = false;
	}

	private void getSounds () {
        soundBase = new SoundBase();
        boolean result = soundBase.generateSoundsBase();
        if (result) {
            usedSounds = soundBase.getGameSounds();
            soundsSequence = soundBase.getGameSoundSequence(gameSoundsNumbers);
        }
    }

    private void getInitialCoordinates() {
	    cannonsCoord[0] = new Vector2(Constants.CANNON_LATERAL_MARGIN,Constants.CANNON_DOWN_MARGIN);
	    float cannonsWidth = (width-2*Constants.CANNON_LATERAL_MARGIN - Constants.SOURCE_SIZE_X)/(numSounds+1);
        for (int i = 1; i < numSounds ; i++) {
            cannonsCoord[i] = new Vector2( cannonsCoord[0].x + cannonsWidth*i
                    , Constants.CANNON_DOWN_MARGIN);
        }

        for (int i = 0; i < numAttempts; i++) {
            positionCoord[i] = new Vector2(width/2,height/ numAttempts *(i+1));
        }
    }

    @Override
    public void show() {
        // Initialize the HUD viewport
        hudViewport = new ScreenViewport();

        // определяем пушки
        for (int i = 0; i < numSounds ; i++) {
            cannons[i] = new Cannon(this,cannonsCoord[i], usedSounds[i]);
        }

        sequence.playSound(0);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 1, 0.6f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;

        batch.begin();

        for (int i = 0; i < numSounds; i++) {
            cannons[i].render(batch,delta);
        }

        float fps = 1 / delta;
        Gdx.app.log(TAG,"fps =" + fps);

        // рисуем hud
//        hudFont.draw(batch,"time  " + String.format("%.2f", durationOfAttempt - attemptTime),
//                Constants.HUD_MARGIN,height-Constants.HUD_MARGIN);
//        hudFont.draw(batch,"lives " + lives,width/2,height-Constants.HUD_MARGIN);
//        hudFont.draw(batch,"scores " + scores,width/2 + 90,height-Constants.HUD_MARGIN);

        drawSoundSource();
        drawHint();

        if (!isEnd) {
            update(delta);
        } else {
            float x = width  / 2 - 120;
            float y = height /2 - 40 ;
//            resultFont.draw(batch,"End of sounds ", x,y);
        }

        hud.render(batch);

        batch.end();
    }

    private void drawSoundSource() {
	    float xSource = cannonsCoord[numSounds-1].x + (width - cannonsCoord[numSounds-1].x) / 2 ;
//        hudFont.draw(batch,"sound " + sequence.getCurrentSound().getName(),
//                xSource,Constants.CANNON_DOWN_MARGIN + Constants.SOURCE_SIZE_Y/2);
    }

    private void drawHint() {
        float x = width  / 2 - 120;
        float y = height /2 ;
//        hudFont.draw(batch,"sound " + sequence.getCurrentSound().getNumber()+ "  " + "attempt" + (currentAttempt + 1),
//                x,y);
    }

    public void update (float dt) {

        // время игры
        gameTime +=dt;

        if (pauseTime < Constants.STAGE_PAUSE_TIME) {
            pauseTime += dt;
            clickedCannon = null;
        } else  {
            if(attemptTime < durationOfAttempt) {
                attemptTime += dt;
            } else {
                goToNextAttempt();
            }
        }

        Gdx.input.setInputProcessor (new InputAdapter() {
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                clickedCannon =  checkClickEvent(screenX,screenY);
                if (clickedCannon != null) {
                    if (clickedCannon.getSound() == sequence.getCurrentSound()) {
                        clickResult = true;
                        Gdx.app.log("check click", "true");
                        scores++;
                        goToNextAttempt();
                    } else {
                        clickResult = false;
                        lives--;
                        goToNextAttempt();
                        Gdx.app.log("check click", "false");
                    }
                }
                return false;
            }
        });

        if (clickedCannon != null) {
            float x = width / 2 - 120;
            float y = height / 2 - 20;
//            resultFont.draw(batch, " " + clickResult, x, y);
        }
    }

    private void goToNextAttempt() {
        currentAttempt++;
        attemptTime = 0;
        pauseTime = 0;
        if (currentAttempt >= numAttempts) {
            currentAttempt = 0;
            isEnd = !sequence.playNext();
        } else {
            sequence.playCurrent();
        }
    }

    private Cannon checkClickEvent(int screenX, int screenY) {
        Cannon clickPosition = null;
        for (int i = 0; i < cannons.length ; i++) {
            if(cannons[i].getHitBox().contains(screenX,height - screenY)) {
                clickPosition = cannons[i];
                break;
            }
        }
        return clickPosition;
    }
	
	@Override
	public void dispose () {
		batch.dispose();
//        hudFont.dispose();
//        resultFont.dispose();
        //soundInterval.dispose();
	}

    @Override
    public void hide() {
        batch.dispose();
//        hudFont.dispose();
//        resultFont.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        // Update HUD viewport
        //hudViewport.update(width, height, true);

        // Set font scale to min(width, height) / reference screen size
        //font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);
    }



    public int getNumSounds() {
        return numSounds;
    }
}
