package com.distraction.ttd2024;

import java.util.List;

public class PlayerData {
    public String name = "";
    public int score;
    public List<Integer> save;
    public boolean submitted;

    public void set(int score, List<Integer> save) {
        this.score = score;
        this.save = save;
    }

    public void reset() {
        score = 0;
        save = null;
        submitted = false;
    }
}
