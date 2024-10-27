package com.distraction.ttd2024;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ttd2024.screen.ScreenManager;

public class Context {

    public SpriteBatch sb;

    public ScreenManager sm;

    public Context() {
        sb = new SpriteBatch();
        sm = new ScreenManager();
    }

    public void dispose() {
        sb.dispose();
    }

}
