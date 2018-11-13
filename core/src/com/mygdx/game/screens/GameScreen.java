package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
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
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.SoundBase;

public class GameScreen implements Screen {

    private static final String TAG = GameScreen.class.getName();

    private float accumulator = 0;

    private ArtGame game;
    private  SpriteBatch batch ;

    // параметры уровня
    private int numSounds;     // число возможных звуков
    private SoundItem[] usedSounds ; // число используемых звуков
    private int[]  gameSoundsNumbers; // номера звуков уровня
    private SoundItem soundsSequence[];      // последовательность звуков уровня
    private int numSteps;
    private float timeOfGame;
    private float timeOfStep;
    private int lives;

    // переменные уровня
    float gameTime;

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

    // Add BitmapFont
    BitmapFont font;
    BitmapFont font24;

    // экземпляр со звуками уровня
    SoundBase soundBase;

	public GameScreen( SpriteBatch batch,
                       int numSounds,
                       int[] gameSoundsNumbers,
                       float timeOfGame,
                       float timeOfStep,
                       int numSteps,
                       int lives){
		this.batch      = batch ;
		this.numSounds  = numSounds;
		this.gameSoundsNumbers = gameSoundsNumbers;
		this.timeOfGame = timeOfGame;
		this.timeOfStep = timeOfStep;
		this.numSteps   = numSteps;
		this.lives      = lives;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		cannonsCoord = new Vector2[numSounds];
		cannons      = new Cannon[numSounds];
		positionCoord = new Vector2[numSteps];
		getInitialCoordinates();
		getSounds();

        // шрифт для заголовка
        generateFont24();
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

        for (int i = 0; i < numSteps; i++) {
            positionCoord[i] = new Vector2(width/2,height/numSteps*(i+1));
        }
    }

    @Override
    public void show() {
        // Initialize the HUD viewport
        hudViewport = new ScreenViewport();

        //  Initialize the BitmapFont
        font = new BitmapFont();

        // определяем пушки
        for (int i = 0; i < numSounds ; i++) {
            cannons[i] = new Cannon(this,cannonsCoord[i], usedSounds[i]);
        }
    }

    @Override
    public void render(float delta) {
        // время игры
	    gameTime +=delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;

        batch.begin();

        for (int i = 0; i < numSounds; i++) {
            cannons[i].render(batch,delta);
        }

        // Draw the number of player deaths in the top left
        font.setColor(Color.CYAN);

        float fps = 1 / delta;
        Gdx.app.log(TAG,"fps =" + fps);

        // рисуем hud
        font24.draw(batch,"time " + gameTime,Constants.HUD_MARGIN,height-Constants.HUD_MARGIN);
        font24.draw(batch,"lives " + lives,width/2,height-Constants.HUD_MARGIN);

        drawSoundSource();

        batch.end();
    }

    private void drawSoundSource() {
	    float xSource = cannonsCoord[numSounds-1].x + (width - cannonsCoord[numSounds-1].x) / 2 ;
        font24.draw(batch,"sound", xSource,Constants.CANNON_DOWN_MARGIN + Constants.SOURCE_SIZE_Y/2);
    }

    private void playSound(float duration, SoundItem soundItem ) {

    }

    public void update (float dt) {

    }
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}

    @Override
    public void hide() {
        batch.dispose();
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

    private void generateFont24() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;
        font24 = generator.generateFont(parameter);
    }

    public int getNumSounds() {
        return numSounds;
    }
}
