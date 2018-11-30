package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.screens.EndLevelScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.util.LevelResult;

public class ArtGame extends Game {

	// временные параметры
	int   numSounds    = 5;
	int[] soundSequence ;
	float durationOfGame = 20.f;
	float durationOfAttempt = 3.f;
	int   numAttempts        = 2;
	int   lives           = 5;

	@Override
	public void create () {
		setGameScreen();
	}

	public void setGameScreen() {
		generateSoundsSeq();
		GameScreen gameScreen = new GameScreen(this, numSounds, soundSequence,
				durationOfGame, durationOfAttempt, numAttempts, lives );
		setScreen(gameScreen);
	}

	public void setEndLevelScreen( LevelResult levelResult) {
		EndLevelScreen endLevelScreen = new EndLevelScreen(this, levelResult);
		setScreen(endLevelScreen);
	}

	private void generateSoundsSeq() {
		soundSequence = new int[numSounds];
		for (int i = 0; i < soundSequence.length; i++) {
			soundSequence[i] = MathUtils.random(0,soundSequence.length-1);
		}
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
