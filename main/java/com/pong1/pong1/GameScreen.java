package com.pong1.pong1;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.pong1.framework.Game;
import com.pong1.framework.Graphics;
import com.pong1.framework.Image;
import com.pong1.framework.Input;
import com.pong1.framework.Screen;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by stree_001 on 7/19/2015.
 */
public class GameScreen extends Screen {
    enum GameState {
        Ready, Running, Paused, GameOver
    }

    GameState state = GameState.Ready;

    public static Paddle paddle1, paddle2;
    public static Ball ball;

    public static int gameScreenHeight = 480;
    public static int gameScreenWidth = 800;

    public static final int paddleSpeed = 8;
    public static final int paddleWidth = 20;
    public static final int paddleHeight = 80;
    public static final int ballRadius = 10;
    //public static final int ballSpeed = 5;

    //public static int ballSpeed = MainGame.optionsBallSpeed + 2;
    //public static int pointsToWin = MainGame.optionsPlayTo;
    public static int ballSpeed;
    public static int pointsToWin;
    public static boolean soundOn;
    public static boolean isSinglePlayer;
    public static boolean useAI;

    public static MainGame.Mode mode;


    public int p1Score;
    public int p2Score;

    //public static final int pointsToWin = 3;

    //private Image paddleImage, ballImage;

    public GameScreen(Game game) {
        super(game);
        Log.e("GameScreen", "In GameScreen constructor");

        // set options data
        if(mode == MainGame.Mode.AI_MODE) {
            ballSpeed = MainGame.aiDifficulty.ordinal() + 8;
        }
        else {
            ballSpeed = MainGame.optionsBallSpeed + 3;
        }
        pointsToWin = MainGame.optionsPlayTo;
        isSinglePlayer = MainGame.optionsIsSinglePlayer;
        soundOn = MainGame.optionsSoundOn;
        useAI = MainGame.optionsUseAI;

        mode = MainGame.gameMode;

        int midHeight = gameScreenHeight / 2 - paddleHeight / 2;
        // initialize game objects
        paddle1 = new Paddle(0,midHeight, paddleWidth, paddleHeight);

        if(mode == MainGame.Mode.SINGLE_MODE)
            paddle2 = new Paddle(gameScreenWidth - paddleWidth, 0, paddleWidth, gameScreenHeight);
        else
            paddle2 = new Paddle(gameScreenWidth - paddleWidth, midHeight, paddleWidth, paddleHeight);

        Random rand = new Random();
        int randInt = rand.nextInt((2 - 0) + 1) + 0;
        int randInt2 = rand.nextInt((2 - 0) + 1) + 0;

        System.out.println("random number: " + Integer.toString(randInt));
        System.out.println("random number 2: " + Integer.toString(randInt2));
        System.out.println(randInt == 1 ? ballSpeed : -ballSpeed);
        System.out.println(randInt2 == 1 ? ballSpeed : -ballSpeed);
        ball = new Ball(gameScreenWidth / 2, gameScreenHeight / 2, ballRadius, randInt == 1 ? ballSpeed : -ballSpeed, randInt2 == 1 ? ballSpeed : -ballSpeed);

        // reset scores
        p1Score = 0;
        p2Score = 0;

        // debug options data
        System.out.println("In GameScreen constructor");
        System.out.println("play to: " + Integer.toString(pointsToWin));
        System.out.println("ball speed: " + Integer.toString(ballSpeed));
        System.out.print("single player: ");
        System.out.println(isSinglePlayer);
        System.out.print("volume on: ");
        System.out.println(soundOn);
        System.out.print("ai on: ");
        System.out.println(useAI);
        System.out.print("mode: ");
        System.out.println(mode);
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        // We have four separate update methods in this example.

        if (state == GameState.Ready)
            updateReady(touchEvents);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<Input.TouchEvent> touchEvents) {

        // This example starts with a "Ready" screen.
        // When the user touches the screen, the game begins.
        // state now becomes GameState.Running.
        // Now the updateRunning() method will be called!

        if (touchEvents.size() > 0) {
            Log.e("updateReady", "Entering running state");
            state = GameState.Running;
            Assets.theme.play();
        }
    }

    private void updateRunning(List<Input.TouchEvent> touchEvents, float deltaTime) {
        // Check for touch events
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_DOWN) {

                if (inBounds(event, 0, 0, gameScreenWidth / 2, gameScreenHeight / 2)) {
                    paddle1.setSpeed(-paddleSpeed);
                }
                else if (inBounds(event, 0, gameScreenHeight / 2, gameScreenWidth / 2, gameScreenHeight)) {
                    paddle1.setSpeed(paddleSpeed);
                }
                else if ((mode == MainGame.Mode.TWO_PLAYER_MODE) && inBounds(event, gameScreenWidth / 2, 0, gameScreenWidth, gameScreenHeight / 2)) {
                    paddle2.setSpeed(-paddleSpeed);
                }
                else if ((mode == MainGame.Mode.TWO_PLAYER_MODE) && inBounds(event, gameScreenWidth / 2, gameScreenHeight / 2, gameScreenWidth, gameScreenHeight)) {
                    paddle2.setSpeed(paddleSpeed);
                }
            }

            if (event.type == Input.TouchEvent.TOUCH_UP) {

                if (inBounds(event, 0, 0, gameScreenWidth, gameScreenHeight)) {
                    paddle1.setSpeed(0);
                    paddle2.setSpeed(0);

                }
            }

        }

        // Do AI moves
        if(mode == MainGame.Mode.AI_MODE) {
            updatePaddleAI();
        }


        // check if ball is at right edge
        if(ball.getX() + ball.getRadius() >= paddle2.getX()) {
            // handle condition if ball hits paddle2
            if((ball.getY() - ball.getRadius() <= paddle2.getY() + paddle2.getHeight())
            && (ball.getY() + ball.getRadius() >= paddle2.getY())){
                if(soundOn)
                    Assets.collision.play(0.75f);
                ball.ballHitsPaddleHandler();
            }
            // handle case if ball hits edge but not paddle (score)
            else{
                //ball.setSpeedX(-ball.getSpeedX());
                System.out.println("Score player 1");
                resetBall(-ballSpeed);
                ++p1Score;
                if(p1Score >= pointsToWin) {
                    state = GameState.GameOver;
                    if(mode == MainGame.Mode.AI_MODE) {
                        MainGame.aiHighScores.set(MainGame.aiDifficulty.ordinal(), MainGame.aiHighScores.get(MainGame.aiDifficulty.ordinal()) + 1);
                        serializeHighScores(MainGame.ai_highScoresFileName, MainGame.aiHighScores);
                    }
                }
                else
                    state = GameState.Ready;
            }
        }
        else if(ball.getX() - ball.getRadius() <= paddle1.getX() + paddle1.getWidth()) {
            // handle condition if ball hits paddle1
            if(        (ball.getY() - ball.getRadius() <= paddle1.getY() + paddle1.getHeight())
                    && (ball.getY() + ball.getRadius() >= paddle1.getY())){
                if(soundOn)
                    Assets.collision.play(0.75f);
                ball.ballHitsPaddleHandler();
                if(mode == MainGame.Mode.SINGLE_MODE) {
                    ++p1Score;
                }
            }
            // handle case if ball hits edge but not paddle (score)
            else{
                //ball.setSpeedX(-ball.getSpeedX());
                if(mode == MainGame.Mode.SINGLE_MODE) {
                    // In Single Player mode and player just lost
                    state = GameState.GameOver;
                    if(p1Score > MainGame.highScores.get(MainGame.optionsBallSpeed)) {
                        MainGame.highScores.set(MainGame.optionsBallSpeed, p1Score);
                        serializeHighScores(MainGame.highScoresFileName, MainGame.highScores);
                    }
                }
                else {
                    // Not in single player mode, ball hits edge but not paddle
                    System.out.println("Score player 2");
                    resetBall(ballSpeed);
                    ++p2Score;
                    if (p2Score >= pointsToWin) {
                        state = GameState.GameOver;
                    }
                    else
                        state = GameState.Ready;
                }
            }
        }

        paddle1.update();
        paddle2.update();
        ball.update();
    }

    private void resetBall(int speed) {
        ball.setSpeedX(speed);

        Random rand = new Random();
        int randInt = rand.nextInt((2 - 0) + 1) + 0;
        ball.setSpeedY(randInt == 1 ? ballSpeed : -ballSpeed);

        ball.setX(gameScreenWidth / 2);
        ball.setY(gameScreenHeight / 2);
    }
    private boolean checkCollision(Ball b, Paddle p) {
        if(        (b.getX() < p.getX() + p.getWidth())
                && (b.getX() + b.getRadius() > p.getX())
                && (b.getY() < p.getY() + p.getHeight())
                && (b.getY() + b.getRadius() > p.getY())) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    private void updatePaused(List<Input.TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_UP) {
                if (inBounds(event, 0, 0, 800, 240)) {

                    if (!inBounds(event, 0, 0, 35, 35)) {
                        resume();
                    }
                }

                if (inBounds(event, 0, 240, 800, 240)) {
                    nullify();
                    goToMenu();
                }
            }
        }
    }

    private void updateGameOver(List<Input.TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_DOWN) {
                if (inBounds(event, 0, 0, 800, 480)) {
                    nullify();
                    game.setScreen(new MainMenuScreen(game, MainGame.appContext));
                    return;
                }
            }
        }
    }

    private void nullify() {

        // Set all variables to null. You will be recreating them in the
        // constructor.
        paddle1 = null;
        paddle2 = null;
        ball = null;

        //paddleImage = null;
        //ballImage = null;

        System.gc();
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();

        // Clear screen
        g.clearScreen(Color.BLACK);

        // Draw game elements
        g.drawRect(paddle1.getX(), paddle1.getY(), paddle1.getWidth(), paddle1.getHeight(), Color.GREEN);
        g.drawRect(paddle2.getX(), paddle2.getY(), paddle2.getWidth(), paddle2.getHeight(), Color.GREEN);
        g.drawCircle(ball.getX(), ball.getY(), ball.getRadius(), Color.BLUE);

        // Draw edge lines
        g.drawLine(paddle1.getWidth(), 0, paddle1.getWidth(), gameScreenHeight, Color.WHITE);
        g.drawLine(paddle2.getX(), 0, paddle2.getX(), gameScreenHeight, Color.WHITE);

        // Draw quadrant lines
        g.drawLine(gameScreenWidth / 2, 0, gameScreenWidth / 2, gameScreenHeight, Color.DKGRAY);
        g.drawLine(0, gameScreenHeight / 2, gameScreenWidth, gameScreenHeight / 2, Color.DKGRAY);

        // Draw scores
        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        g.drawString(Integer.toString(p1Score), paddle1.getWidth() + 20, gameScreenHeight - 20, paint);
        if(mode == MainGame.Mode.AI_MODE || mode == MainGame.Mode.TWO_PLAYER_MODE)
            g.drawString(Integer.toString(p2Score), gameScreenWidth - paddle2.getWidth() - 20, gameScreenHeight - 20, paint);

        // Secondly, draw the UI above the game elements.
        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        // Defining a paint object
        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        g.drawARGB(155, 0, 0, 0);
        g.drawString("Tap to Start.", 400, 240, paint);

    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        //Log.e("drawRunningUI", "draw running UI");
        /*
        g.drawImage(Assets.button, 0, 285, 0, 0, 65, 65);
        g.drawImage(Assets.button, 0, 350, 0, 65, 65, 65);
        g.drawImage(Assets.button, 0, 415, 0, 130, 65, 65);
        g.drawImage(Assets.button, 0, 0, 0, 195, 35, 35);
        */

    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        // Darken the entire screen so you can display the Paused screen.
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        g.drawARGB(155, 0, 0, 0);
        g.drawString("Resume", 400, 165, paint);
        g.drawString("Menu", 400, 360, paint);

    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();

        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        Paint paint2 = new Paint();
        paint2.setTextSize(100);
        paint2.setTextAlign(Paint.Align.CENTER);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.WHITE);

        //g.drawRect(0, 0, 1281, 801, Color.BLACK);
        if(mode == MainGame.Mode.SINGLE_MODE) {
            g.drawString("Score: " + Integer.toString(p1Score), 400, 240, paint2);
        }
        else {
            String winStr = "Player 1 Wins!";
            if (p2Score > p1Score) {
                if(mode == MainGame.Mode.TWO_PLAYER_MODE)
                    winStr = "Player 2 Wins!";
                else if(mode == MainGame.Mode.AI_MODE)
                    winStr = "Computer Wins";

            }

            StringBuilder scoreSb = new StringBuilder();
            scoreSb.append("Score: Player 1 - ");
            scoreSb.append(p1Score);
            if(mode == MainGame.Mode.TWO_PLAYER_MODE)
                scoreSb.append(", Player 2 - ");
            else if(mode == MainGame.Mode.AI_MODE)
                scoreSb.append(", Computer - ");
            scoreSb.append(p2Score);
            String scoreStr = scoreSb.toString();

            g.drawString(winStr, 400, 240, paint2);
            g.drawString(scoreStr, 400, 290, paint);

        }
        g.drawString("Tap to return.", 400, 330, paint);
    }

    @Override
    public void pause() {
        Log.d("GameScreen pause", "pause");
        if (state == GameState.Running) {
            state = GameState.Paused;
            Assets.theme.pause();
        }
    }

    @Override
    public void resume() {
        if (state == GameState.Paused) {
            state = GameState.Running;
            Assets.theme.play();
        }
    }

    @Override
    public void dispose() {
        Log.d("GameScreen dispose", "dispose");
        Assets.theme.stop();
    }

    @Override
    public void backButton() {
        Log.d("GameScreen backButton", "backButton");
        pause();
    }

    private void goToMenu() {
        // TODO Auto-generated method stub
        Log.d("GameScreen goToMenu", "goToMenu");
        game.setScreen(new MainMenuScreen(game, MainGame.appContext));

    }

    public void serializeHighScores(String fileName, ArrayList<Integer> scoresContainer) {
        try{
            FileOutputStream fos= new FileOutputStream(MainGame.appContext.getFilesDir().getPath() + "/" + fileName);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            Log.d("GameScreen", "serializeHighScores - writing object");
            oos.writeObject(scoresContainer);
            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void updatePaddleAI() {
        // find midPoint of paddle in y
        int paddleMidPointY = paddle2.getY() + paddle2.getHeight() / 2;

        // scale the AI speed by the ball speed and difficulty
        int aiPaddleSpeed = ballSpeed - 1;


        // Check if ball is moving away or towards paddle
        if(ball.getSpeedX() < 0) {
            // Moving away from paddle - center the paddle
            if(paddleMidPointY < (gameScreenHeight / 2)){
                // If paddle is above middle of screen, move it down
                paddle2.setSpeed(aiPaddleSpeed);
            }
            else {
                // If paddle is below middle of screen, move it up
                paddle2.setSpeed(-aiPaddleSpeed);
            }
        }
        else {
            // Moving towards the paddle - try to hit the ball
            if(ball.getY() < paddleMidPointY) {
                // If ball is higher than paddle, then move up
                paddle2.setSpeed(-aiPaddleSpeed);
            }
            else {
                // If ball is lower than paddle, then move down
                paddle2.setSpeed(aiPaddleSpeed);
            }
        }
    }

}
