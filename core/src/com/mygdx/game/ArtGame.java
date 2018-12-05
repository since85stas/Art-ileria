package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.screens.EndLevelScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.util.LevelResult;

public class ArtGame extends Game {

	// временные параметры
	int numSoundsUsed = 5;
	int[] soundsUsed  = {0,1,2,3,4};
	int   numSondsInSequence = 10;
	int[] soundSequence ;
	float durationOfGame = 20.f;
	float durationOfAttempt = 2.f;
	int   numAttempts        = 1;
	int   lives           = 5;

	@Override
	public void create () {
		setGameScreen();
	}

	public void setGameScreen() {
		generateSoundsSeq();
		GameScreen gameScreen = new GameScreen(this,
				numSoundsUsed,
				soundsUsed,
				soundSequence,
				durationOfGame,
				durationOfAttempt,
				numAttempts,
				lives );
		setScreen(gameScreen);
	}

	public void setEndLevelScreen( LevelResult levelResult) {
		EndLevelScreen endLevelScreen = new EndLevelScreen(this, levelResult);
		setScreen(endLevelScreen);
	}

	private void generateSoundsSeq() {
		soundSequence = new int[numSondsInSequence];
		for (int i = 0; i < numSondsInSequence -1 ; i++) {
			soundSequence[i] = MathUtils.random( 0, numSoundsUsed -1 );
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
