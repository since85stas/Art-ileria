package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ArtGame;
import com.mygdx.game.classes.SoundItem;
import com.mygdx.game.entites.Cannon;
import com.mygdx.game.entites.CannonTarget;
import com.mygdx.game.entites.Enemy;
import com.mygdx.game.overlays.GameScreenHud;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.LevelResult;
import com.mygdx.game.util.SoundBase;

public class GameScreen extends InputAdapter implements Screen {

    private static final String TAG = GameScreen.class.getName();

    private float accumulator = 0;

    private SpriteBatch batch;
    private GameScreenHud hud;
    private Stage stage;
    private Skin mySkin;

    // параметры уровня
    private ArtGame artGame;
    private int numSounds;          // число возможных звуков
    private SoundItem[] usedSounds; // число используемых звуков
    private int[] levelSequence;    // номера звуков уровня
//    private SoundItem[] soundsSequence;      // последовательность звуков уровня
    private int distanceToTarget;   // расстояние до целей
    private float timeOfGame;
    private float durationOfAttempt;
    private float delayDuration = 0;
    private int lives;
//    private SoundSequence sequence;

    // переменные уровня
    int scores;
    int currentAttempt;
    float gameTime;
    float stageTime;
    float preloadTime;
    float attemptTime;
    float delayTime;
    private boolean isStart = false; // указатель начала уровня
    private boolean isAttemptStart = false; // указатель начала попытки
    private boolean isButtonPressed = false; // нажали на зеленую кнопку
    private SoundItem currentSound; // играющий в данный момент звук
    boolean isEnd = false; // конец последовательности
    boolean onPause = false; //стоим на паузе выводим результата
    boolean onDelay = false; // задержка перед попыткой
    private Cannon clickedCannon; // выбранная пушка
    float cannonsWidth;  // размер пушки
    private Enemy enemy;

    boolean attemptResult; // результат последней попытки

    // screen dimensions
    private int width;
    private int height;

    // положения пушек
    Vector2[] cannonsCoord;
    DelayedRemovalArray<Cannon> cannons;
    DelayedRemovalArray<CannonTarget> targets;

    // положение позиций
    Vector2[] positionCoord;

    // Add ScreenViewport for HUD
    ScreenViewport hudViewport;

    // экземпляр со звуками уровня
    SoundBase soundBase;

    // кнопки управления
    Button greenButton;
    Button redButton;

    // результаты уровня
    LevelResult levelResult;

    public GameScreen(ArtGame artGame,
                      SoundBase soundBase,
                      int numSounds,
                      int[] usedSoundsInt,
                      int[] levelSequence,
                      float timeOfGame,
                      float durationOfAttempt,
                      float betweenDelay,
                      int distanceToTarget,
                      int lives) {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        this.artGame = artGame;
        this.soundBase = soundBase;
        this.numSounds = numSounds;
        this.levelSequence = levelSequence;
        this.timeOfGame = timeOfGame;
        this.durationOfAttempt = durationOfAttempt;
//        delayDuration = betweenDelay;
        this.distanceToTarget = distanceToTarget;
        this.lives = lives;
//		this.usedSounds = usedSounds;
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        hud = new GameScreenHud(width, height);
        cannonsCoord = new Vector2[numSounds];
        cannons = new DelayedRemovalArray<Cannon>(false, numSounds);
        targets = new DelayedRemovalArray<CannonTarget>(false,numSounds);
        enemy = new Enemy(this);
        positionCoord = new Vector2[distanceToTarget];
        getInitialCoordinates();
        getSounds(usedSoundsInt);
//        sequence = new SoundSequence(soundsSequence, usedSounds, distanceToTarget);
        levelResult = new LevelResult(usedSounds);

        isAttemptStart = false;

        Gdx.input.setInputProcessor(stage);

    }

    private void getSounds(int[] sounds) {

        usedSounds = soundBase.getLevelSounds();
//        soundsSequence = soundBase.getGameSoundSequence(levelSequence);
    }

    private void getInitialCoordinates() {

        cannonsCoord[0] = new Vector2(Constants.SOURCE_BUTTONS_SIZE_X * width, 0);
        cannonsWidth = (width - 2 * width * Constants.SOURCE_BUTTONS_SIZE_X) / numSounds;

        for (int i = 1; i < numSounds; i++) {
            cannonsCoord[i] = new Vector2(cannonsCoord[0].x + cannonsWidth * i, 0);
        }
        for (int i = 0; i < distanceToTarget; i++) {
            positionCoord[i] = new Vector2(width / 2, height / distanceToTarget * (i + 1));
        }
    }

    @Override
    public void show() {
        gameTime = 0;

        // Initialize the HUD viewport
        hudViewport = new ScreenViewport();

        // определяем зеленую кнопку
        mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        greenButton = new TextButton("1", mySkin);
        greenButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pressGreenBut();
                return false;
            }
        });

        float buttonSizeX = width * Constants.ACTUAL_BUTTONS_SIZE_X;
        float buttonSzieY = buttonSizeX;
        greenButton.setSize(buttonSizeX, buttonSzieY);
        greenButton.setPosition((width * Constants.SOURCE_BUTTONS_SIZE_X - buttonSizeX) / 2,
                (width * Constants.SOURCE_BUTTONS_SIZE_X - buttonSzieY) / 2);
        stage.addActor(greenButton);

        // initialize cannons
        float cannonHeight = height * Constants.CANNONS_HEIGHT_RATIO;
        int aimDistanceStep =(int)((height - height *Constants.CANNON_AIM_SIZE)/(distanceToTarget));
        for (int i = 0; i < numSounds; i++) {
            final Cannon cannon = new Cannon(cannonsCoord[i],
                    cannonsWidth,
                    cannonHeight,
                    usedSounds[i]);
            cannon.setDistanceStep (aimDistanceStep);
            cannon.setDistance (distanceToTarget)   ;
            cannon.addListener (new InputListener() {
                                   @Override
                                   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                       Gdx.app.log("Can", "stage");
                                       if (isAttemptStart) {
                                           if (isButtonPressed) {
                                               if (cannon.getSound().equals(currentSound)) {
                                                   cannon.playSound();
                                                   cannon.answerCorrect(distanceToTarget);
                                                   levelResult.setAnswer(true, currentSound);
                                                   attemptResult = true;
                                                   endCurrentAttempt();
                                                   checkEndGame();
                                               } else {
                                                   cannon.playSound();
//                                                   cannon.answerFalse();
                                                   checkEndAttemptTime();
                                                   endCurrentAttempt();
                                                   levelResult.setAnswer(false, currentSound);
                                                   attemptResult = false;
                                                   checkEndGame();
                                               }
                                           }
                                       } else {
                                           cannon.playSound();
                                       }
                                       return super.touchDown(event, x, y, pointer, button);
                                   }
                               }
            );
            stage.addActor(cannon);
            cannons.add(cannon);
        }

        // определяем цели
        for (int i = 0; i < cannons.size; i++) {
            CannonTarget target = new CannonTarget(
                    (int)cannonsCoord[i].x,
                    (int)(cannonsCoord[i].y + height - height*Constants.TARGETS_HEIGHT_RATIO),
                    cannonsWidth,
                    height*Constants.TARGETS_HEIGHT_RATIO
            );
            targets.add(target);
        }

        // определяем красную кнопку
        mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        redButton = new TextButton("2", mySkin);
        redButton.setPosition(cannonsCoord[numSounds - 1].x + cannonsWidth, 0);
        redButton.setSize(buttonSizeX, buttonSzieY);
        redButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pressRedBut();
                return false;
            }
        });
        stage.addActor(redButton);
    }

    private void pressGreenBut() {
        if (!isStart) {
            isStart = true;
        }
        if(!isButtonPressed) {
            isButtonPressed = true;
            isAttemptStart = true;
            goToNextAttempt();
        }
    }

    private void pressRedBut() {
        for (int i = 0; i < cannons.size; i++) {
            if(cannons.get(i).getCharge() == distanceToTarget) {
                targets.get(i).setBroken(true);
            }
            cannons.get(i).nullCharge();
        }
        isEnd = checkEndGame();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 1, 0.6f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        preloadTime += delta;
        if (preloadTime > Constants.STAGE_PRELOAD_TIME) {
            stage.act();
            stage.draw();

            // fixed time step
            // max frame time to avoid spiral of death (on slow devices)
            float frameTime = Math.min(delta, 0.25f);
            accumulator += frameTime;

            batch.begin();

            for (int i = 0; i < numSounds; i++) {
                targets.get(i).render(batch,delta);
                cannons.get(i).render(batch, delta);
            }

            float fps = 1 / delta;

            if (!isEnd ) {
                if (isStart) {
                    update(delta);
                }
            } else {
                artGame.setEndLevelScreen(getLevelResult());
            }

            String string;
            if (currentSound == null) {
                string = "--";
            } else {
                string = currentSound.getName();
            }
            boolean drawHint = true;
            hud.render (batch,
                    delta,
                    timeOfGame - gameTime,
                    lives,
                    scores,
                    drawHint,
                    string,
                    currentAttempt + 1,
                    !isButtonPressed,
                    attemptResult);
            batch.end();
        }
    }

    public void update(float dt) {

        // время игры
        gameTime += dt;

        if (gameTime > timeOfGame) {
            isEnd = true;
        } else {
            if (delayTime < delayDuration) {
                delayTime += dt;
            } else {
                if (isAttemptStart) {
                    enemy.render(batch, dt);
                    if (attemptTime < durationOfAttempt) {
                        attemptTime += dt;
                    } else {
                        checkEndAttemptTime();
                        endCurrentAttempt();
                        attemptResult = false;
                        checkEndGame();
//                        goToNextAttempt();
                    }
                }
            }
        }
    }

    private void checkEndAttemptTime() {
        for (int i = 0; i < cannons.size ; i++) {
            if(cannons.get(i).getSound().getName().equals(currentSound.getName())) {
                cannons.get(i).answerFalse();
            }
        }
    }

    private void goToNextAttempt() {


//        isAttemptStart = false;
        currentSound = usedSounds[MathUtils.random(0, numSounds - 1)];
        currentSound.playSound();
    }

    private void endCurrentAttempt() {
        if (!isEnd) {
            isEnd = checkEndGame();
        }
        enemy.setInitCoord();
        onPause = false;
        isAttemptStart = false;
        isButtonPressed = false;
        attemptTime = 0;
        delayTime = 0;
    }

    private Cannon checkClickEvent(int screenX, int screenY) {
        Cannon clickPosition = null;
        for (int i = 0; i < cannons.size; i++) {
            if (cannons.get(i).getHitBox().contains(screenX, height - screenY)) {
                clickPosition = cannons.get(i);
                break;
            }
        }
        return clickPosition;
    }

    private boolean checkEndGame() {
        boolean result = true;
        for (int i = 0; i < targets.size; i++) {
            result &= targets.get(i).isBroken();
        }
        return result;
    }

    @Override
    public void dispose() {
        batch.dispose();
        hud.dispose();
        for (int i = 0; i < cannons.size; i++) {
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

    public LevelResult getLevelResult() {
        return levelResult;
    }

    public float getDurationOfAttempt() {
        return durationOfAttempt;
    }
}
