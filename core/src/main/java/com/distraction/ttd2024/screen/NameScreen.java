package com.distraction.ttd2024.screen;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.B;
import static com.badlogic.gdx.Input.Keys.BACKSPACE;
import static com.badlogic.gdx.Input.Keys.C;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.E;
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
import com.badlogic.gdx.graphics.Color;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.entity.Entity;
import com.distraction.ttd2024.entity.FontEntity;

import java.util.HashMap;
import java.util.Map;

public class NameScreen extends Screen {

    private static final int MAX_CHARS = 12;

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

    private boolean shift = false;

    private final FontEntity enterNameFont;
    private final FontEntity nameFont;
    private final Entity submitButton;

    private float caretTime;

    public NameScreen(Context context) {
        super(context);

        enterNameFont = new FontEntity(context, context.getFont(Context.FONT_NAME_VCR20));
        enterNameFont.setText("Enter Name");
        enterNameFont.center = true;
        enterNameFont.x = Constants.WIDTH / 2f;
        enterNameFont.y = Constants.HEIGHT / 2f + 20;
        nameFont = new FontEntity(context, context.getFont(Context.FONT_NAME_M5X716));
        nameFont.center = true;
        nameFont.x = Constants.WIDTH / 2f;
        nameFont.y = Constants.HEIGHT / 2f - 10;
        submitButton = new Entity(context, context.getImage("submit"), Constants.WIDTH / 2f, 50);

        out = new Transition(context, Transition.Type.FLASH_OUT, 0.5f, () -> context.sm.replace(new TitleScreen(context)));

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (!ignoreInput) {
                    unproject(screenX, screenY);
                    if (submitButton.contains(m.x, m.y, 4, 4)) {
                        if (validName()) {
                            ignoreInput = true;
                            out.start();
                            Gdx.input.setInputProcessor(null);
                        }
                    }
                }
                return true;
            }

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
                            caretTime = 0;
                        }
                    }
                    if (keycode == BACKSPACE) {
                        if (!context.data.name.isEmpty()) {
                            context.data.name = context.data.name.substring(0, context.data.name.length() - 1);
                            caretTime = 0;
                        }
                    }
                }
                if (context.data.name != null) {
                    nameFont.setText(context.data.name);
                }
                return true;
            }
        });
    }

    private boolean validName() {
        return !context.data.name.isEmpty();
    }

    @Override
    public void update(float dt) {
        caretTime += dt;
        out.update(dt);
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Color.WHITE);
        enterNameFont.render(sb);
        nameFont.render(sb);

        if (caretTime % 1 < 0.5f && context.data.name.length() < MAX_CHARS) {
            sb.draw(pixel, context.data.name.isEmpty() ? Constants.WIDTH / 2f - 3 : nameFont.x + nameFont.w / 2f + 1, 77, 6, 1);
        }

        submitButton.a = validName() ? 1f : 0.3f;
        submitButton.render(sb);

        out.render(sb);

        sb.end();
    }
}