package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.Utils;

public class Collectable extends Entity {

    public enum Type {
        SOUL
    }

    public Type type;

    public Collectable(Context context, Type type, float x, float y) {
        super(context);
        this.type = type;
        this.x = x;
        this.y = y;

        if (type == Type.SOUL) {
            setImage(context.getImage("soul"));
        } else {
            throw new IllegalStateException("couldn't find sprites for type " + type);
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, image, x, y);
    }

}
