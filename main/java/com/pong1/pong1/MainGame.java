package com.pong1.pong1;

import android.content.Context;

import com.pong1.framework.Screen;
import com.pong1.framework.implementation.AndroidGame;

/**
 * Created by stree_001 on 7/18/2015.
 */
public class MainGame extends AndroidGame {
    // Option defaults
    public static int optionsBallSpeed = 5;
    public static boolean optionsIsSinglePlayer = false;
    public static boolean optionsSoundOn = true;
    public static int optionsPlayTo = 3;

    public static Context appContext;

    @Override
    public Screen getInitScreen() {
        Assets.load(this);
        appContext = getApplicationContext();
        return new SplashLoadingScreen(this, appContext);
    }

    @Override
    public void onBackPressed() {
        getCurrentScreen().backButton();
    }

}
