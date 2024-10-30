package com.distraction.ttd2024;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.distraction.ttd2024.screen.PlayScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private Context context;

    private static final float TICK = 1f / 60f;
    private float accum;

    @Override
    public void create() {
        Data.load();

        context = new Context();
        context.sm.push(new PlayScreen(context));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        accum += Gdx.graphics.getDeltaTime();
        while (accum > TICK) {
            accum -= TICK;
            context.sm.update(TICK);
        }
        context.sm.render();
    }

    @Override
    public void dispose() {
        context.dispose();
    }
}
