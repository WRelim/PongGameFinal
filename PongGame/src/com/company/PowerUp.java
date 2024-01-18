package com.company;

import static com.company.GamePanel.*;

public class PowerUp {

    private int xPos, yPos;
    private boolean power = false;
    boolean moveLeft;

    PowerUp() {
        xPos = ((SCREEN_WIDTH / 2) - UNIT_SIZE * 3);
        yPos = ((SCREEN_HEIGHT / 2) - UNIT_SIZE * 3);
        int rand = (int) (Math.random() * 10);
        if (rand < 5) {
            power = true;
        }
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public boolean getPowerUp() {
        return power;
    }

    public void setLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void move() {
        if (moveLeft) {
            this.xPos -= UNIT_SIZE;
        } else {
            this.xPos += UNIT_SIZE;
        }
    }
}