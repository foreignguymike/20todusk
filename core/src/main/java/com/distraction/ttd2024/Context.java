package com.distraction.ttd2024;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.distraction.ttd2024.audio.AudioHandler;
import com.distraction.ttd2024.gj.GameJoltClient;
import com.distraction.ttd2024.screen.ScreenManager;

import java.util.ArrayList;
import java.util.List;

import de.golfgl.gdxgamesvcs.leaderboard.ILeaderBoardEntry;

public class Context {

    private static final String ATLAS_FILE = "20todusk.atlas";
    public static final String FONT_NAME_VCR20 = "fonts/vcr20.fnt";
    public static final String FONT_NAME_M5X716 = "fonts/m5x716.fnt";

    public AssetManager assets;
    public AudioHandler audio;

    public SpriteBatch sb;
    public ScreenManager sm;

    public PlayerData data = new PlayerData();

    public static final int MAX_SCORES = 8;
    public GameJoltClient client;
    public boolean leaderboardsRequesting;
    public boolean leaderboardsInitialized;
    public List<ILeaderBoardEntry> entries = new ArrayList<>();

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS_FILE, TextureAtlas.class);
        assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assets.load(FONT_NAME_M5X716, BitmapFont.class);
        assets.load(FONT_NAME_VCR20, BitmapFont.class);
        assets.finishLoading();

        sb = new SpriteBatch();
        sm = new ScreenManager();

        audio = new AudioHandler();
    }

    public TextureRegion getImage(String key) {
        TextureRegion region = assets.get(ATLAS_FILE, TextureAtlas.class).findRegion(key);
        if (region == null) throw new IllegalStateException("image " + key + " not found");
        return region;
    }

    public TextureRegion[] getImages(String key) {
        int count = 1;
        List<TextureRegion> list = new ArrayList<>();
        while (true) {
            TextureRegion region = assets.get(ATLAS_FILE, TextureAtlas.class).findRegion(key + count++);
            if (region == null) break;
            list.add(region);
        }
        return list.toArray(new TextureRegion[0]);
    }

    public TextureRegion getPixel() {
        return assets.get(ATLAS_FILE, TextureAtlas.class).findRegion("pixel");
    }

    public BitmapFont getFont(String name) {
        return assets.get(name, BitmapFont.class);
    }

    public void fetchLeaderboard(SuccessCallback callback) {
        if (Constants.LEADERBOARD_ID == 0) {
            callback.callback(false);
            return;
        }
        entries.clear();
        if (leaderboardsRequesting) return;
        leaderboardsRequesting = true;
        client.fetchLeaderboardEntries("", MAX_SCORES, false, leaderBoard -> {
            if (leaderBoard != null) {
                leaderboardsRequesting = false;
                leaderboardsInitialized = true;
                entries.clear();
                for (int i = 0; i < leaderBoard.size; i++) {
                    entries.add(leaderBoard.get(i));
                }
            }
            callback.callback(leaderBoard != null);
        });
    }

    public void submitScore(String name, int score, String metadata, Net.HttpResponseListener listener) {
        client.setGuestName(name);
        client.submitToLeaderboard("", score, metadata, 10000, listener);
    }

    public boolean isHighscore(int score) {
        if (!leaderboardsInitialized) return false;
        if (score < 10000) return false;
        return entries.size() < MAX_SCORES || score > Integer.parseInt(entries.get(entries.size() - 1).getFormattedValue());
    }

    public void dispose() {
        sb.dispose();
    }

}
