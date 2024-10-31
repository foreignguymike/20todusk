package com.distraction.ttd2024.screen;

import com.badlogic.gdx.graphics.Color;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;

public class TransitionScreen extends Screen {

    private final Screen nextScreen;
    private final int numPop;

    protected float duration = 0.5f;
    protected float time = 0f;
    protected boolean next = false;

    public TransitionScreen(Context context, Screen nextScreen) {
        this(context, nextScreen, 1);
    }

    public TransitionScreen(Context context, Screen nextScreen, int numPop) {
        super(context);
        this.nextScreen = nextScreen;
        this.numPop = numPop;
        context.sm.depth++;
    }

    @Override
    public void update(float dt) {
        time += dt;
        nextScreen.ignoreInput = time < duration;
        if (!next && time > duration / 2) {
            next = true;
            for (int i = 0; i < numPop; i++) context.sm.pop();
            context.sm.depth -= numPop - 1;
            context.sm.replace(nextScreen);
            context.sm.push(this);
        }
        if (time > duration) {
            context.sm.depth--;
            context.sm.pop();
        }
    }

    @Override
    public void render() {
        float interp = time / duration;
        float perc = interp < 0.5f ? interp * 2 : 1f - (time - duration / 2) / duration * 2;
        Color c = sb.getColor();
        sb.setColor(Color.BLACK);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(pixel, 0, Constants.HEIGHT, Constants.WIDTH, -perc * Constants.HEIGHT / 2);
        sb.draw(pixel, 0, 0, Constants.WIDTH, perc * Constants.HEIGHT / 2);
        sb.end();
        sb.setColor(c);
    }
}

