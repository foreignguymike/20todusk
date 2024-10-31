package com.distraction.ttd2024.screen;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.B;
import static com.badlogic.gdx.Input.Keys.BACKSPACE;
import static com.badlogic.gdx.Input.Keys.C;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.E;
import static com.badlogic.gdx.Input.Keys.ENTER;
import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static com.badlogic.gdx.Input.Keys.F;
import static com.badlogic.gdx.Input.Keys.G;
import static com.badlogic.gdx.Input.Keys.H;
import static com.badlogic.gdx.Input.Keys.I;
import static com.badlogic.gdx.Input.Keys.J;
import static com.badlogic.gdx.Input.Keys.K;
import static com.badlogic.gdx.Input.Keys.L;
import static com.badlogic.gdx.Input.Keys.M;
import static com.badlogic.gdx.Input.Keys.N;
import static com.badlogic.gdx.Input.Keys.NUM_0;
import static com.badlogic.gdx.Input.Keys.NUM_1;
import static com.badlogic.gdx.Input.Keys.NUM_2;
import static com.badlogic.gdx.Input.Keys.NUM_3;
import static com.badlogic.gdx.Input.Keys.NUM_4;
import static com.badlogic.gdx.Input.Keys.NUM_5;
import static com.badlogic.gdx.Input.Keys.NUM_6;
import static com.badlogic.gdx.Input.Keys.NUM_7;
import static com.badlogic.gdx.Input.Keys.NUM_8;
import static com.badlogic.gdx.Input.Keys.NUM_9;
import static com.badlogic.gdx.Input.Keys.O;
import static com.badlogic.gdx.Input.Keys.P;
import static com.badlogic.gdx.Input.Keys.Q;
import static com.badlogic.gdx.Input.Keys.R;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.SHIFT_LEFT;
import static com.badlogic.gdx.Input.Keys.SHIFT_RIGHT;
import static com.badlogic.gdx.Input.Keys.T;
import static com.badlogic.gdx.Input.Keys.U;
import static com.badlogic.gdx.Input.Keys.V;
import static com.badlogic.gdx.Input.Keys.W;
import static com.badlogic.gdx.Input.Keys.X;
import static com.badlogic.gdx.Input.Keys.Y;
import static com.badlogic.gdx.Input.Keys.Z;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.entity.FontEntity;

import java.util.HashMap;
import java.util.Map;

import de.golfgl.gdxgamesvcs.leaderboard.ILeaderBoardEntry;

public class ScoreScreen extends Screen {

    private static final Map<Integer, String> INPUT_MAP = new HashMap<>() {{
        put(NUM_0, "0");
        put(NUM_1, "1");
        put(NUM_2, "2");
        put(NUM_3, "3");
        put(NUM_4, "4");
        put(NUM_5, "5");
        put(NUM_6, "6");
        put(NUM_7, "7");
        put(NUM_8, "8");
        put(NUM_9, "9");
        put(A, "a");
        put(B, "b");
        put(C, "c");
        put(D, "d");
        put(E, "e");
        put(F, "f");
        put(G, "g");
        put(H, "h");
        put(I, "i");
        put(J, "j");
        put(K, "k");
        put(L, "l");
        put(M, "m");
        put(N, "n");
        put(O, "o");
        put(P, "p");
        put(Q, "q");
        put(R, "r");
        put(S, "s");
        put(T, "t");
        put(U, "u");
        put(V, "v");
        put(W, "w");
        put(X, "x");
        put(Y, "y");
        put(Z, "z");
    }};

    private static final Color BG_COLOR = Color.valueOf("392946");

    private final TextureRegion pixel;

    private final FontEntity leaderboardFont;
    private final FontEntity scoreFont;
    private final FontEntity[][] scoreFonts;
    private final FontEntity enterNameFont;
    private final FontEntity nameFont;

    private boolean submitted = false;
    private boolean shift = false;
    private boolean loading = false;

    private String name = "";
    private final int score;

    private float time;

    protected ScoreScreen(Context context, int score) {
        super(context);
        this.score = score;

        pixel = context.getPixel();

        BitmapFont impactFont = context.getFont(Context.FONT_NAME_VCR20);
        leaderboardFont = new FontEntity(context, impactFont);
        leaderboardFont.setText("LEADERBOARD");
        leaderboardFont.center = false;
        leaderboardFont.x = 15f;
        leaderboardFont.y = Constants.HEIGHT - 15f;

        scoreFont = new FontEntity(context, impactFont);
        scoreFont.setText("Score:" + score);
        scoreFont.center = false;
        scoreFont.x = 165f;
        scoreFont.y = Constants.HEIGHT - 15f;

        scoreFonts = new FontEntity[Context.MAX_SCORES][3];
        for (int row = 0; row < scoreFonts.length; row++) {
            scoreFonts[row][0] = new FontEntity(context, impactFont);
            scoreFonts[row][1] = new FontEntity(context, impactFont);
            scoreFonts[row][2] = new FontEntity(context, impactFont);

            scoreFonts[row][0].center = true;
            scoreFonts[row][1].center = false;
            scoreFonts[row][2].center = false;

            scoreFonts[row][0].x = 40;
            scoreFonts[row][0].y = Constants.HEIGHT - 36 - row * 16;
            scoreFonts[row][1].x = 70;
            scoreFonts[row][1].y = scoreFonts[row][0].y;
            scoreFonts[row][2].x = 240;
            scoreFonts[row][2].y = scoreFonts[row][0].y;

            scoreFonts[row][0].setText((row + 1) + "");
            scoreFonts[row][1].setText("-");
            scoreFonts[row][2].setText("-");
        }
        enterNameFont = new FontEntity(context, context.getFont(Context.FONT_NAME_VCR20));
        enterNameFont.setText("Name:");
        enterNameFont.center = false;
        enterNameFont.x = 20;
        enterNameFont.y = 13;
        nameFont = new FontEntity(context, context.getFont(Context.FONT_NAME_VCR20));
        nameFont.center = false;
        nameFont.x = 100;
        nameFont.y = 13;

        requestLeaderboards();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyUp(int keycode) {
                if (keycode == SHIFT_LEFT || keycode == SHIFT_RIGHT) {
                    shift = false;
                }
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                if (ignoreInput) return true;
                if (keycode == SHIFT_LEFT || keycode == SHIFT_RIGHT) {
                    shift = true;
                }
                String letter = INPUT_MAP.get(keycode);
                if (letter != null) {
                    if (name.length() < 10) {
                        if (shift) name += letter.toUpperCase();
                        else name += letter;
                    }
                }
                if (keycode == BACKSPACE) {
                    if (!name.isEmpty()) {
                        name = name.substring(0, name.length() - 1);
                    }
                }
                if (keycode == ENTER) {
                    submit();
                }
                if (keycode == ESCAPE) {
                    ignoreInput = true;
                    Gdx.input.setInputProcessor(null);
                    context.sm.push(new CheckeredTransitionScreen(context, new PlayScreen(context)));
                }
                nameFont.setText(name);
                return true;
            }
        });
    }

    private void requestLeaderboards() {
        if (!context.leaderboardsRequested) {
            context.leaderboardsRequested = true;
            context.fetchLeaderboard(() -> {
                context.leaderboardsInitialized = true;
                updateLeaderboards();
            });
        }
        updateLeaderboards();
    }

    private void updateLeaderboards() {
        for (int i = 0; i < Context.MAX_SCORES; i++) {
            if (i < context.entries.size()) {
                ILeaderBoardEntry entry = context.entries.get(i);
                scoreFonts[i][1].setText(entry.getUserDisplayName());
                scoreFonts[i][2].setText(entry.getFormattedValue());
            } else {
                scoreFonts[i][1].setText("-");
                scoreFonts[i][2].setText("-");
            }
        }
    }

    private void submit() {
        if (!valid()) return;
        if (submitted) return;
        loading = true;
        context.submitScore(name, score, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String res = httpResponse.getResultAsString();
                // throwing an exception with SubmitScoreResponse here for some reason
                // just doing a sus true check instead
                if (res.contains("true")) {
                    submitted = true;
                    context.fetchLeaderboard(() -> updateLeaderboards());
                } else {
                    failed(null);
                }
                loading = false;
            }

            @Override
            public void failed(Throwable t) {
                ignoreInput = false;
                loading = false;
            }

            @Override
            public void cancelled() {
                failed(null);
            }
        });
    }

    private boolean valid() {
        return !name.isEmpty() && context.leaderboardsInitialized;
    }

    @Override
    public void update(float dt) {
        time += dt;
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(cam.combined);
        sb.setColor(BG_COLOR);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);

        sb.setColor(Color.WHITE);
        leaderboardFont.render(sb);
        scoreFont.render(sb);
        for (FontEntity[] scoreFont : scoreFonts) {
            for (int i = 0; i < scoreFonts[0].length; i++) {
                scoreFont[i].render(sb);
            }
        }

        if (context.leaderboardsInitialized) {
            if (score > 0) {
                if (context.entries.size() < Context.MAX_SCORES || Integer.parseInt(context.entries.getLast().getFormattedValue()) < score) {
                    enterNameFont.render(sb);
                    nameFont.render(sb);
                }
            }
        }

        if (loading) {
            for (int i = 0; i < 5; i++) {
                float x = 10 * MathUtils.cos(-6f * time + i * 0.1f);
                float y = 10 * MathUtils.sin(-6f * time + i * 0.1f);
                sb.draw(pixel, 240 + x, 13 + y, 2, 2);
            }
        }

        sb.end();
    }

}
