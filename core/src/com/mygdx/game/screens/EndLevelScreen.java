package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ArtGame;
import com.mygdx.game.overlays.EndLevelHud;
import com.mygdx.game.util.LevelResult;

public class EndLevelScreen extends InputAdapter implements Screen {

    ArtGame artGame;
    private SpriteBatch batch ;
    LevelResult levelResult;
    EndLevelHud hud;

    public EndLevelScreen (ArtGame artGame,LevelResult levelResult) {
        this.artGame = artGame;
        this.levelResult = levelResult;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        int widtht = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        hud   = new EndLevelHud(widtht,height,levelResult);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.5f, 0.6f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fps = 1 / delta;
        Gdx.app.log("endLevel","fps =" + fps);

        batch.begin();
        hud.render(batch,delta);
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            artGame.setSettScreen();
        return false;
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
        hud.dispose();
        batch.dispose();
    }
}
