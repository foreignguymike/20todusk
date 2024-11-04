package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ttd2024.Animation;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.Utils;

public class Particle extends Entity {

    public enum Type {
        BUBBLE,
        SOUL,
        BIGSOUL
    }

    public Particle.Type type;

    private float alpha = 1f;
    public boolean remove;

    public Particle(Context context, Particle.Type type, float x, float y) {
        this(context, type, x, y, 0, 0);
    }

    public Particle(Context context, Particle.Type type, float x, float y, float dx, float dy) {
        super(context);
        this.type = type;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;

        if (type == Type.BUBBLE) animation = new Animation(context.getImages("bubble"), 0.25f);
        else if (type == Type.SOUL) animation = new Animation(context.getImage("soul"));
        else if (type == Type.BIGSOUL) animation = new Animation(context.getImage("bigsoul"));
        else throw new IllegalStateException("couldn't find sprites for type " + type);
        setImage(animation.getImage());
    }

    @Override
    public void update(float dt) {
        animation.update(dt);
        setImage(animation.getImage());
        x += dx * dt;
        y += dy * dt;

        if (type == Type.BUBBLE) {
            if (animation.playCount > 0) remove = true;
        } else if (type == Type.SOUL || type == Type.BIGSOUL) {
            alpha -= dt;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1f, 1f, 1f, alpha);
        Utils.drawCentered(sb, image, x, y);
    }

}
