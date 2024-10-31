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

//        context.sm.push(new PlayScreen(context, new int[] {85, 1, 96, 0, 115, 3, 131, 2, 157, 3, 164, 2, 188, 1, 198, 0, 212, 3, 228, 2, 231, 1, 265, 0, 286, 3, 321, 2, 322, 7, 324, 1, 342, 6, 359, 5, 361, 0, 377, 3, 379, 4, 404, 7, 405, 2, 424, 1, 425, 6, 450, 0, 451, 3, 486, 2, 487, 1, 519, 0, 521, 3, 538, 7, 549, 2, 557, 1, 558, 6, 572, 5, 576, 0, 586, 3, 590, 4, 596, 5, 607, 4, 610, 2, 621, 7, 623, 1, 625, 6, 637, 0, 658, 1, 675, 0, 677, 7, 697, 6, 701, 1, 706, 5, 711, 0, 720, 4, 727, 3, 736, 2, 747, 1, 762, 0, 771, 3, 787, 2, 796, 3, 807, 2, 814, 7, 823, 6, 835, 3, 848, 2, 867, 1, 894, 0, 909, 3, 939, 2, 940, 1, 949, 0, 986, 3, 993, 2, 1006, 1, 1035, 0, 1036, 3, 1070, 2, 1082, 1, 1090, 0, 1112, 3, 1120, 2, 1123, 1, 1140, 0}));
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
