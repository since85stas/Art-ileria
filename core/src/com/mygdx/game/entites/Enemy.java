package com.mygdx.game.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

/**
 * Created by seeyo on 03.12.2018.
 */

public class Enemy  {

    private GameScreen gameScreen;
    private TextureRegion currentFrame;
    float stateTime;

    float width;
    float height;

    float x ;
    float y ;

    public Enemy(GameScreen gameScreen) {

        width = Gdx.graphics.getWidth();
        height =Gdx.graphics.getHeight();

        this.gameScreen = gameScreen;
        this.x = width*Constants.ENEMY_X_POSIT;
        this.y = height* ( 1 - ( Constants.HUD_UP_SIZE));
//        this.y = height - height*(Constants.ENEMY_SIZE + Constants.HUD_UP_SIZE);
    }

    public void render(SpriteBatch batch, float dt) {
        stateTime += dt;

        currentFrame = Assets.instance.enemyAssets.walkAnimation.getKeyFrame(stateTime,true);
        float enemyWidth = width * Constants.ENEMY_SIZE;
        float enemyHeight = enemyWidth;

        batch.draw(currentFrame, x,y,enemyWidth,enemyHeight);
    }

    public void update(float dt) {

    }
}
