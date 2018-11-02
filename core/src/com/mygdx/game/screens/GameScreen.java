package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ArtGame;
import com.mygdx.game.classes.SoundItem;
import com.mygdx.game.entites.Cannon;
import com.mygdx.game.util.Constants;

public class GameScreen implements Screen {

    private static final String TAG = GameScreen.class.getName();

    private float accumulator = 0;

    private ArtGame game;
    private  SpriteBatch batch ;

    // параметры уровня
    int numSounds;
    SoundItem sounds[];
    int numSteps;
    float timeOfGame;
    float timeOfStep;

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
    BitmapFont font48;

	public GameScreen( SpriteBatch batch, int numSounds, float timeOfGame, float timeOfStep, int numSteps){
		this.batch      = batch ;
		this.numSounds  = numSounds;
		this.timeOfGame = timeOfGame;
		this.timeOfStep = timeOfStep;
		this.numSteps   = numSteps;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		cannonsCoord = new Vector2[numSounds];
		cannons      = new Cannon[numSounds];
		sounds       = new SoundItem[numSounds];
		positionCoord = new Vector2[numSteps];
		getInitialCoordinates();
		getSounds();
	}

	private void getSounds () {
        for (int i = 0; i < numSounds ; i++) {
            sounds[i] = new SoundItem("Sound " + i);
        }
    }

    private void getInitialCoordinates() {
        for (int i = 0; i < numSounds ; i++) {
            cannonsCoord[i] = new Vector2(width/(numSounds+1)*(i+1),Constants.CANNON_DOWN_MARGIN);
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
            cannons[i] = new Cannon(this,cannonsCoord[i],sounds[i]);
        }
    }



    @Override
    public void render(float delta) {

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
//        cannons[0].render(batch,delta);


        // Draw the number of player deaths in the top left
        font.setColor(Color.CYAN);

        float fps = 1 / delta;
        Gdx.app.log(TAG,"fps =" + fps);

//        font48.draw(batch,  "Text", width/2 , height/2);

        batch.end();
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

    public int getNumSounds() {
        return numSounds;
    }
}
