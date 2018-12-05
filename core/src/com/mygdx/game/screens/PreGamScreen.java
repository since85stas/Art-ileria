package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.overlays.EndLevelHud;

/**
 * Created by seeyo on 04.12.2018.
 */

public class PreGamScreen extends InputAdapter implements Screen{

    SpriteBatch batch;
    private Stage stage;
    private Skin mySkin;
    int widtht;
    int height;


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        widtht = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Button button2 = new TextButton("PLAY",mySkin);
        button2.setSize(80.f,40.f);
        button2.setPosition(widtht/2,height/2);
        button2.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                outputLabel.setText("Pressed Text Button");
                Gdx.app.log("PreScreen","Pressed");
                return true;
            }
        });
        Gdx.input.setInputProcessor(stage);
        stage.addActor(button2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.5f, 0.6f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fps = 1 / delta;
        Gdx.app.log("preLevel","fps =" + fps);

        stage.act();
        stage.draw();

        batch.begin();
//        hud.render(batch,delta);
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
    }
}
