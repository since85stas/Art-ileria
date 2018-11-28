package com.mygdx.game.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.LevelResult;

/**
 * Created by seeyo on 27.11.2018.
 */

public class EndLevelHud {
    // Add BitmapFont
    BitmapFont font;
    BitmapFont hudFont;
    BitmapFont resultFont;
    BitmapFont blinkFont;
    BitmapFontCache blinkFontCache; //= new BitmapFontCache(numberPrinter);
    int width;
    int height;
    float pauseTime;
    LevelResult levelResult;
    GlyphLayout layout = new GlyphLayout();

    public EndLevelHud(int width, int height, LevelResult levelResult) {
        //this.viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.width = width;
        this.height = height;
        this.levelResult = levelResult;
        // шрифт для заголовка
        hudFont = generateHudFont();
        resultFont =generateResultFont();
        blinkFont =  generateHudFont();
        String[] results = levelResult.generateOutStrings();
    }

    public void render (Batch batch) {
        //resultFont.draw(batch,"End of sounds ", Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

        // рисуем hud
        float xHud = Constants.HUD_MARGIN;
        float yHud = height-Constants.HUD_MARGIN;
//        hudFont.draw(batch,"time  " + String.format("%.2f", attemptTime),
//                Constants.HUD_MARGIN,yHud);

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
