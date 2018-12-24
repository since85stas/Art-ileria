package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ArtGame;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.LevelParameters;

public class LevelSettingsScreen extends InputAdapter implements Screen {
    private final static int BOX_SIZE = 60;
    private final static String[] soundsNum = {"2","3","4","5","6","7","8","9"};
    private final static String[] durNum = {"2","3","4","5","6","7"};
    private final static String[] linesNum = {"1","2","3"};
    private final static String[] gameTime = {"60","120","180","240","300"};


    ArtGame game;
    SpriteBatch batch;
    BitmapFont hudFont;
    ArtGame mGame;
    SelectBox<String> soundsSelect;
    SelectBox<String> durationSelect;
    SelectBox<String> delaySelect;
    SelectBox<String> timeSelect;

    private Stage stage;
    private Skin mySkin;
    int widtht;
    int height;

    float xNumSelet;
    float yNumSelet;
    float xDurSelet;
    float yDurSelet;
    float xDelSelet;
    float yDelSelet;
    float xTimeSelet;
    float yTimeSelet;

    public LevelSettingsScreen(ArtGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        widtht = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
//        mySkin.getFont()
        hudFont = generateHudFont();

        generateButtons();
        xNumSelet = widtht/2 ;
        yNumSelet = height/2;
        generateSoundsSelectBox(xNumSelet,yNumSelet);

        xDurSelet = xNumSelet;
        yDurSelet = yNumSelet - height * Constants.HUD_MAIN_TEXT*3;
        generateDurationSelectBox(xDurSelet,yDurSelet);

        xDelSelet = xDurSelet;
        yDelSelet = yDurSelet - height * Constants.HUD_MAIN_TEXT*3;
        generateLinesSelectBox(xDelSelet,yDelSelet);

        xTimeSelet = xDelSelet;
        yTimeSelet = yDelSelet - height * Constants.HUD_MAIN_TEXT*3;
        generateTimeSelectBox(xTimeSelet,yTimeSelet);

//        generateButtons(soundBase.getUsedSounds());
//        ;       usedSoundsItems = soundBase.getLevelSounds();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 1f, 0.7f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fps = 1 / delta;
        Gdx.app.log("preLevel","fps =" + fps);

        stage.act();
        stage.draw();

        batch.begin();
        float dx = 250;
        hudFont.draw(batch,"sounds",xNumSelet - dx,
                yNumSelet + (height * Constants.HUD_MAIN_TEXT) );
        hudFont.draw(batch,"duration",xDurSelet - dx,
                yDurSelet + (height * Constants.HUD_MAIN_TEXT) );
        hudFont.draw(batch,"lines",xDelSelet - dx,
                yDelSelet + (height * Constants.HUD_MAIN_TEXT) );
        hudFont.draw(batch,"time",xTimeSelet - dx,
                yTimeSelet + (height * Constants.HUD_MAIN_TEXT) );
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private BitmapFont generateHudFont() {
        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (height * Constants.HUD_MAIN_TEXT);
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;
        font = generator.generateFont(parameter);
        return font;
    }

    private void generateButtons() {

        float buttonX = widtht / 2;
        float buttonY = height - height * Constants.HUD_PRE_ITEM_SIZE * 2;

        Button testButton ;
//        Spinner

        buttonY -= 2 * height * Constants.HUD_PRE_ITEM_SIZE;
        testButton = new TextButton("start", mySkin);
        testButton.setSize(80.f, 40.f);
        testButton.setPosition(buttonX, buttonY);

        testButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

//                int num = Integer.valueOf(soundsSelect.getSelected());
//                prefs.putInteger(Constants.PREF_NUM_SOUNDS,  num);
//                float fl = Float.valueOf(durationSelect.getSelected());
//                prefs.putFloat(Constants.PREF_SOUND_DURATIN,fl );
//                fl = Float.valueOf(delaySelect.getSelected());
//                prefs.putFloat(Constants.PREF_BETWEEN_DELAY,
//                        fl);
                LevelParameters parameters = new LevelParameters();
                parameters.setNumSounds(Integer.valueOf(soundsSelect.getSelected()));
                parameters.setSoundDuration(Float.valueOf(durationSelect.getSelected()));
                parameters.setNumLines(Integer.valueOf(delaySelect.getSelected()));
                parameters.setGameTime(Integer.valueOf(timeSelect.getSelected()));
                game.setGameScreen(parameters);

                return true;
            }
        });
        stage.addActor(testButton);

    }

    private void generateSoundsSelectBox(float x, float y) {
        soundsSelect = new SelectBox<String>(mySkin);
        soundsSelect.setSize(BOX_SIZE,BOX_SIZE);
//        soundsSelect.sest
        soundsSelect.setItems(soundsNum);
        soundsSelect.setSelected("5");
        soundsSelect.setX(x);
        soundsSelect.setY(y);

        soundsSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("sett","select");
                String str = soundsSelect.getSelected();
            }
        });
        Actor actor = soundsSelect;
        actor.setName("soundSelect");
        stage.addActor(actor);
    }

    private void generateDurationSelectBox(float x ,float y ) {
        durationSelect = new SelectBox<String>(mySkin);
        durationSelect.setSize(BOX_SIZE,BOX_SIZE);
        durationSelect.setItems(durNum);
        durationSelect.setSelected("5");
        durationSelect.setX(x);
        durationSelect.setY(y);

        durationSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("sett","select");
                String str = durationSelect.getSelected();
            }
        });
        Actor actor = durationSelect;
        actor.setName("durationSelect");
        stage.addActor(actor);
    }

    private void generateLinesSelectBox(float x , float y ) {
        delaySelect = new SelectBox<String>(mySkin);
        delaySelect.setSize(BOX_SIZE,BOX_SIZE);
        delaySelect.setItems(linesNum);
        delaySelect.setSelected("2");
        delaySelect.setX(x);
        delaySelect.setY(y);

        delaySelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("sett","select");
                String str = delaySelect.getSelected();
            }
        });
        Actor actor = delaySelect;
        actor.setName("delaySelect");
        stage.addActor(actor);
    }

    private void generateTimeSelectBox(float x , float y ) {
        timeSelect = new SelectBox<String>(mySkin);
        timeSelect.setSize(BOX_SIZE,BOX_SIZE);
        timeSelect.setItems(gameTime);
        timeSelect.setSelected("60");
        timeSelect.setX(x);
        timeSelect.setY(y);

        timeSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("sett","select");
                String str = timeSelect.getSelected();
            }
        });
        Actor actor = timeSelect;
        actor.setName("timeSelect");
        stage.addActor(actor);
    }

}
