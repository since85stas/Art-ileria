package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ArtGame;
import com.mygdx.game.classes.SoundItem;
import com.mygdx.game.entites.Cannon;
import com.mygdx.game.entites.Enemy;
import com.mygdx.game.entites.SoundSequence;
import com.mygdx.game.overlays.GameScreenHud;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.LevelResult;
import com.mygdx.game.util.SoundBase;

public class GameScreen extends InputAdapter implements Screen  {

    private static final String TAG = GameScreen.class.getName();

    private float accumulator = 0;

    private  SpriteBatch batch ;
    private GameScreenHud hud;
    private Stage stage;
    private Skin mySkin;

    // параметры уровня
    private ArtGame artGame;
    private int numSounds;     // число возможных звуков
    private SoundItem[] usedSounds ; // число используемых звуков
    private int[] levelSequence; // номера звуков уровня
    private SoundItem[] soundsSequence;      // последовательность звуков уровня
    private int numAttempts;  // число попыток на каждый звук
    private float timeOfGame;
    private float durationOfAttempt;
    private float delayDuration ;
    private int lives;
    private SoundSequence sequence;

    // переменные уровня
    int scores ;
    int currentAttempt;
    float gameTime;
    float stageTime;
    float preloadTime;
    float attemptTime;
    float delayTime;
    private boolean isStart = false;
    private SoundItem currentSound; // играющий в данный момент звук
    boolean isEnd = false; // конец последовательности
    boolean onPause = false ; //стоим на паузе выводим результата
    boolean onDelay = false ; // задержка перед попыткой
    private Cannon clickedCannon; // выбранная пушка
    private Enemy  enemy;

    boolean clickResult ;

    // screen dimensions
    private int width ;
    private int height;

    // положения пушек
    Vector2[] cannonsCoord;
    DelayedRemovalArray<Cannon> cannons;

    // положение позиций
    Vector2[] positionCoord;

    // Add ScreenViewport for HUD
    ScreenViewport hudViewport;

    // экземпляр со звуками уровня
    SoundBase soundBase;

    // кнопки управления
    Button greenButton;
    Button redButton;

    public GameScreen( ArtGame artGame,
                       SoundBase soundBase,
                       int numSounds,
                       int[] usedSoundsInt,
                       int[] levelSequence,
                       float timeOfGame,
                       float durationOfAttempt,
                       float betweenDelay,
                       int numAttempts,
                       int lives)
    {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        this.artGame = artGame;
        this.soundBase = soundBase;
		this.numSounds  = numSounds;
		this.levelSequence = levelSequence;
		this.timeOfGame = timeOfGame;
		this.durationOfAttempt = durationOfAttempt;
		delayDuration = betweenDelay;
		this.numAttempts = numAttempts;
		this.lives      = lives;
//		this.usedSounds = usedSounds;
		AssetManager am = new AssetManager();
		Assets.instance.init(am);
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
        hud = new GameScreenHud(width,height);
		cannonsCoord = new Vector2[numSounds];
		cannons      = new DelayedRemovalArray<Cannon>(false,numSounds);
		enemy = new Enemy(this);
		positionCoord = new Vector2[numAttempts];
		getInitialCoordinates();
		getSounds(usedSoundsInt);
        sequence =new SoundSequence(soundsSequence,usedSounds, numAttempts);

        isStart = false;

        Gdx.input.setInputProcessor(this);



	}

	private void getSounds (int[] sounds) {
//        soundBase = new SoundBase();
//        boolean result = soundBase.generateSoundsBase( sounds );
            usedSounds = soundBase.getLevelSounds();
            soundsSequence = soundBase.getGameSoundSequence(levelSequence);
    }

    private void getInitialCoordinates() {

	    cannonsCoord[0] = new Vector2(Constants.SOURCE_BUTTONS_SIZE_X*width  ,0);
	    float cannonsWidth = (width - 2*Constants.SOURCE_BUTTONS_SIZE_X )/(numSounds+1);
        for (int i = 1; i < numSounds ; i++) {
            cannonsCoord[i] = new Vector2( cannonsCoord[0].x + cannonsWidth*i, 0);
        }
        for (int i = 0; i < numAttempts; i++) {
            positionCoord[i] = new Vector2(width/2,height/ numAttempts *(i+1));
        }
    }

    @Override
    public void show() {
        // Initialize the HUD viewport
        hudViewport = new ScreenViewport();

        // определяем зеленую кнопку
        mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        greenButton = new TextButton("Fire",mySkin);

        float buttonSizeX = width*Constants.ACTUAL_BUTTONS_SIZE_X;
        float buttonSzieY = buttonSizeX;
        greenButton.setSize(buttonSizeX,buttonSzieY);
        greenButton.setPosition((width*Constants.SOURCE_BUTTONS_SIZE_X-buttonSizeX)/2,
                (width*Constants.SOURCE_BUTTONS_SIZE_X-buttonSzieY)/2);
        stage.addActor(greenButton);

        // initialize cannons
        float cannonWidth = (width-2*Constants.SOURCE_BUTTONS_SIZE_X)/
                numSounds;
        float cannonHeight = height*Constants.CONTROLS_HEIGHT_RATIO;
        for (int i = 0; i < numSounds ; i++) {
            cannons.add(new Cannon(cannonsCoord[i], cannonWidth,cannonHeight,usedSounds[i]));
        }

        // определяем красную кнопку
        mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        redButton = new TextButton("Listen",mySkin);
        redButton.setPosition(cannonsCoord[numSounds-1].x + cannonWidth,0);
        redButton.setSize(buttonSizeX,   buttonSzieY);
        stage.addActor(redButton);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 1, 0.6f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        preloadTime += delta;
        if (preloadTime > Constants.STAGE_PRELOAD_TIME) {

            stage.act();
            stage.draw();

            if ( ! isStart) {
                sequence.playSound(0);
                isStart = true;
            }
            // fixed time step
            // max frame time to avoid spiral of death (on slow devices)
            float frameTime = Math.min(delta, 0.25f);
            accumulator += frameTime;

            batch.begin();

            for (int i = 0; i < numSounds; i++) {
                cannons.get(i).render(batch, delta);
            }

            float fps = 1 / delta;
            Gdx.app.log(TAG, "fps =" + fps);

            drawSoundSource();
            drawHint();

            if (!isEnd) {
                update(delta);
            } else {
                if ( true) {
                    sequence.result.setWin(true);
                } else {
                    sequence.result.setWin(false);
                }
                artGame.setEndLevelScreen(getLevelResult());
            }
            hud.render(batch,
                    delta,
                    delayTime,
                    lives,
                    scores,
                    sequence.getI() +1 ,
                    sequence.getCurrentSound().getName(),
                    currentAttempt + 1,
                    onDelay,
                    clickResult);
            batch.end();
        }
    }

    private void drawSoundSource() {
	    float xSource = cannonsCoord[numSounds-1].x + (width - cannonsCoord[numSounds-1].x) / 2;
    }

    private void drawHint() {
        float x = width  / 2 - 120;
        float y = height /2 ;
    }

    public void update (float dt) {

        if ( !onPause) {
            // время игры
            gameTime += dt;

            if (delayTime < delayDuration) {
                delayTime += dt;
                clickedCannon = null;
            } else {
                if (onDelay) {
                    playSound();
                }
                onDelay = false;
                enemy.render(batch,dt);
                if ( attemptTime < durationOfAttempt ) {
                    attemptTime += dt;
                } else {
                    sequence.answerFalse(currentAttempt);
                    for (int i = 0; i < cannons.size; i++) {
                        if (cannons.get(i).getSound() == sequence.getCurrentSound()) {
                            cannons.get(i).setBroken(true);
                        }
                    }
                    clickResult = false;
                    goToNextAttempt();
                }
            }
            if (clickedCannon != null) {
                float x = width / 2 - 120;
                float y = height / 2 - 20;
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        clickedCannon = checkClickEvent(screenX, screenY);
        if (clickedCannon != null && !onPause && !clickedCannon.isBroken() ) {
            onPause = false;
            if (clickedCannon.getSound() == sequence.getCurrentSound()) {
                clickResult = true;
                sequence.answerCorrect(currentAttempt);
                Gdx.app.log("check click", "true");
                scores++;
                goToNextAttempt();
//                            goToNextAttempt();
            } else {
                clickResult = false;
                lives--;
                clickedCannon.setBroken(true);
                sequence.answerFalse(currentAttempt);
                Gdx.app.log("check click", "false");
                goToNextAttempt();
            }
        } else if (true) {
//            goToNextAttempt();
        }
        return false;
    }

    private void goToNextAttempt() {
        enemy.setInitCoord();
        onPause = false;
        onDelay = true;
//        currentAttempt++;
        sequence.addTime(attemptTime);
        attemptTime = 0;
        delayTime   = 0;
        if (!isEnd) {
            isEnd = checkEndGame();
        }
    }

    private void playSound() {
        currentAttempt++;
        if (currentAttempt >= numAttempts || clickResult) {
            currentAttempt = 0;
            isEnd = !sequence.playNext();
        } else {
            sequence.playCurrent();
        }

    }

    private Cannon checkClickEvent(int screenX, int screenY) {
        Cannon clickPosition = null;
        for (int i = 0; i < cannons.size ; i++) {
            if(cannons.get(i).getHitBox().contains(screenX,height - screenY)) {
                clickPosition = cannons.get(i);
                break;
            }
        }
        return clickPosition;
    }

    private boolean checkEndGame() {
        boolean result = true;
        for (int i = 0; i < cannons.size ; i++) {
                result &= cannons.get(i).isBroken();
        }
        return result;
    }
	
	@Override
	public void dispose () {
		batch.dispose();
		hud.dispose();
        for (int i = 0; i < cannons.size ; i++) {
            cannons.removeIndex(i);
        }
        Assets.instance.dispose();
    }

    @Override
    public void hide() {
        dispose();
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
    }

    public int getNumSounds() {
        return numSounds;
    }

    public LevelResult getLevelResult () {
        return sequence.result;
    }

    public float getDurationOfAttempt() {
        return durationOfAttempt;
    }
}
