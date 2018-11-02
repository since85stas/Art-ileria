package com.mygdx.game.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.classes.SoundItem;
import com.mygdx.game.util.Constants;

import java.awt.*;

public class Cannon {

    private Texture texture;
    private Vector2 position;
    private SoundItem soundItem;
    private ShapeRenderer renderer ;

    // Add ScreenViewport for HUD
    ScreenViewport hudViewport;

    // Add BitmapFont
    BitmapFont font;

    // переменные управления
    private int number;

    public Cannon (Vector2 position, SoundItem soundItem) {
        this.position = position;
        this.soundItem = soundItem;
        renderer       = new ShapeRenderer();

        // Initialize the HUD viewport
//        hudViewport = new ScreenViewport();

        //  Initialize the BitmapFont
        //font = new BitmapFont();

        //  Give the font a linear TextureFilter
        //font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
    }

    public void  render(Batch batch, float dt) {

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GREEN);
        renderer.circle(position.x,position.y,30);
        renderer.end();

//        hudViewport.apply();

        // Set the SpriteBatch's projection matrix
//        batch.setProjectionMatrix(hudViewport.getCamera().combined);

        // Draw the name
//        font.setColor(Color.CYAN);

//        font.draw(batch,  soundItem.getName(), position.x , position.y + 100);

    }
}
