package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;

public class Background extends Entity {

    private static final Color BG = Color.valueOf("D46453");

    private final Player player;
    private final float totalDistance;

    private final TextureRegion[] images;
    private final float[] parallax = new float[]{26f, 18f, 10f, 1f};
    private final float[] xs = new float[4];
    private final float[] heights = new float[]{110, 100, 90, 10};
    private final Color[] colors = new Color[]{
        Color.valueOf("8F5765"),
        Color.valueOf("392946"),
        Color.valueOf("0F022E"),
        Color.valueOf("2C645E")
    };

    private final TextureRegion pixel;

    public Background(Context context, Player player, float totalDistance) {
        super(context);
        this.player = player;
        this.totalDistance = totalDistance;

        images = context.getImages("mountains");
        setImage(images[0]);

        pixel = context.getPixel();
    }

    @Override
    public void update(float dt) {
        for (int i = 0; i < images.length; i++) {
            xs[i] = (-player.x / parallax[i]) % Constants.WIDTH;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        sb.setColor(0, 0, MathUtils.clamp(0.2f * player.x / totalDistance, 0, 0.2f), MathUtils.clamp(0.9f * player.x / totalDistance, 0, 0.9f));
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        for (int i = 0; i < images.length; i++) {
            sb.setColor(Color.WHITE);
            sb.draw(images[i], xs[i], heights[i], images[i].getRegionWidth() + 0.5f, images[i].getRegionHeight());
            sb.draw(images[i], xs[i] + w, heights[i], images[i].getRegionWidth() + 0.5f, images[i].getRegionHeight());
            sb.draw(images[i], xs[i] + 2 * w, heights[i], images[i].getRegionWidth() + 0.5f, images[i].getRegionHeight());
            sb.setColor(colors[i]);
            sb.draw(pixel, 0, 0, Constants.WIDTH, heights[i]);
        }
        sb.setColor(0, 0, 0, MathUtils.clamp(0.75f * player.x / totalDistance, 0, 0.75f));
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
    }
}
