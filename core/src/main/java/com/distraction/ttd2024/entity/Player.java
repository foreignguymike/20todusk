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

    // dashing
    private static final float MAX_DASH_TIME = 2f;
    private static final float MAX_DASH_SPEED = 300;
    private boolean dashing;
    private float dashTime = MAX_DASH_TIME;

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

    public void setDashing() {
        if (dashing || dashTime < MAX_DASH_TIME) return;
        dashing = true;
    }

    @Override
    public void update(float dt) {
        animation.update(dt);
        animation.interval = MathUtils.map(0, MAX_DASH_SPEED, 0.2f, 0.04f, Math.abs(dx));

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
        float maxSpeed = dashing ? MAX_DASH_SPEED : MAX_SPEED;
        if (dy > maxSpeed) dy = maxSpeed;
        if (dy < -maxSpeed) dy = -maxSpeed;
        if (dx < -maxSpeed) dx = -maxSpeed;
        if (dx > maxSpeed) dx = maxSpeed;

        // move
        x += dx * dt;
        y += dy * dt;

        // update dash
        if (dashing) {
            dashTime -= dt;
            if (dashTime < 0) {
                dashTime = 0;
                dashing = false;
            }
        } else {
            dashTime += dt / 2f;
            if (dashTime > MAX_DASH_TIME) dashTime = MAX_DASH_TIME;
        }

        broomTime += dt;
        broomy = MathUtils.sin(broomTime * BOUNCE_INTERVAL) * BOUNCE_HEIGHT;
    }

    @Override
    public void render(SpriteBatch sb) {
        setImage(animation.getImage());
        Utils.drawCentered(sb, image, x, y + broomy, renderRight);
    }

}
