package com.distraction.ttd2024.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;

public abstract class Screen {

    protected Context context;
    protected SpriteBatch sb;

    protected OrthographicCamera cam;
    protected OrthographicCamera uiCam;

    protected Screen(Context context) {
        this.context = context;
        sb = context.sb;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);
        uiCam = new OrthographicCamera();
        uiCam.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);
    }

    public abstract void update(float dt);

    public abstract void render();

}
