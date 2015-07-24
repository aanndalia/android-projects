package com.pong1.pong1;

import android.content.Context;

import com.pong1.framework.Game;
import com.pong1.framework.Graphics;
import com.pong1.framework.Screen;

/**
 * Created by stree_001 on 7/18/2015.
 */
public class SplashLoadingScreen extends Screen {
    public Context appContext;

    public SplashLoadingScreen(Game game, Context appContext) {
        super(game);
        this.appContext = appContext;
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.splash = g.newImage("mysplash2.png", Graphics.ImageFormat.RGB565);

        game.setScreen(new LoadingScreen(game, appContext));
    }

    @Override
    public void paint(float deltaTime) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

    }
}
