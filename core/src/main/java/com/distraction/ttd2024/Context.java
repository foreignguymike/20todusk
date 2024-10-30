package com.distraction.ttd2024;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.distraction.ttd2024.screen.ScreenManager;

import java.util.ArrayList;
import java.util.List;

public class Context {

    private static final String TILED_FILE = "tiled.tmx";
    private static final String ATLAS_FILE = "20todusk.atlas";

    public static final String FONT_NAME_IMPACT16 = "fonts/impact16.fnt";
    public static final String FONT_NAME_VCR20 = "fonts/vcr20.fnt";

    public AssetManager assets;

    public SpriteBatch sb;
    public ScreenManager sm;

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS_FILE, TextureAtlas.class);
        assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assets.load(FONT_NAME_IMPACT16, BitmapFont.class);
        assets.load(FONT_NAME_VCR20, BitmapFont.class);
//        assets.load(TILED_FILE, TiledMap.class);
        assets.finishLoading();

        sb = new SpriteBatch();
        sm = new ScreenManager();
    }

    public TiledMap getTiles() {
        return assets.get(TILED_FILE);
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

    public void dispose() {
        sb.dispose();
    }

}
