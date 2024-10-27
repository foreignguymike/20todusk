package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.ttd2024.Animation;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.Utils;

public class Player extends Entity {

    // moving
    private static final float ACCEL = 6;
    private static final float FRICTION = 2;
    private static final float MAX_SPEED = 150;

    // broom
    private static final float BOUNCE_HEIGHT = 6f;
    private static final float BOUNCE_INTERVAL = 4f;
    private float broomTime;
    private float broomy;

    public boolean up, down, left, right;

    private boolean renderRight;

    public Player(Context context) {
        super(context);

        animation = new Animation(context.getImages("witch"), 0.2f);
    }

    @Override
    public void update(float dt) {
        animation.update(dt);
        animation.interval = MathUtils.map(0, MAX_SPEED, 0.2f, 0.1f, Math.abs(dx));

        // friction
        if (dy > 0) {
            dy -= FRICTION;
            if (dy < 0) dy = 0;
        } else if (dy < 0) {
            dy += FRICTION;
            if (dy > 0) dy = 0;
        }
        if (dx > 0) {
            dx -= FRICTION;
            if (dx < 0) dx = 0;
        } else if (dx < 0) {
            dx += FRICTION;
            if (dx > 0) dx = 0;
        }

        // velocity
        if (up) dy += ACCEL;
        if (down) dy -= ACCEL;
        if (left) dx -= ACCEL;
        if (right) dx += ACCEL;

        if (left) renderRight = false;
        if (right) renderRight = true;

        // clamp max speed
        if (dy > MAX_SPEED) dy = MAX_SPEED;
        if (dy < -MAX_SPEED) dy = -MAX_SPEED;
        if (dx < -MAX_SPEED) dx = -MAX_SPEED;
        if (dx > MAX_SPEED) dx = MAX_SPEED;

        // move
        x += dx * dt;
        y += dy * dt;

        broomTime += dt;
        broomy = MathUtils.sin(broomTime * BOUNCE_INTERVAL) * BOUNCE_HEIGHT;
    }

    @Override
    public void render(SpriteBatch sb) {
        setImage(animation.getImage());
        Utils.drawCentered(sb, image, x, y + broomy, renderRight);
    }

}
