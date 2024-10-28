package com.distraction.ttd2024.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.entity.Background;
import com.distraction.ttd2024.entity.Collectable;
import com.distraction.ttd2024.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends Screen {

    private final Player player;
    private final Background bg;

    private List<Collectable> collectables;

    public PlayScreen(Context context) {
        super(context);

        player = new Player(context);
        player.x = Constants.WIDTH / 2f;
        player.y = Constants.HEIGHT / 2f;

        bg = new Background(context, player);

        collectables = new ArrayList<>();
        collectables.add(new Collectable(context, Collectable.Type.SOUL, 1000, 200));
    }

    @Override
    public void update(float dt) {
        player.up = Gdx.input.isKeyPressed(Input.Keys.UP);
        player.down = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        player.left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        player.right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
            player.dash(); // TODO make a pick up
        }

        player.update(dt);
        cam.position.x = player.x;
        cam.update();

        bg.update(dt);

        for (int i = 0; i < collectables.size(); i++) {
            Collectable collectable = collectables.get(i);
            if (collectable.overlaps(player.truex(), player.y, player.w, player.h)) {
                collectables.remove(i--);
                if (collectable.type == Collectable.Type.SOUL) {
                    player.dash();
                }
            }
        }
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(uiCam.combined);
        bg.render(sb);
        sb.setProjectionMatrix(cam.combined);
        player.render(sb);

        for (Collectable collectable : collectables) {
            collectable.render(sb);
        }
        sb.end();
    }
}
