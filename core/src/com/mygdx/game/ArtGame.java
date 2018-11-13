package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.screens.GameScreen;

public class ArtGame extends Game {
	SpriteBatch batch;
	GameScreen gameScreen;
	FitViewport viewport;

	// временные параметры
	int numSounds    = 10;
	int[] soundSequence = {0,1,2,3,4,5,6,7,8,9};
	float durationOfGame = 20.f;
	float durationOfStep = 2.f;
	int numSteps       = 3;
	int lives = 5;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gameScreen = new GameScreen(batch,numSounds,soundSequence, durationOfGame, durationOfStep, numSteps, lives );
		viewport   = new FitViewport(640,480);
		setScreen(gameScreen);
	}

	@Override
	public void render () {

		float dt = Gdx.graphics.getDeltaTime();
		//update(dt);

		getScreen().render(dt);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		getScreen().dispose();
	}
}
