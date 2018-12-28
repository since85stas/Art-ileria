package com.mygdx.game.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.classes.SoundItem;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

import javax.xml.soap.Text;

public class Cannon extends Actor {
    private boolean isBroken = false;

    // заряд пушки
    private int charge = 0;

    private Texture cannonTexture;
//    private TextureRegion aimTexture;
    private Texture explosTexture;
//    private GameScreen gameScreen;
    private float width;
    private float height;
    private int aimDistanceStep; // шаг расстояния до цели в пикселях
    private int aimDistance;  // расстояние до цели
    private Vector2 position;
    private SoundItem soundItem;

    private Rectangle hitBox;
    GlyphLayout layout = new GlyphLayout();

    // Add ScreenViewport for HUD
    ScreenViewport hudViewport;

    // Add BitmapFont
    BitmapFont cannonFont;

    // переменные управления

    public Cannon ( Vector2 position, float width, float height ,SoundItem soundItem) {
        this.position = position;
        this.soundItem = soundItem;
        this.width = width;
        this.height = height;

        cannonTexture = new Texture("asteroid64.png");
//        Texture text = new Texture("crosshairs_strip6.png");
//        aimTexture = new TextureRegion(text,0,0,64,64);
        explosTexture  = new Texture("explosion-large.png");
        hitBox = new Rectangle(position.x,position.y,width,height);

        // шрифт для подписей
        generateFont12();
        layout.setText(cannonFont,soundItem.getName());

        // определяем границы для нажатий
        setWidth(width);
        setHeight(height);
        setBounds(position.x,position.y,width,height);
    }

    private void generateFont12() {
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size =  (int)(height*Constants.TEXT_RATIO);
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;
        cannonFont = generator.generateFont(parameter);
    }

    public void  render(Batch batch, float dt) {
        batch.draw(cannonTexture,position.x,0,width,height);
        batch.draw(Assets.instance.crosshairAssets.getAimTexture(charge != aimDistance),
                position.x + width/2,
                charge*aimDistanceStep + height/2,
                width*Constants.CANNON_AIM_SIZE,
                width*Constants.CANNON_AIM_SIZE);
        if (isBroken) {
            batch.draw(explosTexture,position.x,0,width,height);
        }
        String title = soundItem.getName();
        float titleWidth = layout.width;
        if (cannonFont != null) {
            cannonFont.draw(batch, title, position.x + (width-titleWidth)/2, position.y +
            Constants.HUD_MARGIN_DOWN_RATIO*height + Constants.TEXT_RATIO*height);
            cannonFont.draw(batch,""+charge,position.x + width/2,position.y + height);
        }
    }

    public void playSound() {
        soundItem.playSound();
    }

    public void answerCorrect(int max) {
        charge ++;
        if (charge > max) {
            charge = max;
        }
    }

    public void answerFalse() {
        charge--;
        if(charge<0) {
            charge = 0;
        }
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public SoundItem getSound() {
        return soundItem;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setDistanceStep(int distance) {
        aimDistanceStep = distance;
    }

    public void setDistance(int distance) {
        aimDistance = distance;
    }

    public int getCharge() {
        return charge;
    }

    public void nullCharge() {
        charge = 0;
    }
}
