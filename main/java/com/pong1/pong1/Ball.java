package com.pong1.pong1;

/**
 * Created by stree_001 on 7/20/2015.
 */
public class Ball {
    private int x;
    private int y;
    private int radius;
    private int speedX;
    private int speedY;

    public Ball(int x, int y, int radius, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void update(){
        y += speedY;
        x += speedX;

        if((y + radius > GameScreen.gameScreenHeight) || (y - radius < 0))
            speedY = -speedY;
    }

    public void ballHitsPaddleHandler() {
        System.out.println("Rebound");
        speedX = -speedX;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
