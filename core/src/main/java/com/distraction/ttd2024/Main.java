package com.distraction.ttd2024;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.distraction.ttd2024.gj.GameJoltClient;
import com.distraction.ttd2024.screen.PlayScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private Context context;

    private static final float TICK = 1f / 60f;
    private float accum;

    @Override
    public void create() {
        Data.load();

        context = new Context();

        GameJoltClient client = new GameJoltClient();
        client.setGjScoreTableMapper(key -> Constants.LEADERBOARD_ID);
        client.initialize(Constants.APP_ID, Constants.API_KEY);
        context.client = client;

//        context.sm.push(new PlayScreen(context, new int[] {48, 0, 1, 67, 0, 0, 98, 1, 1, 121, 1, 0, 155, 1, 1, 161, 1, 0, 175, 0, 1, 189, 0, 0, 194, 3, 1, 205, 3, 0, 209, 0, 1, 222, 0, 0, 230, 1, 1, 232, 3, 1, 249, 1, 0, 249, 3, 0, 256, 3, 1, 270, 3, 0, 276, 0, 1, 279, 2, 1, 298, 0, 0, 302, 2, 0, 309, 1, 1, 316, 1, 0, 317, 3, 1, 329, 1, 1, 333, 3, 0, 335, 2, 1, 345, 1, 0, 345, 2, 0, 354, 1, 1, 361, 2, 1, 363, 1, 0, 365, 2, 0, 370, 0, 1, 386, 0, 0, 394, 1, 1, 411, 1, 0, 416, 2, 1, 418, 0, 1, 428, 2, 0, 448, 0, 0, 451, 1, 1, 487, 1, 0, 489, 0, 1, 521, 0, 0, 522, 1, 1, 545, 1, 0, 551, 0, 1, 557, 0, 0, 564, 0, 1, 564, 3, 1, 575, 0, 0, 578, 3, 0, 585, 1, 1, 591, 2, 1, 602, 1, 0, 608, 2, 0, 620, 0, 1, 639, 0, 0, 649, 2, 1, 660, 2, 0, 690, 1, 1, 693, 2, 1, 704, 1, 0, 704, 2, 0, 720, 0, 1, 720, 3, 1, 734, 0, 0, 737, 3, 0, 753, 2, 1, 755, 0, 1, 763, 2, 0, 764, 0, 0, 775, 3, 1, 776, 1, 1, 786, 3, 0, 793, 1, 0, 808, 2, 1, 813, 2, 0, 818, 0, 1, 821, 0, 0, 835, 1, 1, 835, 3, 1, 847, 1, 0, 849, 3, 0, 865, 0, 1, 867, 2, 1, 877, 0, 0, 877, 2, 0, 896, 0, 1, 896, 2, 1, 914, 0, 0, 920, 2, 0, 925, 1, 1, 954, 1, 0, 960, 3, 1, 963, 0, 1, 972, 3, 0, 983, 0, 0, 992, 0, 1, 1010, 0, 0, 1020, 1, 1, 1022, 3, 1, 1027, 1, 0, 1036, 1, 1, 1037, 3, 0, 1061, 1, 0, 1068, 0, 1, 1091, 0, 0, 1101, 0, 1, 1112, 0, 0, 1120, 1, 1, 1132, 2, 1, 1147, 1, 0, 1147, 2, 0, 1165, 0, 1, 1173, 0, 0}));
        context.sm.push(new PlayScreen(context));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        accum += Gdx.graphics.getDeltaTime();
        while (accum > TICK) {
            accum -= TICK;
            context.sm.update(TICK);
        }
        context.sm.render();
    }

    @Override
    public void dispose() {
        context.dispose();
    }
}
