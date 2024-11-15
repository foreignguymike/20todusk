package com.distraction.ttd2024;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.ttd2024.entity.FontEntity;
import com.distraction.ttd2024.entity.Player;

public class UI {

    public static final Color METER_COLOR = Color.valueOf("FF7A7D");

    private final Player player;

    private final float totalDistance;
    public float currentDistance;

    private final TextureRegion pixel;
    private final TextureRegion meter;
    private final TextureRegion meterHead;
    private final float mx;
    private final float my;
    private final float mw;
    private final float mh;

    private final FontEntity font;

    private final TextureRegion twox;

    public UI(Context context, Player player, float totalDistance) {
        this.player = player;
        this.totalDistance = totalDistance;

        pixel = context.getPixel();
        meter = context.getImage("meter");
        meterHead = context.getImage("meterhead");

        mw = meter.getRegionWidth();
        mh = meter.getRegionHeight();
        mx = (Constants.WIDTH - mw) / 2f;
        my = 4f;

        font = new FontEntity(context, context.getFont(Context.FONT_NAME_VCR20), "0", 5, Constants.HEIGHT - 15);

        twox = context.getImage("2x");
    }

    public void updateScore() {
        font.setText(player.score + "");
    }

    public void render(SpriteBatch sb) {
        float md = (mw - 2) * MathUtils.clamp(currentDistance / totalDistance, 0, 1);

        sb.setColor(Color.WHITE);
        sb.draw(meter, mx, my);
        sb.setColor(METER_COLOR);
        sb.draw(pixel, mx + 1, my + 1, md, mh - 2);
        sb.setColor(Color.WHITE);
        sb.draw(meterHead, mx + md, my - 2);

        if (player.doubleTime > 0) {
            sb.draw(twox, 4, 2);
        }
    }

    public void renderScore(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        font.render(sb);
    }

}
