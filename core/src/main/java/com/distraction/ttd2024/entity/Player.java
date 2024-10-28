package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.ttd2024.Animation;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.Utils;

public class Player extends Entity {

    // moving
    private static final float ACCEL = 6;
    private static final float FRICTION = 2;
    private static final float MAX_SPEED = 200;

    // dashing
    private static final float MAX_DASH_TIME = 3f;
    private static final float MAX_DASH_SPEED = 400;
    private float dashTime = 0;

    // broom
    private static final float BOUNCE_HEIGHT = 6f;
    private static final float BOUNCE_INTERVAL = 4f;
    private float broomTime;
    private float broomy;

    public boolean up, down;

    public Player(Context context) {
        super(context);

        animation = new Animation(context.getImages("witch"), 0.2f);
    }

    public void dash() {
        dashTime = MAX_DASH_TIME;
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
        dx += ACCEL;

        // clamp max speed, dx clamps to dash
        float maxSpeed = dashTime > 0 ? MAX_DASH_SPEED : MAX_SPEED;
        if (dy > MAX_SPEED) dy = MAX_SPEED;
        if (dy < -MAX_SPEED) dy = -MAX_SPEED;
        if (dx < -maxSpeed) dx = -maxSpeed;
        if (dx > maxSpeed) dx = maxSpeed;

        // move
        x += dx * dt;
        y += dy * dt;

        // update dash
        if (dashTime > 0) {
            dashTime -= dt;
            if (dashTime < 0) dashTime = 0;
        }

        broomTime += dt;
        broomy = MathUtils.sin(broomTime * BOUNCE_INTERVAL) * BOUNCE_HEIGHT;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        setImage(animation.getImage());
        Utils.drawCentered(sb, image, x, y + broomy);
    }

}
