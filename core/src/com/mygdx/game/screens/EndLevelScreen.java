package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ArtGame;
import com.mygdx.game.overlays.EndLevelHud;
import com.mygdx.game.util.LevelResult;

public class EndLevelScreen implements Screen {

    ArtGame artGame;
    private SpriteBatch batch ;
    LevelResult levelResult;
    EndLevelHud hud;

    public EndLevelScreen (ArtGame artGame,LevelResult levelResult) {
        this.artGame = artGame;
        this.levelResult = levelResult;
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
        batch.begin();
        hud.render(batch);
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
        batch.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
