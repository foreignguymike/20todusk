package com.distraction.ttd2024;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.distraction.ttd2024.entity.Collectable;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public final Collectable.Type type;
    public final float x;
    public final float y;

    public Data(Collectable.Type type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public static Data[] DATA;

    public static void load() {
        List<Data> list = new ArrayList<>();

        Texture map = new Texture("map.png");
        if (!map.getTextureData().isPrepared()) {
            map.getTextureData().prepare();
        }
        Pixmap pixmap = map.getTextureData().consumePixmap();
        for (int row = 0; row < pixmap.getHeight(); row++) {
            for (int col = 0; col < pixmap.getWidth(); col++) {
                Color color = new Color(pixmap.getPixel(col, row));
                Collectable.Type type = null;
                if (color.equals(Color.BLACK)) type = Collectable.Type.SOUL;
                else if (color.equals(Color.RED)) type = Collectable.Type.BIGSOUL;
                else if (color.equals(Color.YELLOW)) type = Collectable.Type.TWOX;
                else if (color.equals(Color.GREEN)) type = Collectable.Type.SPIKE;
                if (type == null) continue;
                list.add(new Data(type, col * 10, (pixmap.getHeight() - row) * 10));
            }
        }
        DATA = new Data[list.size()];
        list.toArray(DATA);
    }

}
