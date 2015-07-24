package com.pong1.pong1;

import android.util.Log;

import com.pong1.framework.Game;
import com.pong1.framework.Screen;

/**
 * Created by stree_001 on 7/22/2015.
 */
public class OptionsScreen extends Screen {
    public OptionsScreen(Game game) {
        super(game);
        Log.e("OptionsScreen", "In OptionsScreen constructor");
    }

    @Override
    public void update(float deltaTime) {

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
        game.setScreen(new MainMenuScreen(game, MainGame.appContext));
    }
}
