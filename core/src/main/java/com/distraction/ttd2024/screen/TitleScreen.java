package com.distraction.ttd2024.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.entity.Entity;

public class TitleScreen extends Screen {

    private final TextureRegion bg;
    private final Entity playButton;
    private final Entity scoresButton;
    private final Entity arrow;

    public TitleScreen(Context context) {
        super(context);

        bg = context.getImage("title");
        playButton = new Entity(context, context.getImage("play"), 250, 80);
        scoresButton = new Entity(context, context.getImage("scores"), 266, 55);
        arrow = new Entity(context, context.getImage("menuarrow"), 216, -100);
    }

    @Override
    public void update(float dt) {
        if (!ignoreInput) {
            unproject();
            if (playButton.contains(m.x, m.y, 3, 3)) arrow.y = playButton.y;
            else if (scoresButton.contains(m.x, m.y, 3, 3)) arrow.y = scoresButton.y;
            else arrow.y = -100;
            if (Gdx.input.justTouched()) {
                if (playButton.contains(m.x, m.y)) {
                    ignoreInput = true;
                    context.data.reset();
                    context.sm.push(new CheckeredTransitionScreen(context, new PlayScreen(context)));
                } else if (scoresButton.contains(m.x, m.y)) {
                    ignoreInput = true;
                    context.data.reset();
                    context.sm.push(new CheckeredTransitionScreen(context, new ScoreScreen(context)));
                }
            }
        }
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
        sb.end();
    }
}
