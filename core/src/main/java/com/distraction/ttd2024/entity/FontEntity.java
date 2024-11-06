package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.distraction.ttd2024.Context;

import java.util.Objects;

public class FontEntity extends Entity {

    private final GlyphLayout glyphLayout;
    private final BitmapFont font;

    public boolean center = true;

    private String currentText = "";
    private Color color = Color.WHITE;

    public FontEntity(Context context, BitmapFont font) {
        super(context);
        this.font = font;
        glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,"");
    }

    public FontEntity(Context context, BitmapFont font, String text, float x, float y) {
        super(context);
        this.font = font;
        glyphLayout = new GlyphLayout();
        setText(text);
        this.x = x;
        this.y = y;
    }

    public String getText() {
        return currentText;
    }

    public void setText(String text) {
        if (!Objects.equals(currentText, text)) {
            currentText = text;
            glyphLayout.setText(font, currentText, 0, currentText.length(), color, 0, Align.left, false, null);
            w = glyphLayout.width;
            h = glyphLayout.height;
        }
    }

    public void setColor(Color color) {
        if (color != this.color) {
            this.color = color;
            glyphLayout.setText(font, currentText, 0, currentText.length(), color, 0, Align.left, false, null);
        }
    }

    @Override
    public boolean contains(float x, float y, float px, float py) {
        if (center) return super.contains(x, y, px, py);
        else return x > this.x - px
            && x < this.x + w + px
            && y > this.y - h / 2 - py
            && y < this.y + h / 2 + py;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (currentText.isEmpty()) return;
        if (center) {
            font.draw(sb, glyphLayout, x - glyphLayout.width / 2f, y + glyphLayout.height / 2f);
        } else {
            font.draw(sb, glyphLayout, x, y + glyphLayout.height / 2f);
        }
    }

}
