package com.distraction.ttd2024.screen;

import java.util.Stack;

public class ScreenManager {

    public int depth = 1;

    private final Stack<Screen> screens;

    public ScreenManager() {
        screens = new Stack<>();
    }

    public void push(Screen s) {
        screens.push(s);
    }

    public void pop() {
        screens.pop();
    }

    public void replace(Screen s) {
        pop();
        push(s);
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
