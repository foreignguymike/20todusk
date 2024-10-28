package com.distraction.ttd2024.screen;

import java.util.Stack;

public class ScreenManager {

    private Stack<Screen> screens;

    public ScreenManager() {
        screens = new Stack<>();
    }

    public void push(Screen s) {
        screens.push(s);
    }

    public Screen pop() {
        return screens.pop();
    }

    public Screen replace(Screen s) {
        Screen r = pop();
        push(s);
        return r;
    }

    public void update(float dt) {
        screens.peek().update(dt);
    }

    public void render() {
        screens.peek().render();
    }

}
