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
    BitmapFont hudFont;
    BitmapFont resultFont;
    BitmapFont blinkFont;

    int width;
    int height;
    String[] results;

    float time;

    GlyphLayout layout = new GlyphLayout();

    public EndLevelHud(int width, int height, LevelResult levelResult) {
        //this.viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.width = width;
        this.height = height;
//        this.levelResult = levelResult;

        // шрифт для заголовка
        hudFont = generateHudFont();
        resultFont =generateResultFont();
        blinkFont =  generateHudFont();

        results = levelResult.generateUniqueOutStrings();
        Gdx.app.log("endHud", "test");
    }

    public void render (Batch batch, float dt) {
        time +=dt;
        // рисуем hud
        float xHud = Constants.END_HUD_MARGIN;
        float yHud = height - Constants.END_HUD_MARGIN;
        resultFont.draw(batch,"Level finished",xHud,yHud);
        yHud -= (int)(height*Constants.HUD_BIG_TEXT)*2;
        for (int i = 0; i < results.length ; i++) {
            yHud -= (height*Constants.HUD_MAIN_TEXT)*2;
            hudFont.draw(batch,results[i],xHud,yHud);
        }

        blinkFont.setColor(1, 1, 1,
                0.5f + 0.5f * (float) Math.sin(time * Constants.BLINKING_PERIOD));
        blinkFont.draw(batch, "Tap to restart",xHud,
                yHud - (int)(height*Constants.HUD_BIG_TEXT)*2 );

    }

    public void dispose() {
        hudFont.dispose();
        resultFont.dispose();
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
        parameter.size = (int)(height*Constants.HUD_BIG_TEXT);
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;
        font = generator.generateFont(parameter);
        return font;
    }

}
