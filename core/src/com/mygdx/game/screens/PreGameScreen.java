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
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ArtGame;
import com.mygdx.game.classes.SoundItem;
import com.mygdx.game.overlays.EndLevelHud;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.SoundBase;

import java.util.ArrayList;

/**
 * Created by seeyo on 04.12.2018.
 */

public class PreGameScreen extends InputAdapter implements Screen{

    SpriteBatch batch;
    SoundBase soundBase;
    BitmapFont hudFont;
    ArtGame mGame;

    private SoundItem[] usedSoundsItems ; // число используемых звуков
    private Stage stage;
    private Skin mySkin;
    int widtht;
    int height;

    public PreGameScreen(ArtGame game, SoundBase soundBase) {
        mGame = game;
        this.soundBase = soundBase;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        widtht = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        hudFont = generateHudFont();
        generateButtons(soundBase.getUsedSounds());
;       usedSoundsItems = soundBase.getLevelSounds();
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
        hudFont.draw(batch,
                "click to listen",
                widtht/2,
                height - height*Constants.HUD_PRE_UP_RATIO);
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
        this.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        hudFont.dispose();
    }

    private void generateButtons(int[] usedSounds) {
        Button[] buttons = new Button[usedSounds.length];

        float buttonX = widtht/2;
        float buttonY = height - height*Constants.HUD_PRE_ITEM_SIZE*2;

        for (int i = 0; i < buttons.length; i++) {
            buttonY -= 2*height*Constants.HUD_PRE_ITEM_SIZE;
            buttons[i] = new TextButton(soundBase.getGameSounds()[i].getName(),mySkin);
            buttons[i].setSize(100.f,60.f);
            buttons[i].setPosition(buttonX,buttonY);

            buttons[i].addListener(new InputListener(){
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                outputLabel.setText("Pressed Text Button");
                    Gdx.app.log("PreScreen","Pressed");
                    Actor act = event.getListenerActor();
//                    InputEvent ev = event.getListenerActor();

                    String str = act.getName();
                    for (int j = 0; j < usedSoundsItems.length ; j++) {
                        if (usedSoundsItems[j].getName().equals(str)) {
                            usedSoundsItems[j].playSound();
                        }
                    }

                    return true;
                }
            });
            stage.addActor(buttons[i]);
            stage.getActors().get(i).setName(soundBase.getGameSounds()[i].getName());
        }

        buttonY -= 2*height*Constants.HUD_PRE_ITEM_SIZE;
        Button startButton = new TextButton("Start level",mySkin);
        startButton.setSize(150.f,60.f);
        startButton.setPosition(buttonX,buttonY);

        startButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                outputLabel.setText("Pressed Text Button");
                Gdx.app.log("PreScreen","Pressed");
                mGame.setGameScreen();
                return true;
            }
        });
        Gdx.input.setInputProcessor(stage);
//            Actor actor = new Actor();
//            actor.setName(soundBase.getGameSounds()[i].getName());
//            actor.
        stage.addActor(startButton);

    }

    private BitmapFont generateHudFont() {
        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(height*Constants.HUD_MAIN_TEXT);
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;
        font = generator.generateFont(parameter);
        return font;
    }

}
