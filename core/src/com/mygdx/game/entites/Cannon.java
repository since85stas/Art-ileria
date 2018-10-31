package com.mygdx.game.entites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.classes.SoundItem;

public class Cannon {

    private Texture texture;
    private Vector2 position;
    private SoundItem soundItem;
    private ShapeRenderer renderer ;
    private int number;

    public Cannon (Vector2 position, SoundItem soundItem) {
        this.position = position;
        this.soundItem = soundItem;
        renderer       = new ShapeRenderer();
    }

    public void  render(Batch batch, float dt) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GREEN);
        renderer.circle(position.x,position.y,30);
        renderer.end();
    }
}
