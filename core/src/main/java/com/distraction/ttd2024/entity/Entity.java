package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ttd2024.Animation;
import com.distraction.ttd2024.Context;

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
        this(context, 0, 0, 0, 0);
    }

    public Entity(Context context, float x, float y, float w, float h) {
        this.context = context;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void setImage(TextureRegion image) {
        this.image = image;
        w = image.getRegionWidth();
        h = image.getRegionHeight();
    }

    public boolean contains(float x, float y) {
        return x > this.x - w / 2 && x < this.x + w / 2 && y > this.y - h / 2 && y < this.y + h / 2;
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
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + w + ", " + h + "]";
    }
}

