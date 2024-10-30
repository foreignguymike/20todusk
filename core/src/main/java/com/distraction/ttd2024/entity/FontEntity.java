package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ttd2024.Context;

import java.util.Objects;

public class FontEntity extends Entity {

    private final GlyphLayout glyphLayout;
    private final BitmapFont font;

    public boolean center = true;

    private String currentText = "";

    public FontEntity(Context context, BitmapFont font) {
        super(context);
        this.font = font;
        glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,"");
    }

    public void setText(String text) {
        if (!Objects.equals(currentText, text)) {
            currentText = text;
            glyphLayout.setText(font, text);
            w = glyphLayout.width;
            h = glyphLayout.height;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (center) {
            font.draw(sb, glyphLayout, x - glyphLayout.width / 2f, y + glyphLayout.height / 2f);
        } else {
            font.draw(sb, glyphLayout, x, y + glyphLayout.height / 2f);
        }
    }

}
