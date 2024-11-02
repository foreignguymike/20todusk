package com.distraction.ttd2024.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.entity.Entity;
import com.distraction.ttd2024.entity.FontEntity;

public class TitleScreen extends Screen {

    private final TextureRegion bg;
    private final Entity playButton;
    private final Entity scoresButton;
    private final Entity arrow;

    private final FontEntity playerFont;

    private final FontEntity errorFont;
    private float errorFontTime;

    private boolean first = true;
    private boolean fromLeaderboards;

    public TitleScreen(Context context) {
        super(context);

        bg = context.getImage("title");
        playButton = new Entity(context, context.getImage("play"), 250, 80);
        scoresButton = new Entity(context, context.getImage("scores"), 266, 55);
        arrow = new Entity(context, context.getImage("menuarrow"), 216, -100);

        ignoreInput = true;
        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f, () -> context.sm.push(new PlayScreen(context)));

        playerFont = new FontEntity(context, context.getFont(Context.FONT_NAME_M5X716));
        playerFont.setText("Player: " + context.data.name);
        playerFont.center = false;
        playerFont.x = 5;
        playerFont.y = Constants.HEIGHT - 10;

        errorFont = new FontEntity(context, context.getFont(Context.FONT_NAME_M5X716));
        errorFont.x = Constants.WIDTH / 2f;
        errorFont.y = 5;

        if (!context.leaderboardsInitialized && !context.leaderboardsRequested) {
            context.fetchLeaderboard((success) -> {
                errorFont.setText(success ? "leaderboards fetched" : "error fetching leaderboards");
                errorFontTime = 3f;
            });
        }
    }

    @Override
    public void resume() {
        ignoreInput = false;
        if (first) {
            first = false;
            in = new Transition(context, Transition.Type.FLASH_IN, 0.5f, () -> ignoreInput = false);
            in.start();
        } else if (!fromLeaderboards) {
            in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
            in.start();
        } else {
            fromLeaderboards = false;
        }
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);
        if (!ignoreInput) {
            unproject();
            if (playButton.contains(m.x, m.y, 3, 3)) arrow.y = playButton.y;
            else if (scoresButton.contains(m.x, m.y, 3, 3)) arrow.y = scoresButton.y;
            else arrow.y = -100;
            if (Gdx.input.isTouched()) {
                if (playButton.contains(m.x, m.y)) {
                    ignoreInput = true;
                    context.data.reset();
                    out.start();
                } else if (scoresButton.contains(m.x, m.y)) {
                    ignoreInput = true;
                    context.data.reset();
                    fromLeaderboards = true;
                    context.sm.push(new ScoreScreen(context));
                } else if (playerFont.contains(m.x, m.y, 5, 3)) {
                    ignoreInput = true;
                    out = new Transition(context, Transition.Type.FLASH_OUT, 0.5f, () -> context.sm.replace(new NameScreen(context)));
                    out.start();
                }
            }
        }
        errorFontTime -= dt;
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Color.WHITE);
        sb.draw(bg, 0, 0);
        playButton.render(sb);
        scoresButton.render(sb);
        arrow.render(sb);
        playerFont.render(sb);
        if (errorFontTime > 0) {
            errorFont.render(sb);
        }

        in.render(sb);
        out.render(sb);
        sb.end();
    }
}
