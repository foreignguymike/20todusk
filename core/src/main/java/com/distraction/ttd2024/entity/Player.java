package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.ttd2024.Animation;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.Utils;

public class Player extends Entity {

    // moving
    private static final float ACCEL = 6;
    private static final float FRICTION = 2;
    private static final float MAX_SPEED = 300;

    // dashing
    private static final float MAX_DASH_TIME = 3f;
    private static final float MAX_DASH_SPEED = 700;
    private float dashTime = 0;

    // broom
    private static final float BOUNCE_HEIGHT = 6f;
    private static final float BOUNCE_INTERVAL = 4f;
    private float broomTime;
    private float broomy;

    // screen point
    public float sx;
    public float sdx;

    public boolean up, down, left, right;

    public Player(Context context) {
        super(context);

        animation = new Animation(context.getImages("witch"), 0.2f);
    }

    public void dash() {
        dashTime = MAX_DASH_TIME;
    }

    public float truex() {
        return x + sx;
    }

    public float truey() {
        return y + broomy;
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
        if (sdx > 0) {
            sdx -= FRICTION;
            if (sdx < 0) sdx = 0;
        } else if (sdx < 0) {
            sdx += FRICTION;
            if (sdx > 0) sdx = 0;
        }

        // velocity
        if (up) dy += ACCEL;
        if (down) dy -= ACCEL;
        dx += ACCEL;
        if (left) sdx -= ACCEL;
        if (right) sdx += ACCEL;

        // clamp max speed, dx clamps to dash
        float maxSpeed = dashTime > 0 ? MAX_DASH_SPEED : MAX_SPEED;
        dx = MathUtils.clamp(dx, -maxSpeed, maxSpeed);
        dy = MathUtils.clamp(dy, -MAX_SPEED, MAX_SPEED);
        sdx = MathUtils.clamp(sdx, -MAX_SPEED, MAX_SPEED);

        // move
        x += dx * dt;
        y += dy * dt;
        sx += sdx * dt;

        // clamp position
        y = Utils.clampCallback(y, h / 2 + 15, Constants.HEIGHT - h / 2f, () -> dy = 0);
        sx = Utils.clampCallback(sx, (w - Constants.WIDTH) / 2f, (Constants.WIDTH - w) / 2f, () -> sdx = 0);

        // update dash
        if (dashTime > 0) {
            dashTime -= dt;
            if (dashTime < 0) dashTime = 0;
        }

        // broom bounce
        broomTime += dt;
        broomy = MathUtils.sin(broomTime * BOUNCE_INTERVAL) * BOUNCE_HEIGHT;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        setImage(animation.getImage());
        Utils.drawCentered(sb, image, x + sx, y + broomy);
    }

}
