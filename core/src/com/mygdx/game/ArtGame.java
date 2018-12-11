package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.screens.EndLevelScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.LevelSettingsScreen;
import com.mygdx.game.screens.PreGameScreen;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.LevelParameters;
import com.mygdx.game.util.LevelResult;
import com.mygdx.game.util.SoundBase;

public class ArtGame extends Game {
	SoundBase soundBase;
	Preferences pref;

	// временные параметры
	int numSoundsUsed ;
	int[] soundsUsed;
	int   numSondsInSequence;
	int[] soundSequence;
//	int[] soundSequence ={0,0,1,1,2,2,3,3,4,4};
	float durationOfGame = 20.f;
	float durationOfAttempt = 2.f;
	float betweenDelay = 1f;
	int   numAttempts        = 1;
	int   lives           = 5;


	@Override
	public void create () {
		setSettScreen();
	}

	public void setSettScreen() {
		setScreen(new LevelSettingsScreen(this));
	}

	public void setPreGame(LevelParameters parameters) {

		soundBase = new SoundBase();
		numSoundsUsed = parameters.getNumSounds();
		numSondsInSequence = numSoundsUsed*2;
		soundsUsed = new int[numSoundsUsed];
		for (int i = 0; i < soundsUsed.length ; i++) {
			soundsUsed[i] = i;
		}
		boolean result = soundBase.generateSoundsBase( soundsUsed );
		if (result) {
			setScreen(new PreGameScreen(this, soundBase));
		}

		durationOfAttempt = parameters.getSoundDuration();
		betweenDelay  = parameters.getTimeDelay();
	}

	public void setGameScreen() {
		generateSoundsSeq();
		GameScreen gameScreen = new GameScreen(this,
				soundBase,
				numSoundsUsed,
				soundsUsed,
				soundSequence,
				durationOfGame,
				durationOfAttempt,
				betweenDelay,
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
//			soundSequence = ;
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
