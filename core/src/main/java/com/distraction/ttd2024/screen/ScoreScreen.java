package com.distraction.ttd2024.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.entity.Entity;
import com.distraction.ttd2024.entity.FontEntity;

import de.golfgl.gdxgamesvcs.leaderboard.ILeaderBoardEntry;

public class ScoreScreen extends Screen {

    private static final Color GOLD = Color.valueOf("FFF540");
    private static final Color SILVER = Color.valueOf("C7D4E1");
    private static final Color BRONZE = Color.valueOf("CF968C");
    private static final Color LAME = Color.valueOf("928FB8");


    private static final int SCORES_PER_PAGE = 8;
    private static final int PAGES = Context.MAX_SCORES / SCORES_PER_PAGE;
    private int page;
    private int newPage;

    private final TextureRegion bg;
    private final TextureRegion pixel;

    private final FontEntity[][] scoreFonts;

    private final int[][] replayData;
    private final Entity[] replayButtons;

    private final FontEntity fontEntity;
    private final Entity refreshButton;

    private final Entity scoreLeft;
    private final Entity scoreRight;

    private final Entity closeButton;
    private boolean closing;

    private float alpha;
    private OrthographicCamera staticCam;

    protected ScoreScreen(Context context) {
        super(context);

        bg = context.getImage("scoresbg");
        pixel = context.getPixel();

        context.sm.depth = 2;

        BitmapFont m5Font = context.getFont(Context.FONT_NAME_M5X716);
        scoreFonts = new FontEntity[Context.MAX_SCORES][3];
        for (int row = 0; row < scoreFonts.length; row++) {
            int page = row / SCORES_PER_PAGE;
            scoreFonts[row][0] = new FontEntity(context, m5Font);
            scoreFonts[row][0].center = true;
            scoreFonts[row][0].x = Constants.WIDTH * page + 50;
            scoreFonts[row][0].y = Constants.HEIGHT - 59 - (row % SCORES_PER_PAGE) * 16;
            scoreFonts[row][0].setText((row + 1) + "");
        }
        for (int row = 0; row < scoreFonts.length; row++) {
            scoreFonts[row][1] = new FontEntity(context, m5Font);
            scoreFonts[row][2] = new FontEntity(context, m5Font);

            scoreFonts[row][1].center = false;
            scoreFonts[row][2].center = false;

            scoreFonts[row][1].x = scoreFonts[row][0].x + 10;
            scoreFonts[row][1].y = scoreFonts[row][0].y;
            scoreFonts[row][2].x = scoreFonts[row][0].x + 117;
            scoreFonts[row][2].y = scoreFonts[row][0].y;

            scoreFonts[row][1].setText("");
            scoreFonts[row][2].setText("");

            if (row == 0) {
                scoreFonts[row][0].setColor(GOLD);
                scoreFonts[row][1].setColor(GOLD);
                scoreFonts[row][2].setColor(GOLD);
            } else if (row == 1) {
                scoreFonts[row][0].setColor(SILVER);
                scoreFonts[row][1].setColor(SILVER);
                scoreFonts[row][2].setColor(SILVER);
            } else if (row == 2) {
                scoreFonts[row][0].setColor(BRONZE);
                scoreFonts[row][1].setColor(BRONZE);
                scoreFonts[row][2].setColor(BRONZE);
            } else {
                scoreFonts[row][0].setColor(LAME);
                scoreFonts[row][1].setColor(LAME);
                scoreFonts[row][2].setColor(LAME);
            }
        }

        replayData = new int[Context.MAX_SCORES][];
        replayButtons = new Entity[Context.MAX_SCORES];
        for (int i = 0; i < replayButtons.length; i++) {
            replayButtons[i] = new Entity(context, context.getImage("replay"), scoreFonts[i][0].x + 196, scoreFonts[i][0].y + 1);
        }

        fontEntity = new FontEntity(context, m5Font);
        fontEntity.x = Constants.WIDTH / 2f;
        fontEntity.y = Constants.HEIGHT / 2f + 10;
        refreshButton = new Entity(context, context.getImage("restart"), Constants.WIDTH / 2f, Constants.HEIGHT / 2f - 10);

        scoreLeft = new Entity(context, context.getImage("scoreleft"), 20, Constants.HEIGHT / 2f - 30);
        scoreRight = new Entity(context, context.getImage("scoreright"), Constants.WIDTH - 20f, scoreLeft.y);

        closeButton = new Entity(context, context.getImage("close"), Constants.WIDTH - 14, Constants.HEIGHT - 15);

        uiCam.position.y = Constants.HEIGHT * 1.5f;
        uiCam.update();

        staticCam = new OrthographicCamera();
        staticCam.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);

        updateLeaderboards();
    }

    private void updateLeaderboards() {
        for (int i = 0; i < Context.MAX_SCORES; i++) {
            if (i < context.entries.size()) {
                ILeaderBoardEntry entry = context.entries.get(i);
                scoreFonts[i][1].setText(entry.getUserDisplayName());
                scoreFonts[i][2].setText(entry.getFormattedValue());

                String tag = entry.getScoreTag();
                if (!tag.isEmpty()) {
                    String[] split = entry.getScoreTag().split(",");
                    replayData[i] = new int[split.length];
                    for (int j = 0; j < split.length; j++) {
                        replayData[i][j] = Integer.parseInt(split[j]);
                    }
                } else {
                    replayData[i] = new int[0];
                }
            } else {
                scoreFonts[i][1].setText("");
                scoreFonts[i][2].setText("");
            }
        }
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

        if (!ignoreInput && alpha >= 0.5f) {
            if (Gdx.input.isTouched() && page == newPage) {
                unproject(cam);
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
                unproject();
                if (closeButton.contains(m.x, m.y)) {
                    ignoreInput = true;
                    Gdx.input.setInputProcessor(null);
                    closing = true;
                }
                if (refreshButton.contains(m.x, m.y) && !context.leaderboardsInitialized && !context.leaderboardsRequesting) {
                    context.fetchLeaderboard((success) -> {});
                }
                if (scoreLeft.contains(m.x, m.y) && page > 0) newPage--;
                if (scoreRight.contains(m.x, m.y) && page < PAGES - 1) newPage++;
            }
        }

        if (page != newPage) {
            float x = Constants.WIDTH * newPage + Constants.WIDTH / 2f;
            if (cam.position.x < x) {
                cam.position.x += 1000 * dt;
                if (cam.position.x > x) {
                    cam.position.x = x;
                    page = newPage;
                }
            }
            if (cam.position.x > x) {
                cam.position.x -= 1000 * dt;
                if (cam.position.x < x) {
                    cam.position.x = x;
                    page = newPage;
                }
            }
            cam.update();
        }
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(staticCam.combined);
        sb.setColor(0, 0, 0, MathUtils.clamp(alpha * 2f, 0f, 0.9f));
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Color.WHITE);
        sb.draw(bg, 0, 0);

        if (uiCam.position.y <= Constants.HEIGHT / 2f) {
            sb.setProjectionMatrix(cam.combined);
        }
        sb.setColor(Color.WHITE);
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

        sb.setProjectionMatrix(uiCam.combined);
        closeButton.render(sb);

        scoreLeft.render(sb);
        scoreRight.render(sb);

        if (!context.leaderboardsInitialized) {
            if (context.leaderboardsRequesting) {
                fontEntity.setText("Fetching scores...");
            } else {
                fontEntity.setText("Error fetching leaderboards.");
                refreshButton.render(sb);
            }

            fontEntity.render(sb);
        }

        sb.end();
    }

}
