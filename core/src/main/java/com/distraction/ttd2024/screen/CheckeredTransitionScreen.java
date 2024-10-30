package com.distraction.ttd2024.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;

class CheckeredTransitionScreen extends TransitionScreen {

    public CheckeredTransitionScreen(Context context, Screen nextScreen) {
        this(context, nextScreen, 1);
    }

    public CheckeredTransitionScreen(Context context, Screen nextScreen, int numPop) {
        super(context, nextScreen, numPop);
        duration = 1.3f;
    }

    @Override
    public void render() {
        sb.begin();
        sb.setColor(Color.BLACK);
        sb.setProjectionMatrix(uiCam.combined);
        float squareSize = Constants.WIDTH / 16f;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 16; col++) {
                float size;
                float ttime = time - ((9 - row + col) / 40f) * (duration / 2);
                if (time < duration / 2) size = squareSize * (ttime / (duration / 6));
                else size = squareSize - squareSize * ((ttime - duration / 2) / (duration / 6));
                size = MathUtils.clamp(size, 0, squareSize);
                sb.draw(pixel, squareSize * 0.5f + squareSize * col - size / 2, squareSize * 0.5f + squareSize * row - size / 2, size, size);
            }
        }
        sb.end();
    }
}

