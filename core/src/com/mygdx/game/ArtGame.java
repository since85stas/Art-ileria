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
	int numSounds    = 7;
	float durationOfGame = 20.f;
	float durationOfStep = 2.f;
	int numSteps       = 3;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gameScreen = new GameScreen(batch,numSounds, durationOfGame, durationOfStep, numSteps);
		viewport   = new FitViewport(640,480);
		setScreen(gameScreen);
	}

	@Override
	public void render () {

		float dt = Gdx.graphics.getDeltaTime();
		//update(dt);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getScreen().render(dt);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		getScreen().dispose();
	}
}
