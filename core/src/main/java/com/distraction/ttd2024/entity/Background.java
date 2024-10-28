package com.distraction.ttd2024.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;

public class Background extends Entity {

    private static final Color BG = Color.valueOf("D46453");

    private final Player player;
    private final TextureRegion[] images;
    private final float[] parallax = new float[]{26f, 18f, 10f};
    private final float[] xs = new float[3];
    private final float[] heights = new float[]{130, 120, 110};
    private final Color[] colors = new Color[]{
        Color.valueOf("8F5765"),
        Color.valueOf("392946"),
        Color.valueOf("0F022E")
    };

    private final TextureRegion pixel;

    public Background(Context context, Player player) {
        super(context);
        this.player = player;

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
        for (int i = 0; i < images.length; i++) {
            sb.setColor(Color.WHITE);
            sb.draw(images[i], xs[i], heights[i]);
            sb.draw(images[i], xs[i] + w, heights[i]);
            sb.setColor(colors[i]);
            sb.draw(pixel, 0, 0, w, heights[i]);
        }
    }
}
