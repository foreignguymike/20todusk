package com.distraction.ttd2024.screen;

import java.util.Stack;

public class ScreenManager {

    public int depth = 1;

    private final Stack<Screen> screens;

    public ScreenManager() {
        screens = new Stack<>();
    }

    public void push(Screen s) {
        if (!screens.isEmpty()) peek().pause();
        screens.push(s);
        peek().resume();
    }

    public void pop() {
        screens.pop();
        if (!screens.isEmpty()) peek().resume();
    }

    public void replace(Screen s) {
        screens.pop();
        screens.push(s);
        peek().resume();
    }

    public Screen peek() {
        return screens.peek();
    }

    public void update(float dt) {
        for (int i = screens.size() - depth; i < screens.size(); i++) {
            screens.get(i).update(dt);
        }
    }

    public void render() {
        for (int i = screens.size() - depth; i < screens.size(); i++) {
            screens.get(i).render();
        }
    }

}
