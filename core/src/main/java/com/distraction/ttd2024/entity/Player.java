package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.ttd2024.Animation;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.Utils;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {

    // moving
    private static final float ACCEL = 1000;
    private static final float FRICTION = 500;
    private static final float MAX_SPEED = 300;

    // broom
    private static final float BOUNCE_HEIGHT = 6f;
    private static final float BOUNCE_INTERVAL = 4f;
    private float broomTime;
    private float broomy;

    // screen point
    public float sx;
    public float sdx;

    // input
    public boolean up, down, left, right;

    // collectables
    private final List<Collectable.Type> collectables;
    public int score;

    // 2x
    private static final Color GLOW = Color.valueOf("FFF540");
    private static final float DOUBLE_TIME = 3f;
    public float doubleTime;

    // hit
    private static final float HIT_INTERVAL = 1f;
    private float hitTime;

    public Player(Context context) {
        super(context);

        collectables = new ArrayList<>();
        animation = new Animation(context.getImages("witch"), 0.2f);

        sx = -100;
    }

    public float truex() {
        return x + sx;
    }

    public float truey() {
        return y + broomy;
    }

    public void collect(Collectable.Type type) {
        if (type == Collectable.Type.TWOX) doubleTime = DOUBLE_TIME;
        if (type.hasScore()) collectables.add(type);
        score += type.points * (doubleTime > 0 ? 2 : 1);
    }

    public List<Collectable.Type> hit() {
        List<Collectable.Type> remove = new ArrayList<>();

        if (hitTime > 0) return remove;
        hitTime = HIT_INTERVAL;

        if (!collectables.isEmpty()) remove.add(collectables.removeLast());
        if (!collectables.isEmpty()) remove.add(collectables.removeLast());
        if (!collectables.isEmpty()) remove.add(collectables.removeLast());
        if (!collectables.isEmpty()) remove.add(collectables.removeLast());
        for (Collectable.Type type : remove) {
            score -= type.points;
        }
        return remove;
    }

    @Override
    public void update(float dt) {
        animation.update(dt);

        dx = MAX_SPEED;

        float friction = FRICTION * dt;
        // friction
        if (dy > 0) {
            dy -= friction;
            if (dy < 0) dy = 0;
        } else if (dy < 0) {
            dy += friction;
            if (dy > 0) dy = 0;
        }
        if (sdx > 0) {
            sdx -= friction;
            if (sdx < 0) sdx = 0;
        } else if (sdx < 0) {
            sdx += friction;
            if (sdx > 0) sdx = 0;
        }

        // velocity
        float maxAccel = ACCEL * dt;
        if (up) dy += maxAccel;
        if (down) dy -= maxAccel;
        if (left) sdx -= maxAccel;
        if (right) sdx += maxAccel;

        // clamp max speed, dx clamps to dash
        dy = MathUtils.clamp(dy, -MAX_SPEED, MAX_SPEED);
        sdx = MathUtils.clamp(sdx, -MAX_SPEED, MAX_SPEED);

        // move
        x += dx * dt;
        y += dy * dt;
        sx += sdx * dt;

        // clamp position
        y = Utils.clampCallback(y, h / 2 + 15, Constants.HEIGHT - h / 2f, () -> dy = 0);
        sx = Utils.clampCallback(sx, (w - Constants.WIDTH) / 2f, (Constants.WIDTH - w) / 2f, () -> sdx = 0);

        // broom bounce
        broomTime += dt;
        broomy = MathUtils.sin(broomTime * BOUNCE_INTERVAL) * BOUNCE_HEIGHT;

        // hit
        hitTime -= dt;

        // double
        doubleTime -= dt;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (hitTime > 0) {
            if (hitTime % 0.2f < 0.1f) return;
            float hitAngle = -2 * MathUtils.PI * hitTime / HIT_INTERVAL;
            Utils.drawCentered(sb, image, x + sx, y + broomy, hitAngle);
            return;
        }
        setImage(animation.getImage());
        Utils.drawCentered(sb, image, x + sx, y + broomy);
    }

}
