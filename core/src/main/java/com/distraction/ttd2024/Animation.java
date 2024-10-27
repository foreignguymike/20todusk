package com.distraction.ttd2024;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {

    private final TextureRegion[] images;

    private int index;
    private float time;
    private float interval;

    public Animation(TextureRegion[] images) {
        this(images, -1);
    }

    public Animation(TextureRegion[] images, float interval) {
        this.images = images;
        this.interval = interval;
    }

    public void update(float dt) {
        if (interval < 0) return;
        time += dt;
        while (time > interval) {
            time -= interval;
            index++;
            if (index >= images.length) index = 0;
        }
    }

    public TextureRegion getImage() {
        return images[index];
    }

}
