package com.distraction.ttd2024;

import com.distraction.ttd2024.entity.Collectable;

public class Data {

    public final Collectable.Type type;
    public final float x;
    public final float y;

    public Data(Collectable.Type type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public static final Data[] DATA = new Data[]{
        new Data(Collectable.Type.SOUL, 1000, 120),
        new Data(Collectable.Type.SOUL, 1040, 120),
        new Data(Collectable.Type.SOUL, 1080, 120),
        new Data(Collectable.Type.SOUL, 1120, 120),

        new Data(Collectable.Type.SOUL, 1500, 60),
        new Data(Collectable.Type.SOUL, 1540, 60),
        new Data(Collectable.Type.SOUL, 1580, 60),
        new Data(Collectable.Type.SOUL, 1620, 60),

        new Data(Collectable.Type.SOUL, 2000, 120),
        new Data(Collectable.Type.SOUL, 2040, 110),
        new Data(Collectable.Type.SOUL, 2080, 100),
        new Data(Collectable.Type.SOUL, 2120, 90),

    };

}
