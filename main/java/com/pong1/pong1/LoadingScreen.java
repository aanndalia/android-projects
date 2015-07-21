package com.pong1.pong1;

import android.util.Log;

import com.pong1.framework.Game;
import com.pong1.framework.Graphics;
import com.pong1.framework.Screen;

/**
 * Created by stree_001 on 7/18/2015.
 */
public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {

        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();

        Log.d("Pacing", "About to sleep");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("Pacing", "Done Sleeping");

        Assets.menu = g.newImage("myloadscreen2.png", Graphics.ImageFormat.RGB565);
        Assets.paddle = g.newImage("paddle.png", Graphics.ImageFormat.RGB565);
        Assets.ball = g.newImage("ball.png", Graphics.ImageFormat.RGB565);
        Assets.button = g.newImage("start_button.png", Graphics.ImageFormat.RGB565);

        game.setScreen(new MainMenuScreen(game));

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.splash, 0, 0);
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
