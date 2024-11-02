package com.distraction.ttd2024.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.Data;
import com.distraction.ttd2024.UI;
import com.distraction.ttd2024.entity.Background;
import com.distraction.ttd2024.entity.Collectable;
import com.distraction.ttd2024.entity.Entity;
import com.distraction.ttd2024.entity.FontEntity;
import com.distraction.ttd2024.entity.Particle;
import com.distraction.ttd2024.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends Screen {

    private static final float BUBBLE_INTERVAL = 0.1f;
    private static final float TOTAL_DISTANCE = 6000;

    private final UI ui;

    private final Player player;
    private final Background bg;

    private final List<Collectable> collectables;
    private final List<Particle> particles;

    private float bubbleTime;
    private float collectSoundTime;

    private boolean done;

    // buttons
    private Entity backButton;
    private Entity restartButton;

    // replay and save
    private FontEntity replayFont;
    private boolean isReplay;
    private int[] replay = null;
    private int replayIndex = 0;
    private final List<Integer> save = new ArrayList<>();
    private final boolean[] downs = new boolean[4];
    private int tick;

    public PlayScreen(Context context, int[] replay) {
        this(context);
        isReplay = true;
        this.replay = replay;
        replayFont = new FontEntity(context, context.getFont(Context.FONT_NAME_VCR20));
        replayFont.setText("REPLAY");
        replayFont.x = Constants.WIDTH / 2f;
        replayFont.y = Constants.HEIGHT - 15;

        backButton.x = Constants.WIDTH - 14;
        backButton.y = Constants.HEIGHT - 15;
    }

    public PlayScreen(Context context) {
        super(context);

        player = new Player(context);
        player.x = 0;
        player.y = Constants.HEIGHT / 2f;

        bg = new Background(context, player, TOTAL_DISTANCE);

        collectables = new ArrayList<>();
        for (Data data : Data.DATA) {
            collectables.add(new Collectable(context, data.type, data.x, data.y));
        }

        particles = new ArrayList<>();

        ui = new UI(context, player, TOTAL_DISTANCE);

        backButton = new Entity(context, context.getImage("back"), Constants.WIDTH - 39, Constants.HEIGHT - 15);
        restartButton = new Entity(context, context.getImage("restart"), Constants.WIDTH - 14, Constants.HEIGHT - 15);

        ignoreInput = true;
        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f, () -> context.sm.replace(new PlayScreen(context)));
    }

    private void hit() {
        List<Collectable.Type> hitList = player.hit();
        context.audio.playSound("hit");
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

    private void saveInput(int tick, int input, boolean down) {
        save.add(tick);
        save.add(input * 2 + (down ? 1 : 0));
        downs[input] = down;
    }

    private void checkReplay(int tick) {
        if (replayIndex >= replay.length) return;
        while (replay[replayIndex] == tick) {
            int code = replay[replayIndex + 1];
            downs[code / 2] = code % 2 == 1;
            replayIndex += 2;
            if (replayIndex >= replay.length) break;
        }
    }

    @Override
    public void update(float dt) {
        tick++;

        in.update(dt);
        out.update(dt);

        if (!ignoreInput) {
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                ignoreInput = true;
                context.data.set("", player.score, save);
                context.sm.push(new ScoreScreen(context));
            }

            if (Gdx.input.isTouched()) {
                unproject();
                if (backButton.contains(m.x, m.y)) {
                    ignoreInput = true;
                    out.setCallback(() -> context.sm.pop());
                    out.start();
                }
                if (!isReplay && restartButton.contains(m.x, m.y)) {
                    ignoreInput = true;
                    context.data.reset();
                    out.setCallback(() -> context.sm.replace(new PlayScreen(context)));
                    out.start();
                }
            }

            if (!done) {
                boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
                boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN);
                boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
                boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
                if (isReplay) {
                    checkReplay(tick);
                    player.up = downs[0];
                    player.down = downs[1];
                    player.left = downs[2];
                    player.right = downs[3];
                } else {
                    if (downs[0] != up) saveInput(tick, 0, up);
                    if (downs[1] != down) saveInput(tick, 1, down);
                    if (downs[2] != left) saveInput(tick, 2, left);
                    if (downs[3] != right) saveInput(tick, 3, right);
                    player.up = up;
                    player.down = down;
                    player.left = left;
                    player.right = right;
                }
            }
        }

        player.update(dt);

        cam.position.x = player.x;
        cam.update();

        ui.currentDistance = player.x;

        bg.update(dt);

        if (!ignoreInput) {
            for (int i = 0; i < collectables.size(); i++) {
                Collectable collectable = collectables.get(i);
                boolean collided;
                if (collectable.type == Collectable.Type.SPIKE) {
                    collided = collectable.contains(player.truex(), player.truey()) && !player.isHit();
                    if (collided) hit();
                } else {
                    collided = collectable.overlaps(player.truex(), player.truey(), player.w, player.h);
                    if (collided) {
                        player.collect(collectable.type);
                        if (collectSoundTime > 0.05f) {
                            collectSoundTime = 0;
                            float volume = collectable.type == Collectable.Type.TWOX ? 0.5f : 1f;
                            context.audio.playSoundCut(collectable.type.sound, volume);
                        }
                    }
                }
                if (collided || player.x - collectable.x > Constants.WIDTH) {
                    collectables.remove(i--);
                    ui.updateScore();
                }
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

        collectSoundTime += dt;

        // done
        if (!done && player.x >= TOTAL_DISTANCE) {
            done = true;
            context.sm.pop();
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
        backButton.render(sb);
        if (!isReplay) restartButton.render(sb);

        if (isReplay) {
            replayFont.render(sb);
        }

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}
