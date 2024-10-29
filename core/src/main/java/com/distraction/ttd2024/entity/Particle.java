package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ttd2024.Animation;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.Utils;

public class Particle extends Entity {

    public enum Type {
        BUBBLE
    }

    public Particle.Type type;

    public boolean remove;

    public Particle(Context context, Particle.Type type, float x, float y) {
        super(context);
        this.type = type;
        this.x = x;
        this.y = y;

        if (type == Type.BUBBLE) {
            animation = new Animation(context.getImages("bubble"), 0.25f);
            setImage(animation.getImage());
        } else {
            throw new IllegalStateException("couldn't find sprites for type " + type);
        }
    }

    @Override
    public void update(float dt) {
        animation.update(dt);
        setImage(animation.getImage());

        if (type == Type.BUBBLE) {
            if (animation.playCount > 0) remove = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, image, x, y);
    }

}
