package com.mygdx.game.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.util.Assets;

/**
 * Created by seeyo on 03.12.2018.
 */

public class Enemy  {

    private GameScreen gameScreen;
    private TextureRegion currentFrame;
    float stateTime;

    public Enemy(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void render(SpriteBatch batch, float dt) {
        stateTime += dt;

        currentFrame = Assets.instance.enemyAssets.walkAnimation.getKeyFrame(stateTime,true);
        batch.draw(currentFrame, Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
    }
}
