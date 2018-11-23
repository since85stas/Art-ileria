package com.mygdx.game.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.GameScreen;

import javax.swing.text.View;

public class GameScreenHud {
    //public final Viewport viewport;
    // Add BitmapFont
    BitmapFont font;
    BitmapFont hudFont;
    BitmapFont resultFont;

    public GameScreenHud() {
        //this.viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        // шрифт для заголовка
        generateHudFont();
        generateResultFont();
    }
    public void render (Batch batch) {
        resultFont.draw(batch,"End of sounds ", Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
    }

    private void generateHudFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;
        hudFont = generator.generateFont(parameter);
    }

    private void generateResultFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;
        resultFont = generator.generateFont(parameter);
    }
}
