package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by seeyo on 03.12.2018.
 */

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    private Assets() {
    }

    public EnemyAssets enemyAssets;
    public TargetAssets targetAssets;

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load("sprite-animation4.png", Texture.class);
        assetManager.load("ogachallenge6.png",Texture.class);
        assetManager.finishLoading();
        Texture walkTexture = assetManager.get("sprite-animation4.png");
        Texture targetTexture = assetManager.get("ogachallenge6.png");
        enemyAssets = new EnemyAssets(walkTexture);
        targetAssets = new TargetAssets(targetTexture);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class EnemyAssets {
        private static final int FRAME_COLS = 6; // #1
        private static final int FRAME_ROWS = 5; // #2
        private static final int FRAME_ROWS_USED = 2;

        public final Animation<TextureRegion> walkAnimation;
        TextureRegion[] walkFrames; // #5
        SpriteBatch spriteBatch; // #6
        TextureRegion currentFrame; //

        float stateTime;

        public EnemyAssets (Texture texture) {
            TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth()/FRAME_COLS,
                    texture.getHeight()/FRAME_ROWS); // #10
            walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS_USED];
            int index = 0;
            for (int i = 0; i < FRAME_ROWS_USED; i++) {
                for (int j = 0; j < FRAME_COLS; j++) {
                    walkFrames[index++] = tmp[i][j];
                }
            }
            walkAnimation = new Animation<TextureRegion>(Constants.WALK_LOOP_DURATION,walkFrames);
            Gdx.app.log(TAG,"animation load");

        }
    }

    public class TargetAssets {
        private static final int FRAME_COLS = 3; // #1
        private static final int FRAME_ROWS = 1; // #2
        private static final int FRAME_ROWS_USED = 2;

//        public final Animation<TextureRegion> walkAnimation;
        public final TextureRegion targetTexture;
        TextureRegion[] walkFrames; // #5
        SpriteBatch spriteBatch; // #6
        TextureRegion currentFrame; //

        float stateTime;

        public TargetAssets (Texture texture) {
            TextureRegion[][] tmp = TextureRegion.split(texture,
                    texture.getWidth()/FRAME_COLS,
                    texture.getHeight()/FRAME_ROWS); // #10

            targetTexture = tmp[2][0];
            Gdx.app.log(TAG,"animation load");

        }
    }
}
