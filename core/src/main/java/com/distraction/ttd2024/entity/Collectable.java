package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ttd2024.Animation;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.Utils;

public class Collectable extends Entity {

    public enum Type {
        SOUL(100, "soul"),
        BIGSOUL(400, "soul"),
        TWOX(0, "2x"),
        SPIKE(0, "hit");

        public final int points;
        public final String sound;

        Type(int points, String sound) {
            this.points = points;
            this.sound = sound;
        }

        boolean hasScore() {
            return points > 0;
        }
    }

    public Type type;

    public Collectable(Context context, Type type, float x, float y) {
        super(context);
        this.type = type;
        this.x = x;
        this.y = y;

        if (type == Type.SOUL) animation = new Animation(context.getImage("soul"));
        else if (type == Type.BIGSOUL) animation = new Animation(context.getImage("bigsoul"));
        else if (type == Type.TWOX) animation = new Animation(context.getImage("2x"));
        else if (type == Type.SPIKE) animation = new Animation(context.getImage("spike"));
        else throw new IllegalStateException("couldn't find sprites for type " + type);
        setImage(animation.getImage());
    }

    @Override
    public void update(float dt) {
        animation.update(dt);
        setImage(animation.getImage());
        w = h = 1;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, image, x, y);
    }

}
