package com.mygdx.game.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.util.Constants;

import javax.swing.text.View;

public class GameScreenHud {
    //public final Viewport viewport;

    // Add BitmapFont
    BitmapFont font;
    BitmapFont hudFont;
    BitmapFont resultFont;
    BitmapFont blinkFont;
    BitmapFontCache blinkFontCache; //= new BitmapFontCache(numberPrinter);
    int width;
    int height;
    float pauseTime;

    public GameScreenHud(int width,int height) {
        //this.viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.width = width;
        this.height = height;
        // шрифт для заголовка
        hudFont = generateHudFont();
        resultFont =generateResultFont();
        blinkFont =  generateHudFont();
    }
    public void render (Batch batch, float dt,float attemptTime,int lives, int scores, int soundNumber,
                        int attemptNumber, boolean onPause, boolean clickedResult) {
        //resultFont.draw(batch,"End of sounds ", Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

        // рисуем hud
        hudFont.draw(batch,"time  " + String.format("%.2f", attemptTime),
                Constants.HUD_MARGIN,height-Constants.HUD_MARGIN);
        hudFont.draw(batch,"lives " + lives,width/2,height-Constants.HUD_MARGIN);
        hudFont.draw(batch,"scores " + scores,width/2 + 90,height-Constants.HUD_MARGIN);

        float x = width  / 2 - 120;
        float y = height / 2;
        hudFont.draw(batch,"sound " + soundNumber+ "  " + "attempt" + attemptNumber, x,y);
        if (onPause) {
            pauseTime += dt;
            resultFont.draw(batch," " + clickedResult,x,y -40);

            blinkFont.setColor(1, 1, 1, 0.5f + 0.5f * (float) Math.sin(pauseTime * Constants.BLINKING_PERIOD));
            //Gdx.app.log("Alpha","alp " +0.5f + 0.5f * (float) Math.sin(pauseTime * 1.0f)+ " dt" + dt);
            blinkFont.draw(batch, "clicked to continue",x,y-80);
        }
    }


    private BitmapFont generateHudFont() {
        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(height*Constants.HUD_MAIN_TEXT);
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;

        font = generator.generateFont(parameter);
        return font;
    }

    private BitmapFont generateResultFont() {
        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(height*Constants.HUD_BIG_TEXT);;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;
        font = generator.generateFont(parameter);
        return font;
    }
}
