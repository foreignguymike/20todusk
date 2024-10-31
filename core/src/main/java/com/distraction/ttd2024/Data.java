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

    public static Data[] DATA = new Data[] {
        new Data(Collectable.Type.BIGSOUL, 1810, 150),
        new Data(Collectable.Type.SPIKE, 3250, 150),
        new Data(Collectable.Type.SOUL, 3350, 150),
        new Data(Collectable.Type.SPIKE, 3450, 150),
        new Data(Collectable.Type.BIGSOUL, 3560, 150),
        new Data(Collectable.Type.SPIKE, 3660, 150),
        new Data(Collectable.Type.SOUL, 3760, 150),
        new Data(Collectable.Type.SOUL, 3860, 150),
        new Data(Collectable.Type.SPIKE, 5640, 150),
        new Data(Collectable.Type.BIGSOUL, 5690, 150),
        new Data(Collectable.Type.BIGSOUL, 5720, 150),
        new Data(Collectable.Type.SOUL, 1110, 140),
        new Data(Collectable.Type.BIGSOUL, 1420, 140),
        new Data(Collectable.Type.SOUL, 1450, 140),
        new Data(Collectable.Type.SOUL, 1480, 140),
        new Data(Collectable.Type.SOUL, 1510, 140),
        new Data(Collectable.Type.SPIKE, 2050, 140),
        new Data(Collectable.Type.SOUL, 2210, 140),
        new Data(Collectable.Type.BIGSOUL, 2240, 140),
        new Data(Collectable.Type.SPIKE, 2410, 140),
        new Data(Collectable.Type.SOUL, 2780, 140),
        new Data(Collectable.Type.BIGSOUL, 4310, 140),
        new Data(Collectable.Type.BIGSOUL, 4340, 140),
        new Data(Collectable.Type.BIGSOUL, 4370, 140),
        new Data(Collectable.Type.SPIKE, 4420, 140),
        new Data(Collectable.Type.SOUL, 4680, 140),
        new Data(Collectable.Type.SOUL, 4710, 140),
        new Data(Collectable.Type.SOUL, 4740, 140),
        new Data(Collectable.Type.SOUL, 4770, 140),
        new Data(Collectable.Type.TWOX, 4950, 140),
        new Data(Collectable.Type.SOUL, 5300, 140),
        new Data(Collectable.Type.SOUL, 420, 130),
        new Data(Collectable.Type.SOUL, 450, 130),
        new Data(Collectable.Type.SOUL, 480, 130),
        new Data(Collectable.Type.SOUL, 510, 130),
        new Data(Collectable.Type.SPIKE, 790, 130),
        new Data(Collectable.Type.SOUL, 1140, 130),
        new Data(Collectable.Type.SOUL, 2180, 130),
        new Data(Collectable.Type.SOUL, 2270, 130),
        new Data(Collectable.Type.BIGSOUL, 2630, 130),
        new Data(Collectable.Type.SOUL, 3090, 130),
        new Data(Collectable.Type.SPIKE, 3250, 130),
        new Data(Collectable.Type.SOUL, 5260, 130),
        new Data(Collectable.Type.SOUL, 5340, 130),
        new Data(Collectable.Type.SOUL, 1170, 120),
        new Data(Collectable.Type.SOUL, 2890, 120),
        new Data(Collectable.Type.BIGSOUL, 3180, 120),
        new Data(Collectable.Type.SOUL, 1200, 110),
        new Data(Collectable.Type.SOUL, 2150, 110),
        new Data(Collectable.Type.SOUL, 2300, 110),
        new Data(Collectable.Type.SPIKE, 3250, 110),
        new Data(Collectable.Type.SOUL, 5370, 110),
        new Data(Collectable.Type.SPIKE, 1620, 100),
        new Data(Collectable.Type.TWOX, 1700, 100),
        new Data(Collectable.Type.SOUL, 1850, 100),
        new Data(Collectable.Type.SOUL, 1880, 100),
        new Data(Collectable.Type.SOUL, 1910, 100),
        new Data(Collectable.Type.SOUL, 2700, 100),
        new Data(Collectable.Type.BIGSOUL, 2850, 100),
        new Data(Collectable.Type.SOUL, 3000, 100),
        new Data(Collectable.Type.SOUL, 3350, 100),
        new Data(Collectable.Type.SOUL, 3560, 100),
        new Data(Collectable.Type.SOUL, 3760, 100),
        new Data(Collectable.Type.TWOX, 4030, 100),
        new Data(Collectable.Type.SPIKE, 5140, 100),
        new Data(Collectable.Type.SOUL, 5220, 100),
        new Data(Collectable.Type.SOUL, 5410, 100),
        new Data(Collectable.Type.SOUL, 5450, 100),
        new Data(Collectable.Type.SOUL, 5490, 100),
        new Data(Collectable.Type.SPIKE, 5560, 100),
        new Data(Collectable.Type.BIGSOUL, 610, 90),
        new Data(Collectable.Type.BIGSOUL, 940, 90),
        new Data(Collectable.Type.SPIKE, 1340, 90),
        new Data(Collectable.Type.SOUL, 1940, 90),
        new Data(Collectable.Type.SOUL, 2510, 90),
        new Data(Collectable.Type.SOUL, 2540, 90),
        new Data(Collectable.Type.BIGSOUL, 2570, 90),
        new Data(Collectable.Type.SPIKE, 3860, 90),
        new Data(Collectable.Type.SOUL, 4170, 90),
        new Data(Collectable.Type.SOUL, 4500, 90),
        new Data(Collectable.Type.SPIKE, 4550, 90),
        new Data(Collectable.Type.SOUL, 1970, 80),
        new Data(Collectable.Type.SOUL, 2120, 80),
        new Data(Collectable.Type.SOUL, 2330, 80),
        new Data(Collectable.Type.SOUL, 2480, 80),
        new Data(Collectable.Type.SOUL, 4200, 80),
        new Data(Collectable.Type.SOUL, 4470, 80),
        new Data(Collectable.Type.SPIKE, 5140, 80),
        new Data(Collectable.Type.BIGSOUL, 1200, 70),
        new Data(Collectable.Type.BIGSOUL, 2740, 70),
        new Data(Collectable.Type.SOUL, 2930, 70),
        new Data(Collectable.Type.SOUL, 3130, 70),
        new Data(Collectable.Type.SPIKE, 3860, 70),
        new Data(Collectable.Type.SOUL, 4230, 70),
        new Data(Collectable.Type.SOUL, 4440, 70),
        new Data(Collectable.Type.SOUL, 1170, 60),
        new Data(Collectable.Type.SOUL, 2000, 60),
        new Data(Collectable.Type.SOUL, 2090, 60),
        new Data(Collectable.Type.SOUL, 2360, 60),
        new Data(Collectable.Type.SOUL, 2450, 60),
        new Data(Collectable.Type.SOUL, 3040, 60),
        new Data(Collectable.Type.SOUL, 4260, 60),
        new Data(Collectable.Type.SOUL, 4410, 60),
        new Data(Collectable.Type.BIGSOUL, 4680, 60),
        new Data(Collectable.Type.BIGSOUL, 4720, 60),
        new Data(Collectable.Type.BIGSOUL, 4760, 60),
        new Data(Collectable.Type.BIGSOUL, 4950, 60),
        new Data(Collectable.Type.SPIKE, 5140, 60),
        new Data(Collectable.Type.BIGSOUL, 5280, 60),
        new Data(Collectable.Type.BIGSOUL, 5320, 60),
        new Data(Collectable.Type.SPIKE, 470, 50),
        new Data(Collectable.Type.SOUL, 740, 50),
        new Data(Collectable.Type.SOUL, 770, 50),
        new Data(Collectable.Type.SOUL, 800, 50),
        new Data(Collectable.Type.SOUL, 830, 50),
        new Data(Collectable.Type.SOUL, 1140, 50),
        new Data(Collectable.Type.SOUL, 1420, 50),
        new Data(Collectable.Type.SOUL, 1450, 50),
        new Data(Collectable.Type.SOUL, 1480, 50),
        new Data(Collectable.Type.BIGSOUL, 1510, 50),
        new Data(Collectable.Type.SOUL, 2030, 50),
        new Data(Collectable.Type.BIGSOUL, 2060, 50),
        new Data(Collectable.Type.SPIKE, 2230, 50),
        new Data(Collectable.Type.SOUL, 2390, 50),
        new Data(Collectable.Type.BIGSOUL, 2420, 50),
        new Data(Collectable.Type.SOUL, 2670, 50),
        new Data(Collectable.Type.SOUL, 2810, 50),
        new Data(Collectable.Type.SOUL, 3250, 50),
        new Data(Collectable.Type.SPIKE, 3450, 50),
        new Data(Collectable.Type.SOUL, 3560, 50),
        new Data(Collectable.Type.SPIKE, 3660, 50),
        new Data(Collectable.Type.SPIKE, 3860, 50),
        new Data(Collectable.Type.SOUL, 4290, 50),
        new Data(Collectable.Type.SOUL, 4320, 50),
        new Data(Collectable.Type.SOUL, 4350, 50),
        new Data(Collectable.Type.SOUL, 4380, 50),
        new Data(Collectable.Type.SPIKE, 5640, 50),
        new Data(Collectable.Type.SOUL, 5690, 50),
        new Data(Collectable.Type.SOUL, 5720, 50),
        new Data(Collectable.Type.SOUL, 1110, 40),
        new Data(Collectable.Type.BIGSOUL, 1810, 40),
        new Data(Collectable.Type.BIGSOUL, 2960, 40),
    };

}
