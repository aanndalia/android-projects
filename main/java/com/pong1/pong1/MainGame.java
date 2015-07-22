package com.pong1.pong1;

import com.pong1.framework.Screen;
import com.pong1.framework.implementation.AndroidGame;

/**
 * Created by stree_001 on 7/18/2015.
 */
public class MainGame extends AndroidGame {
    @Override
    public Screen getInitScreen() {
        Assets.load(this);
        return new SplashLoadingScreen(this);
    }

    @Override
    public void onBackPressed() {
        getCurrentScreen().backButton();
    }

}
