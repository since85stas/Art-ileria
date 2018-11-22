package com.mygdx.game.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.classes.SoundItem;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.util.Constants;

public class Cannon extends ClickListener {

    private Texture texture;
    private GameScreen gameScreen;
    private float width;
    private float height;
    private Vector2 position;
    private SoundItem soundItem;
    private ShapeRenderer renderer ;
    private Rectangle hitBox;
    GlyphLayout layout = new GlyphLayout();

    // Add ScreenViewport for HUD
    ScreenViewport hudViewport;

    // Add BitmapFont
    BitmapFont cannonFont;

    // переменные управления
    private int number;

    public Cannon (GameScreen gameScreen, Vector2 position, SoundItem soundItem) {
        this.position = position;
        this.soundItem = soundItem;
        this.gameScreen = gameScreen;
        width = (Gdx.graphics.getWidth()-2*Constants.CANNON_LATERAL_MARGIN-Constants.SOURCE_SIZE_X)/
                gameScreen.getNumSounds();
        height = width;
        texture        = new Texture("asteroid64.png");
        hitBox = new Rectangle(position.x,position.y,width,height);
        // шрифт для подписей
        generateFont12();
        layout.setText(cannonFont,soundItem.getName());
    }

    private void generateFont12() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size =  (int)(height*Constants.TEXT_RATIO);
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;
        cannonFont = generator.generateFont(parameter);
    }

    public void  render(Batch batch, float dt) {

        batch.draw(texture,position.x,position.y,width,height);
        String title = soundItem.getName();
        float titleWidth = layout.width;
        if (cannonFont != null) {
            cannonFont.draw(batch, title, position.x + (width-titleWidth)/2, position.y);
        }
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public SoundItem getSound() {
        return soundItem;
    }
}
