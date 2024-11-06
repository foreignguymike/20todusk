package com.distraction.ttd2024.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.entity.Entity;
import com.distraction.ttd2024.entity.FontEntity;

public class TitleScreen extends Screen {

    private static final Color BG_COLOR1 = Color.valueOf("8F5765");
    private static final Color BG_COLOR2 = Color.valueOf("CF968C");

    private final TextureRegion bg1;
    private final TextureRegion bg2;
    private final TextureRegion title;
    private final Entity playButton;
    private final Entity scoresButton;
    private final Entity arrow;

    private final FontEntity playerFont;
    private final FontEntity engineFont;
    private final FontEntity creditsFont;
    private final FontEntity versionFont;

    private final FontEntity errorFont;
    private float errorFontTime;

    private boolean first = true;
    private boolean fromLeaderboards;

    public TitleScreen(Context context) {
        super(context);

        bg1 = context.getImage("titlebg1");
        bg2 = context.getImage("titlebg2");
        title = context.getImage("title");
        playButton = new Entity(context, context.getImage("play"), 250, 80);
        scoresButton = new Entity(context, context.getImage("scores"), 266, 55);
        arrow = new Entity(context, context.getImage("menuarrow"), 216, -100);

        ignoreInput = true;
        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f, () -> context.sm.push(new PlayScreen(context)));

        playerFont = new FontEntity(context, context.getFont(Context.FONT_NAME_M5X716), "Player: " + context.data.name, 5, Constants.HEIGHT - 10);
        playerFont.center = false;
        engineFont = new FontEntity(context, context.getFont(Context.FONT_NAME_M5X716), "libGDX", 4, 15);
        engineFont.center = false;
        creditsFont = new FontEntity(context, context.getFont(Context.FONT_NAME_M5X716), "mike 2024", 4, 5);
        creditsFont.center = false;
        versionFont = new FontEntity(context, context.getFont(Context.FONT_NAME_M5X716), Constants.VERSION_STRING, Constants.WIDTH - 20, 5);

        errorFont = new FontEntity(context, context.getFont(Context.FONT_NAME_M5X716));
        errorFont.x = Constants.WIDTH / 2f;
        errorFont.y = 5;

        if (!context.leaderboardsInitialized && !context.leaderboardsRequesting) {
            errorFont.setText("fetching leaderboards...");
            errorFontTime = 30f;
            context.fetchLeaderboard((success) -> {
                errorFont.setText(success ? "leaderboards fetched" : "error fetching leaderboards");
                errorFontTime = 3f;
            });
        }
    }

    @Override
    public void resume() {
        if (first) {
            first = false;
            in = new Transition(context, Transition.Type.FLASH_IN, 0.5f, () -> ignoreInput = false);
            in.start();
        } else if (!fromLeaderboards) {
            in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
            in.start();
        } else {
            ignoreInput = false;
            fromLeaderboards = false;
        }
        context.audio.stopMusic();
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
                if (playButton.contains(m.x, m.y, 3, 3)) {
                    ignoreInput = true;
                    context.data.reset();
                    out.start();
                } else if (scoresButton.contains(m.x, m.y, 3, 3)) {
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

        sb.setColor(BG_COLOR1);
        sb.draw(pixel, 0, 0, Constants.WIDTH, 23);
        sb.draw(pixel, 0, Constants.HEIGHT - 23, Constants.WIDTH, 23);
        sb.setColor(BG_COLOR2);
        sb.draw(pixel, 0, 39, Constants.WIDTH, 102);
        sb.setColor(Color.WHITE);
        sb.draw(bg1, 0, Constants.HEIGHT - 39);
        sb.draw(bg2, 0, 23);
        sb.draw(title, 19, 30);

        playButton.render(sb);
        scoresButton.render(sb);
        arrow.render(sb);
        playerFont.render(sb);
        engineFont.render(sb);
        creditsFont.render(sb);
        versionFont.render(sb);
        if (errorFontTime > 0) {
            errorFont.render(sb);
        }

        in.render(sb);
        out.render(sb);
        sb.end();
    }
}
