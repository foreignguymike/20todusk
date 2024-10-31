package com.distraction.ttd2024.screen;

import com.distraction.ttd2024.Context;

public class TitleScreen extends Screen {

    public TitleScreen(Context context) {
        super(context);
    }

    @Override
    public void update(float dt) {
        if (!ignoreInput) {
            ignoreInput = true;
            context.data.reset();
            context.sm.push(new CheckeredTransitionScreen(context, new PlayScreen(context)));
        }
    }

    @Override
    public void render() {

    }
}
