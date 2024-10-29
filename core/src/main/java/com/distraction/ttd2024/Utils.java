package com.distraction.ttd2024;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

public class Utils {

    private static final Random random = new Random();

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y) {
        sb.draw(image, x - image.getRegionWidth() / 2f, y - image.getRegionHeight() / 2f);
    }

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y, float rad) {
        sb.draw(
            image,
            x - image.getRegionWidth() / 2f,
            y - image.getRegionHeight() / 2f,
            image.getRegionWidth() / 2f,
            image.getRegionHeight() / 2f,
            image.getRegionWidth(),
            image.getRegionHeight(),
            1,
            1,
            rad * MathUtils.radiansToDegrees
        );
    }

    public static float clampCallback(float value, float min, float max, Runnable r) {
        if (value < min) {
            r.run();
            return min;
        }
        if (value > max) {
            r.run();
            return max;
        }
        return value;
    }

    public static int rint(int max) {
        return random.nextInt(max);
    }

}
