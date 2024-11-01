package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ttd2024.Animation;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.Utils;

public class Entity {

    protected Context context;

    public float x;
    public float y;
    public float w;
    public float h;

    public float dx;
    public float dy;

    protected Animation animation;
    protected TextureRegion image;

    public Entity(Context context) {
        this.context = context;
    }

    public Entity(Context context, TextureRegion image, float x, float y) {
        this.context = context;
        this.x = x;
        this.y = y;
        setImage(image);
    }

    public void setImage(TextureRegion image) {
        this.image = image;
        w = image.getRegionWidth();
        h = image.getRegionHeight();
    }

    public boolean contains(float x, float y) {
        return x > this.x - w / 2
            && x < this.x + w / 2
            && y > this.y - h / 2
            && y < this.y + h / 2;
    }

    public boolean contains(float x, float y, float px, float py) {
        return x > this.x - w / 2 - px
            && x < this.x + w / 2 + px
            && y > this.y - h / 2 - py
            && y < this.y + h / 2 + py;
    }

    public boolean overlaps(float x, float y, float w, float h) {
        return this.x - this.w / 2 < x + w / 2
            && this.x + this.w / 2 > x - w / 2
            && this.y - this.h / 2 < y + h / 2
            && this.y + this.h / 2 > y - h / 2;
    }

    public boolean overlaps(Entity o) {
        return overlaps(o.x, o.y, o.w, o.h);
    }

    public void update(float dt) {
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (image != null) Utils.drawCentered(sb, image, x, y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + w + ", " + h + "]";
    }
}

