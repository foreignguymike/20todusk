package com.distraction.ttd2024.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.entity.Player;

public class PlayScreen extends Screen {

    private Player player;

    public PlayScreen(Context context) {
        super(context);

        player = new Player(context);
        player.x = Constants.WIDTH / 2f;
        player.y = Constants.HEIGHT / 2f;
    }

    @Override
    public void update(float dt) {
        player.up = Gdx.input.isKeyPressed(Input.Keys.UP);
        player.down = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        player.left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        player.right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);

        player.update(dt);
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        player.render(sb);
        sb.end();
    }
}
