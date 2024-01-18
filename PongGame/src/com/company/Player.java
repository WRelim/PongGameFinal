package com.company;

import static com.company.GamePanel.*;

public class Player {
    private int[] y;
    private int x;
    private boolean speedPowerUp = false;

    Player(int xPos, int[] yPos) {
        x = xPos;
        y = yPos;
    }
    public int getX() {
        return x;
    }
    public int getY(int i) {
        return y[i];
    }

    public void setSpeedPowerUp(boolean speedPowerUp) {
        this.speedPowerUp = speedPowerUp;
    }
    public boolean getSpeedPowerUp() {
        return speedPowerUp;
    }

    public void moveUp() {
        if (y[0] > 0) {
            for (int i = 0; i < y.length; i ++) {
                if (!speedPowerUp) {
                    y[i] = y[i] - UNIT_SIZE;
                }
                else {
                    y[i] = y[i] - (UNIT_SIZE * 2);
                }
            }
        }
    }
    public void moveDown() {
        if (y[0] < SCREEN_HEIGHT - (UNIT_SIZE * y.length)) {
            for (int i = 0; i < y.length; i ++) {
                if (!speedPowerUp) {
                    y[i] = y[i] + UNIT_SIZE;
                }
                else {
                    y[i] = y[i] + (UNIT_SIZE * 2);
                }
            }
        }
    }
}
