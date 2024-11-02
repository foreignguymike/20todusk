package com.distraction.ttd2024;

import java.util.List;

public class PlayerData {
    public String name;
    public int score;
    public List<Integer> save;
    public boolean submitted;
    public boolean firstTitle = true;

    public void set(String name, int score, List<Integer> save) {
        this.name = name;
        this.score = score;
        this.save = save;
    }

    public void reset() {
        name = null;
        score = 0;
        save = null;
        submitted = false;
    }
}
