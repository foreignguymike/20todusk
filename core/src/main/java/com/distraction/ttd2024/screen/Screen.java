package com.distraction.ttd2024.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;

public abstract class Screen {

    protected Context context;
    protected SpriteBatch sb;

    protected final OrthographicCamera cam;
    protected final OrthographicCamera uiCam;
    protected final Vector3 m;

    protected boolean ignoreInput;

    protected final TextureRegion pixel;

    protected Transition in = null;
    protected Transition out = null;

    protected Screen(Context context) {
        this.context = context;
        sb = context.sb;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);
        uiCam = new OrthographicCamera();
        uiCam.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);
        m = new Vector3();

        pixel = context.getPixel();
    }

    protected void unproject() {
        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        uiCam.unproject(m);
    }

    protected void unproject(float x, float y) {
        m.set(x, y, 0);
        uiCam.unproject(m);
    }

    public void resume() {}

    public void pause() {}

    public abstract void update(float dt);

    public abstract void render();

}
