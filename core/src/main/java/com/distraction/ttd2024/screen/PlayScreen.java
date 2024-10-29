package com.distraction.ttd2024.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.Data;
import com.distraction.ttd2024.UI;
import com.distraction.ttd2024.Utils;
import com.distraction.ttd2024.entity.Background;
import com.distraction.ttd2024.entity.Collectable;
import com.distraction.ttd2024.entity.Particle;
import com.distraction.ttd2024.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends Screen {

    private static final float BUBBLE_INTERVAL = 0.1f;
    private static final float TOTAL_DISTANCE = 6000;

    private float time;

    private final UI ui;

    private final Player player;
    private final Background bg;

    private List<Collectable> collectables;
    private List<Particle> particles;

    private BitmapFont font = new BitmapFont();

    private float bubbleTime;

    public PlayScreen(Context context) {
        super(context);

        ui = new UI(context, TOTAL_DISTANCE);

        player = new Player(context);
        player.x = 0;
        player.y = Constants.HEIGHT / 2f;

        bg = new Background(context, player, TOTAL_DISTANCE);

        collectables = new ArrayList<>();
        for (Data data : Data.DATA) {
            collectables.add(new Collectable(context, data.type, data.x, data.y));
        }

        particles = new ArrayList<>();
    }

    private void hit() {
        List<Collectable.Type> hitList = player.hit();
        for (int i = 0; i < hitList.size(); i++) {
            Collectable.Type c = hitList.get(i);
            Particle.Type p;
            if (c == Collectable.Type.SOUL) p = Particle.Type.SOUL;
            else if (c == Collectable.Type.BIGSOUL) p = Particle.Type.BIGSOUL;
            else continue;
            float dx = 50;
            float dy = 50;
            if (i == 1) dx *= -1;
            else if (i == 2) dy *= -1;
            else if (i == 3) {
                dx *= -1;
                dy *= -1;
            }
            particles.add(new Particle(context, p, player.truex(), player.truey(), player.dx + dx, dy));
        }
    }

    @Override
    public void update(float dt) {
        time += dt;

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            context.sm.replace(new PlayScreen(context));
        }
        player.up = Gdx.input.isKeyPressed(Input.Keys.UP);
        player.down = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        player.left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        player.right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);

        player.update(dt);

        cam.position.x = player.x;
        cam.update();

        ui.currentDistance = player.x;

        bg.update(dt);

        for (int i = 0; i < collectables.size(); i++) {
            Collectable collectable = collectables.get(i);
            boolean collided;
            if (collectable.type == Collectable.Type.SPIKE) {
                collided = collectable.contains(player.truex(), player.truey());
                if (collided) {
                    hit();
                }
            } else {
                collided = collectable.overlaps(player.truex(), player.y, player.w, player.h);
                if (collided) player.collect(collectable.type);
            }
            if (collided || player.x - collectable.x > Constants.WIDTH) {
                collectables.remove(i--);
            }

        }

        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.update(dt);
            if (player.x - particle.x > Constants.WIDTH || particle.remove) {
                particles.remove(i--);
            }
        }

        bubbleTime += dt;
        while (bubbleTime > BUBBLE_INTERVAL) {
            bubbleTime -= BUBBLE_INTERVAL;
            particles.add(new Particle(context, Particle.Type.BUBBLE, player.truex() - 26, player.truey() - 12));
        }
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(uiCam.combined);
        bg.render(sb);

        sb.setProjectionMatrix(cam.combined);
        player.render(sb);
        for (Collectable collectable : collectables) {
            collectable.render(sb);
        }
        for (Particle particle : particles) {
            particle.render(sb);
        }

        sb.setProjectionMatrix(uiCam.combined);
        ui.render(sb);

//        font.draw(sb, "time: " + String.format("%.1f", 20f - time), 3, 15);
        font.draw(sb, "score: " + player.score, 3, 15);
        sb.end();
    }
}
