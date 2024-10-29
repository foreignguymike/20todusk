package com.distraction.ttd2024;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UI {

    public static final Color METER_COLOR = Color.valueOf("FF7A7D");

    private final float totalDistance;
    public float currentDistance;

    private TextureRegion pixel;
    private TextureRegion meter;
    private TextureRegion meterHead;
    private final float mx;
    private final float my;
    private final float mw;
    private final float mh;
    private float md;

    public UI(Context context, float totalDistance) {
        this.totalDistance = totalDistance;
        pixel = context.getPixel();
        meter = context.getImage("meter");
        meterHead = context.getImage("meterhead");

        mw = meter.getRegionWidth();
        mh = meter.getRegionHeight();
        mx = (Constants.WIDTH - mw) / 2f;
        my = Constants.HEIGHT - 20f;
    }

    public void render(SpriteBatch sb) {
        md = (mw - 2) * currentDistance / totalDistance;

        sb.setColor(Color.WHITE);
        sb.draw(meter, mx, my);
        sb.setColor(METER_COLOR);
        sb.draw(pixel, mx + 1, my + 1, md, mh - 2);
        sb.setColor(Color.WHITE);
        sb.draw(meterHead, mx + md, my - 2);
    }

}
