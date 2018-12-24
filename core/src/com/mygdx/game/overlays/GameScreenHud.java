package com.mygdx.game.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

    int width;
    int height;
    float pauseTime;
    GlyphLayout layout = new GlyphLayout();

    public GameScreenHud(int width,int height) {
        //this.viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.width = width;
        this.height = height;
        // шрифт для заголовка
        hudFont = generateHudFont();
        resultFont =generateResultFont();
        blinkFont =  generateHudFont();
    }
    public void render (Batch batch,
                        float dt,
                        float levelTime,
                        int lives,
                        int scores,
                        String soundName,
                        int attemptNumber,
                        boolean onPause,
                        boolean clickedResult) {
        //resultFont.draw(batch,"End of sounds ", Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

        // рисуем hud
        float xHud = Constants.HUD_MARGIN_UP_RATIO*width;
        float yHud = height - Constants.HUD_MAIN_TEXT*height  ;
            hudFont.draw(batch, "time  " + String.format("%.2f", levelTime),
                    xHud, yHud);
//        xHud = width/2;
//        hudFont.draw(batch,"lives " + lives,xHud,yHud);
//        xHud += 150;
//        hudFont.draw(batch,"scores " + scores,xHud,yHud);

        String text = "sound "  + soundName;
        layout.setText(hudFont,text);
        float layWidth = layout.width;
        float y = height / 2 + Constants.HUD_MARGIN_UP_RATIO*height;
        hudFont.draw(batch,text,(width - layWidth)/2, y);
        if (onPause) {
            text = "" + clickedResult;
            layout.setText(resultFont,text);
            layWidth = layout.width;
            y -= height*Constants.HUD_MAIN_TEXT*2 ;
            resultFont.draw(batch,text,(width - layWidth)/2, y);

            text = "Attention";
            layout.setText(blinkFont,text);
            layWidth = layout.width;
            y -= height*Constants.HUD_MAIN_TEXT*2 ;
            pauseTime += dt;
            blinkFont.setColor(1, 1, 1, 0.5f + 0.5f *
                    (float) Math.sin(pauseTime * Constants.BLINKING_PERIOD));
            blinkFont.draw(batch, text,(width - layWidth)/2,y);
        }
    }

    public void dispose() {
        hudFont.dispose();
        resultFont.dispose();
        blinkFont.dispose();
    }

    private BitmapFont generateHudFont() {
        BitmapFont font;
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(height - (height*(1-Constants.HUD_UP_SIZE)
                + Constants.HUD_MARGIN_UP_RATIO*height) );
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
