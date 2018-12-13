package com.mygdx.game.entites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.util.Constants;

/**
 * Created by seeyo on 13.12.2018.
 */

public class CannonTarget {

    private boolean isBroken = false;

    private float width;
    private float height;
    private Vector2 position;

    private Texture texture;
    private Texture explosTexture;


    public CannonTarget (Vector2 position, float width, float height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public void  render(Batch batch, float dt) {
        batch.draw(texture,position.x,0,width,height);
        if (isBroken) {
            batch.draw(explosTexture,position.x,0,width,height);
        }

//        if (cannonFont != null) {
//            cannonFont.draw(batch, title, position.x + (width-titleWidth)/2, position.y +
//                    Constants.HUD_MARGIN_DOWN_RATIO*height + Constants.TEXT_RATIO*height);
//        }
    }
}
