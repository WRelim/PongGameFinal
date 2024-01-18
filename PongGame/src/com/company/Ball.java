package com.company;

import static com.company.GamePanel.*;

public class Ball {
    private int x, y;
    boolean moveLeft, moveUp;

    Ball() {
        x = ((SCREEN_WIDTH / 2) - UNIT_SIZE);
        y = (SCREEN_HEIGHT / 2) + UNIT_SIZE;
        int randomX = (int)(Math.random() * 2);
        int randomY = (int)(Math.random() * 2);
        if (randomX == 0)
            moveLeft = true;
        else
            moveLeft = false;
        if (randomY == 0)
            moveLeft = true;
        else
            moveLeft = false;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setUp(boolean moveUp) {
        this.moveUp = moveUp;
    }
    public void setLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void move() {
        if (moveLeft) {
            this.x = x - UNIT_SIZE;
        }
        else {
            this.x = x + UNIT_SIZE;
        }
        if (moveUp) {
            this.y = y - UNIT_SIZE;
        }
        else {
            this.y = y + UNIT_SIZE;
        }
    }
}
