package com.distraction.ttd2024.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
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
    private float doneAlpha;

    // buttons
    private final Entity backButton;
    private final Entity restartButton;
    private final Entity submitButton;

    private final FontEntity doneText;
    private final FontEntity submittedFont;
    private boolean loading = false;
    private float time;

    private final Entity leaderboardsButton;
    private boolean fromLeaderboards;

    // replay and save
    private FontEntity nameFont;
    private FontEntity replayFont;
    private boolean isReplay;
    private int[] replay = null;
    private int replayIndex = 0;
    private final List<Integer> save = new ArrayList<>();
    private final boolean[] downs = new boolean[4];
    private int tick;

    // music next (to handle skips)
    private boolean musicKeyDown;

    public PlayScreen(Context context, String name, int[] replay) {
        this(context);
        isReplay = true;
        this.replay = replay;
        replayFont = new FontEntity(context, context.getFont(Context.FONT_NAME_M5X716));
        replayFont.setText("REPLAY");
        replayFont.x = Constants.WIDTH / 2f;
        replayFont.y = Constants.HEIGHT - 15;

        backButton.x = Constants.WIDTH - 14;
        backButton.y = Constants.HEIGHT - 15;

        nameFont = new FontEntity(context, context.getFont(Context.FONT_NAME_M5X716));
        nameFont.setText("Player: " + name);
        nameFont.x = Constants.WIDTH / 2f;
        nameFont.y = 14;
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
        submitButton = new Entity(context, context.getImage("submit"), Constants.WIDTH / 2f, Constants.HEIGHT / 2f - 30);

        ignoreInput = true;
        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f, () -> context.sm.replace(new PlayScreen(context)));

        doneText = new FontEntity(context, context.getFont(Context.FONT_NAME_VCR20));
        doneText.x = Constants.WIDTH / 2f;
        doneText.y = Constants.HEIGHT / 2f;

        submittedFont = new FontEntity(context, context.getFont(Context.FONT_NAME_M5X716));
        submittedFont.setText("Submitted!");
        submittedFont.x = submitButton.x;
        submittedFont.y = submitButton.y - 4;

        leaderboardsButton = new Entity(context, context.getImage("leaderboardsbutton"), Constants.WIDTH / 2f, 30);

        if (!context.audio.isMusicPlaying()) {
            context.audio.nextMusic(0.2f, true);
        }
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

    private void submit() {
        if (context.data.name.isEmpty() || !context.leaderboardsInitialized) return;
        if (context.data.submitted) return;
        if (loading) return;
        loading = true;
        context.submitScore(context.data.name, context.data.score, serializeSave(context.data.save), new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String res = httpResponse.getResultAsString();
                // throwing an exception with SubmitScoreResponse here for some reason
                // just doing a sus true check instead
                if (res.contains("true")) {
                    context.data.submitted = true;
                    context.fetchLeaderboard((success) -> {});
                } else {
                    failed(null);
                }
                loading = false;
            }

            @Override
            public void failed(Throwable t) {
                ignoreInput = false;
                loading = false;
            }

            @Override
            public void cancelled() {
                failed(null);
            }
        });
    }

    private String serializeSave(List<Integer> save) {
        StringBuilder ret = new StringBuilder();
        if (!save.isEmpty()) {
            ret = new StringBuilder(save.get(0).toString());
        }
        for (int i = 1; i < save.size(); i++) {
            ret.append(",").append(save.get(i).toString());
        }
        return ret.toString();
    }

    private void restart() {
        context.data.reset();
        out.setCallback(() -> {
            context.data.reset();
            context.sm.replace(new PlayScreen(context));
        });
        out.start();
    }

    @Override
    public void resume() {
        if (fromLeaderboards) {
            ignoreInput = false;
            fromLeaderboards = false;
        }
    }

    @Override
    public void update(float dt) {
        tick++;
        time += dt;

        in.update(dt);
        out.update(dt);

        if (!ignoreInput) {
            if (Gdx.input.isTouched()) {
                unproject();
                if (backButton.contains(m.x, m.y)) {
                    ignoreInput = true;
                    out.setCallback(() -> context.sm.pop());
                    out.start();
                }
                if (!isReplay && restartButton.contains(m.x, m.y)) {
                    ignoreInput = true;
                    restart();
                }
                if (done) {
                    if (submitButton.contains(m.x, m.y)) {
                        submit();
                    }
                    if (leaderboardsButton.contains(m.x, m.y)) {
                        ignoreInput = true;
                        fromLeaderboards = true;
                        context.sm.push(new ScoreScreen(context));
                    }
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                ignoreInput = true;
                restart();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.M) && !musicKeyDown) {
                musicKeyDown = true;
                context.audio.nextMusic(0.2f, true);
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.M)) musicKeyDown = false;

            if (!done) {
                boolean up = Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W);
                boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S);
                boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A);
                boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);
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
                            context.audio.playSoundCut(collectable.type.sound, 1f);
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
            player.up = player.down = player.left = player.right = false;
            if (isReplay) {
                out.setCallback(() -> context.sm.pop());
                out.start();
            }
            if (context.isHighscore(context.data.name, player.score)) {
                doneText.setText("HIGH SCORE!");
                context.data.set(player.score, save);
            } else {
                doneText.setText("Try again! :)");
            }
        }
        if (done && !isReplay) {
            doneAlpha = MathUtils.clamp(doneAlpha + dt, 0, 0.9f);
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

        if (isReplay) {
            replayFont.render(sb);
            nameFont.render(sb);
        }

        if (done && !isReplay) {
            sb.setColor(0, 0, 0, doneAlpha);
            sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);

            sb.setColor(Color.WHITE);
            doneText.render(sb);

            if (context.isHighscore(context.data.name, player.score)) {
                if (!context.data.submitted) {
                    submitButton.render(sb);
                }
            }
            if (context.data.submitted) {
                submittedFont.render(sb);
            }
            leaderboardsButton.render(sb);
        }

        backButton.render(sb);
        if (!isReplay) restartButton.render(sb);
        ui.renderScore(sb);

        if (loading) {
            for (int i = 0; i < 5; i++) {
                float x = submitButton.x + submitButton.w / 2f + 10 * MathUtils.cos(-6f * time + i * 0.1f) - 5;
                float y = submitButton.y + 10 * MathUtils.sin(-6f * time + i * 0.1f) - 5;
                sb.draw(pixel, x, y, 2, 2);
            }
        }

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}
