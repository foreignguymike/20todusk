package com.distraction.ttd2024.screen;

import com.badlogic.gdx.Gdx;
import com.distraction.ttd2024.Constants;
import com.distraction.ttd2024.Context;
import com.distraction.ttd2024.entity.FontEntity;

public class TitleScreen extends Screen {

    private FontEntity titleFont;

    public TitleScreen(Context context) {
        super(context);

        titleFont = new FontEntity(context, context.getFont(Context.FONT_NAME_VCR20));
        titleFont.setText("TITLE SCREEN :)");
        titleFont.x = Constants.WIDTH / 2f;
        titleFont.y = Constants.HEIGHT / 2f;
    }

    @Override
    public void update(float dt) {
        if (!ignoreInput) {
            if (Gdx.input.justTouched()) {
                ignoreInput = true;
                context.data.reset();
                context.sm.push(new CheckeredTransitionScreen(context, new PlayScreen(context)));
            }
        }
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(uiCam.combined);
        titleFont.render(sb);
        sb.end();
    }
}
