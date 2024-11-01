package com.distraction.ttd2024.screen;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.B;
import static com.badlogic.gdx.Input.Keys.BACKSPACE;
import static com.badlogic.gdx.Input.Keys.C;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.E;
import static com.badlogic.gdx.Input.Keys.ENTER;
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
import static com.badlogic.gdx.Input.Keys.SPACE;
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
import com.distraction.ttd2024.entity.Entity;
import com.distraction.ttd2024.entity.FontEntity;

import java.util.HashMap;
import java.util.List;
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
        put(SPACE, " ");
    }};

    private static final Color BG_COLOR = Color.valueOf("392946");
    private static final int MAX_CHARS = 12;

    private final TextureRegion bg;
    private final TextureRegion pixel;

    private final FontEntity scoreFont;
    private final FontEntity[][] scoreFonts;
    private final FontEntity enterNameFont;
    private final FontEntity nameFont;

    private final int[][] replayData;
    private final Entity[] replayButtons;

    private final Entity closeButton;
    private boolean closing;

    private boolean shift = false;
    private boolean loading = false;
    private final Entity submitButton;

    private float alpha;

    protected ScoreScreen(Context context) {
        super(context);

        bg = context.getImage("scoresbg");
        pixel = context.getPixel();

        context.sm.depth = 2;

        BitmapFont vcrFont = context.getFont(Context.FONT_NAME_VCR20);
        scoreFont = new FontEntity(context, vcrFont);
        scoreFont.setText("Score:" + context.data.score);
        scoreFont.center = false;
        scoreFont.x = 165f;
        scoreFont.y = Constants.HEIGHT - 15f;

        BitmapFont m5Font = context.getFont(Context.FONT_NAME_M5X716);
        scoreFonts = new FontEntity[Context.MAX_SCORES][3];
        for (int row = 0; row < scoreFonts.length; row++) {
            scoreFonts[row][0] = new FontEntity(context, m5Font);
            scoreFonts[row][0].center = true;
            scoreFonts[row][0].x = 50;
            scoreFonts[row][0].y = Constants.HEIGHT - 59 - row * 16;
            scoreFonts[row][0].setText((row + 1) + "");
        }
        for (int row = 0; row < scoreFonts.length; row++) {
            scoreFonts[row][1] = new FontEntity(context, m5Font);
            scoreFonts[row][2] = new FontEntity(context, m5Font);

            scoreFonts[row][1].center = false;
            scoreFonts[row][2].center = false;

            scoreFonts[row][1].x = 60;
            scoreFonts[row][1].y = scoreFonts[row][0].y;
            scoreFonts[row][2].x = 167;
            scoreFonts[row][2].y = scoreFonts[row][0].y;

            scoreFonts[row][1].setText("");
            scoreFonts[row][2].setText("");
        }

        replayData = new int[Context.MAX_SCORES][];
        replayButtons = new Entity[Context.MAX_SCORES];
        for (int i = 0; i < replayButtons.length; i++) {
            replayButtons[i] = new Entity(context, context.getImage("replay"), 246, scoreFonts[i][0].y + 1);
        }

        closeButton = new Entity(context, context.getImage("close"), Constants.WIDTH - 14, Constants.HEIGHT - 15);

        uiCam.position.y = Constants.HEIGHT * 1.5f;
        uiCam.update();

        enterNameFont = new FontEntity(context, context.getFont(Context.FONT_NAME_VCR20));
        enterNameFont.setText("Name:");
        enterNameFont.center = false;
        enterNameFont.x = 20;
        enterNameFont.y = 13;
        nameFont = new FontEntity(context, context.getFont(Context.FONT_NAME_VCR20));
        if (context.data.name != null) nameFont.setText(context.data.name);
        nameFont.center = false;
        nameFont.x = 100;
        nameFont.y = 13;
        submitButton = new Entity(context, context.getImage("submit"), 240, 20);

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
                if (!context.data.submitted) {
                    String letter = INPUT_MAP.get(keycode);
                    if (letter != null) {
                        if (context.data.name.length() < MAX_CHARS) {
                            if (shift) context.data.name += letter.toUpperCase();
                            else context.data.name += letter;
                        }
                    }
                    if (keycode == BACKSPACE) {
                        if (!context.data.name.isEmpty()) {
                            context.data.name = context.data.name.substring(0, context.data.name.length() - 1);
                        }
                    }

                    if (keycode == ENTER) {
                        submit();
                    }
                }
                if (context.data.name != null) {
                    nameFont.setText(context.data.name);
                }
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

                String[] split = entry.getScoreTag().split(",");
                replayData[i] = new int[split.length];
                for (int j = 0; j < split.length; j++) {
                    replayData[i][j] = Integer.parseInt(split[j]);
                }
            } else {
                scoreFonts[i][1].setText("");
                scoreFonts[i][2].setText("");
            }
        }
    }

    private void submit() {
        if (!valid()) return;
        if (context.data.submitted) return;
        loading = true;
        context.submitScore(context.data.name, context.data.score, serializeSave(context.data.save), new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String res = httpResponse.getResultAsString();
                // throwing an exception with SubmitScoreResponse here for some reason
                // just doing a sus true check instead
                if (res.contains("true")) {
                    context.data.submitted = true;
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

    private String serializeSave(List<Integer> save) {
        StringBuilder ret = new StringBuilder();
        if (!save.isEmpty()) {
            ret = new StringBuilder(save.get(0).toString());
        }
        for (int i = 1; i < save.size(); i++) {
            ret.append(",").append(save.get(i).toString());
        }
        return ret.toString();
    }

    private boolean valid() {
        return !context.data.name.isEmpty() && context.leaderboardsInitialized;
    }

    @Override
    public void resume() {
        ignoreInput = false;
        context.sm.depth = 2;
    }

    @Override
    public void update(float dt) {
        if (!closing) {
            alpha += dt;
            if (alpha > 0.5f) alpha = 0.5f;
            uiCam.position.x = Constants.WIDTH / 2f + MathUtils.random(-5, 5);
            uiCam.position.y -= 300 * dt;
            if (uiCam.position.y < Constants.HEIGHT / 2f) {
                uiCam.position.x = Constants.WIDTH / 2f;
                uiCam.position.y = Constants.HEIGHT / 2f;
            }
            uiCam.update();
        } else {
            alpha -= dt;
            if (alpha < 0) alpha = 0;
            uiCam.position.y += 600 * dt;
            if (uiCam.position.y > Constants.HEIGHT * 2f) {
                context.sm.pop();
                context.sm.depth = 1;
                return;
            }
            uiCam.update();
        }

        if (!ignoreInput) {
            if (Gdx.input.isTouched()) {
                unproject();
                for (int i = 0; i < replayButtons.length; i++) {
                    if (replayButtons[i].contains(m.x, m.y)) {
                        if (replayData[i] != null) {
                            ignoreInput = true;
                            context.sm.push(new PlayScreen(context, replayData[i]));
                            context.sm.depth = 1;
                            break;
                        }
                    }
                }
                if (closeButton.contains(m.x, m.y)) {
                    ignoreInput = true;
                    Gdx.input.setInputProcessor(null);
                    closing = true;
                }
            }
        }
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(cam.combined);
        sb.setColor(0, 0, 0, MathUtils.clamp(alpha * 2f, 0f, 0.9f));
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Color.WHITE);
        sb.draw(bg, 0, 0);

        sb.setColor(Color.WHITE);
        if (context.data.score > 0) scoreFont.render(sb);
        for (int i = 0; i < scoreFonts.length; i++) {
            for (int j = 0; j < scoreFonts[0].length; j++) {
                scoreFonts[i][j].render(sb);
            }
        }
        for (int i = 0; i < replayData.length; i++) {
            if (replayData[i] != null) {
                replayButtons[i].render(sb);
            }
        }
        closeButton.render(sb);

        if (context.leaderboardsInitialized) {
            if (context.data.score > 0) {
                if (context.entries.size() < Context.MAX_SCORES
                    || Integer.parseInt(context.entries.get(context.entries.size() - 1).getFormattedValue()) < context.data.score) {
                    enterNameFont.render(sb);
                    nameFont.render(sb);
                    submitButton.render(sb);
                }
            }
        }

        if (loading) {
            for (int i = 0; i < 5; i++) {
                float x = 10 * MathUtils.cos(-6f * alpha + i * 0.1f);
                float y = 10 * MathUtils.sin(-6f * alpha + i * 0.1f);
                sb.draw(pixel, 240 + x, 13 + y, 2, 2);
            }
        }

        sb.end();
    }

}
