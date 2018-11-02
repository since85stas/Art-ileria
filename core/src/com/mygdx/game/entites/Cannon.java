package com.mygdx.game.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.classes.SoundItem;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.util.Constants;

public class Cannon {

    private Texture texture;
    private GameScreen gameScreen;
    private Vector2 position;
    private SoundItem soundItem;
    private ShapeRenderer renderer ;

    // Add ScreenViewport for HUD
    ScreenViewport hudViewport;

    // Add BitmapFont
    BitmapFont font12;

    // переменные управления
    private int number;

    public Cannon (GameScreen gameScreen, Vector2 position, SoundItem soundItem) {
        this.position = position;
        this.soundItem = soundItem;
        this.gameScreen = gameScreen;
        texture        = new Texture("asteroid64.png");

        // шрифт для подписей
        generateFont12();
    }

    private void generateFont12() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;
        font12 = generator.generateFont(parameter);
    }

    public void  render(Batch batch, float dt) {

        float width = Gdx.graphics.getWidth()/gameScreen.getNumSounds();
        float height = width;

        batch.draw(texture,position.x,position.y,width,height);

        if (font12!= null) {
            font12.draw(batch, soundItem.getName(), position.x, position.y);
        }

    }
}
