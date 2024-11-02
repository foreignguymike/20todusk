package com.distraction.ttd2024;

public class Constants {

    public static final String TITLE = "Twilight Run";

    public static final int WIDTH = 320;
    public static final int HEIGHT = 180;
    public static final int SCALE = 3;
    public static final int SWIDTH = WIDTH * SCALE;
    public static final int SHEIGHT = HEIGHT * SCALE;

    public static final boolean FULLSCREEN = false;

    public static final int FPS = 60;

    public static String APP_ID = "";
    public static String API_KEY = "";
    public static int LEADERBOARD_ID = 0;

    // not for you
    static {
        APP_ID = ApiConstants.APP_ID;
        API_KEY = ApiConstants.API_KEY;
        LEADERBOARD_ID = ApiConstants.LEADERBOARD_ID;
    }

}
