package com.pong1.pong1;

/**
 * Created by stree_001 on 7/11/2015.
 */
public class Paddle {

    private int speed;
    private int x;
    private int y;
    private int height;
    private int width;

    public Paddle(int startX, int startY, int width, int height){
        this.x = startX;
        this.y = startY;
        this.speed = 0;
        this.width = width;
        this.height = height;
    }

    public void update() {
        y += speed;
        if(y < 0)
            y = 0;
        else if(y + height > GameScreen.gameScreenHeight)
            y = GameScreen.gameScreenHeight - height;

    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
