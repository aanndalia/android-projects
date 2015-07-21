package com.pong1.pong1;

import com.pong1.framework.Screen;
import com.pong1.framework.implementation.AndroidGame;

/**
 * Created by stree_001 on 7/18/2015.
 */
public class MainGame extends AndroidGame {
    @Override
    public Screen getInitScreen() {
        return new SplashLoadingScreen(this);
    }
}
