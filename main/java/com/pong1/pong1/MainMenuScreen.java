package com.pong1.pong1;

import android.content.Context;
import android.content.Intent;

import com.pong1.framework.Game;
import com.pong1.framework.Graphics;
import com.pong1.framework.Input;
import com.pong1.framework.Screen;
import com.pong1.framework.implementation.AndroidGame;

import java.util.List;

/**
 * Created by stree_001 on 7/19/2015.
 */
public class MainMenuScreen extends Screen {
    //private final Context mContext;
    private final Context appContext;
    public MainMenuScreen(Game game, Context appContext) {
        super(game);
        //this.mContext = context;
        this.appContext = appContext;
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_UP) {

                if (inBounds(event, 25, 350, 155, 86)) {
                    System.out.println("Clicked 2P");
                    MainGame.gameMode = MainGame.Mode.TWO_PLAYER_MODE;
                    //MainGame.optionsIsSinglePlayer = false;
                    game.setScreen(new GameScreen(game));

                }
                else if (inBounds(event, 550, 350, 155, 86)) {
                    System.out.println("Clicked Options.");
                    Intent in = new Intent(appContext, OptionsActivity2.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    appContext.startActivity(in);
                    //Intent in = new Intent (this, OptionsActivity2.class);
                    //startActivity(in);
                }
                else if (inBounds(event, 200, 350, 155, 86)) {

                    MainGame.gameMode = MainGame.Mode.SINGLE_MODE;
                    //MainGame.optionsIsSinglePlayer = true;
                    game.setScreen(new GameScreen(game));
                    //Intent in = new Intent (this, OptionsActivity2.class);
                    //startActivity(in);
                }
                else if (inBounds(event, 375, 350, 155, 86)) {
                    System.out.println("Clicked 1P AI.");
                    MainGame.gameMode = MainGame.Mode.AI_MODE;
                    //MainGame.optionsUseAI = true;
                    //MainGame.optionsIsSinglePlayer = true;
                    game.setScreen(new GameScreen(game));
                    //Intent in = new Intent (this, OptionsActivity2.class);
                    //startActivity(in);
                }
            }
        }
    }

    private boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.menu, 0, 0);
        g.drawImage(Assets.button, 25, 350);
        g.drawImage(Assets.optionsButton, 550, 350);
        g.drawImage(Assets.singlesButton, 200, 350);
        g.drawImage(Assets.singlesAiButton, 375, 350);
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
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
