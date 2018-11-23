package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.screens.GameScreen;

public class ArtGame extends Game {

	GameScreen gameScreen;
	FitViewport viewport;

	// временные параметры
	int   numSounds    = 5;
	int[] soundSequence = {0,1,2,3,4};
	float durationOfGame = 20.f;
	float durationOfAttempt = 2.f;
	int   numAttempts        = 2;
	int lives           = 5;

	@Override
	public void create () {
		setGameScreen();
	}

	public void setGameScreen() {

		gameScreen = new GameScreen(numSounds,soundSequence, durationOfGame, durationOfAttempt, numAttempts , lives );
		setScreen(gameScreen);
	}

	public void setEndLevelScreen() {

	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		//update(dt);
		getScreen().render(dt);
	}
	
	@Override
	public void dispose () {
		getScreen().dispose();
	}
}
