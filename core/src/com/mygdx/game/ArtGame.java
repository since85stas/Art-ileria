package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.screens.EndLevelScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.util.LevelResult;

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

	// резульат уровня
	LevelResult levelResult;

	@Override
	public void create () {
		setGameScreen();
	}

	public void setGameScreen() {
		gameScreen = new GameScreen(this, numSounds, soundSequence, durationOfGame, durationOfAttempt,
				numAttempts, lives );
		setScreen(gameScreen);
	}

	public void setEndLevelScreen() {
		levelResult = gameScreen.getLevelResult();
		EndLevelScreen endLevelScreen = new EndLevelScreen(this, levelResult);
		setScreen(endLevelScreen);
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
