package com.company;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_HEIGHT = 600;
    static final int SCREEN_WIDTH = 1200;
    static final int UNIT_SIZE = 10;
    static final int SCORE_GOAL = 5;
    static int delay = 50;
    static final int PLAYER_LENGTH = 8;
    int[] yPosPlayer1 = new int[PLAYER_LENGTH];
    int[] yPosPlayer2 = new int[PLAYER_LENGTH];
    Player player1, player2;
    int score1, score2 = 0;
    Ball ball, ball2;
    PowerUp powerUp;
    boolean wIsHeld, sIsHeld, upIsHeld, downIsHeld;
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }

    private void startGame() {
        newBall();
        ball2 = null;
        running = true;
        timer = new Timer(delay, this);
        timer.start();
        for (int i = 0; i < PLAYER_LENGTH; i++) {
            yPosPlayer1[i] = (SCREEN_HEIGHT / 2) + (UNIT_SIZE * i);
            yPosPlayer2[i] = (SCREEN_HEIGHT / 2) + (UNIT_SIZE * i);
        }
        player1 = new Player(SCREEN_WIDTH / 10, yPosPlayer1);
        player2 = new Player((SCREEN_WIDTH / 10) * 9, yPosPlayer2);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            if (powerUp != null) {
                if(powerUp.getPowerUp()) {
                    //multiball
                    g.setColor(new Color(16, 16, 40));
                }
                else {
                    //Player speed up
                    g.setColor(new Color(50, 10, 10));
                }
                g.fillOval(powerUp.getX(), powerUp.getY(), UNIT_SIZE * 6, UNIT_SIZE * 6);
            }
            if (ball != null) {
                g.setColor(Color.GRAY);
                g.fillOval(ball.getX(), ball.getY(), UNIT_SIZE * 2, UNIT_SIZE * 2);
            }

            if (ball2 != null) {
                g.fillOval(ball2.getX(), ball2.getY(), UNIT_SIZE * 2, UNIT_SIZE * 2);
            }
            if (player1.getSpeedPowerUp()) {
                g.setColor(Color.YELLOW);
                for (int i = 0; i < PLAYER_LENGTH; i++) {
                    g.fillRect(player1.getX(), player1.getY(i), UNIT_SIZE, UNIT_SIZE);
                }
                g.setColor(Color.GRAY);
            }
            else {
                for (int i = 0; i < PLAYER_LENGTH; i++) {
                    g.fillRect(player1.getX(), player1.getY(i), UNIT_SIZE, UNIT_SIZE);
                }
            }
            if (player2.getSpeedPowerUp()) {
                g.setColor(Color.YELLOW);
                for (int i = 0; i < PLAYER_LENGTH; i++) {
                    g.fillRect(player2.getX(), player2.getY(i), UNIT_SIZE, UNIT_SIZE);
                }
                g.setColor(Color.GRAY);
            }
            else {
                for (int i = 0; i < PLAYER_LENGTH; i++) {
                    g.fillRect(player2.getX(), player2.getY(i), UNIT_SIZE, UNIT_SIZE);
                }
            }

            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("P1 W/S - P2 ^/v", (SCREEN_WIDTH - metrics.stringWidth("P1 W/S - P2 ^/v")) / 2, g.getFont().getSize());
            g.drawString(score1 + " - " + score2, (SCREEN_WIDTH - metrics.stringWidth(score1 + " - " + score2)) / 2, (g.getFont().getSize()) * 2);
        } else {
            gameOver(g);
        }
    }

    public void newBall() {
        ball = new Ball();
    }

    public void checkConditionsBall1() {
        if (ball.getY() < 0) {
            //ball reaches top barrier
            ball.setUp(false);
        } else if (ball.getY() > SCREEN_HEIGHT - UNIT_SIZE && ball != null) {
            //ball reaches bottom barrier
            ball.setUp(true);
        }
        //ball collides with player 1
        if ((player1.getY(0) <= ball.getY() && ball.getY() <= (player1.getY(PLAYER_LENGTH - 1)))) {
            if (player1.getX() <= ball.getX() && ball.getX() <= player1.getX() + UNIT_SIZE) {
                ball.setLeft(false);
                delay += (delay / 20);
            }
        }
        //ball collides with player 2
        if ((player2.getY(0) <= ball.getY() && ball.getY() <= (player2.getY(PLAYER_LENGTH - 1)))) {
            if (player2.getX() - UNIT_SIZE <= ball.getX() && ball.getX() <= player2.getX()) {
                ball.setLeft(true);
                delay += (delay / 20);
            }
        }
        if (ball != null) {
            if (ball.getX() < UNIT_SIZE) {
                //player2 scores
                score2++;
                ball = null;
                if (ball2 == null) {
                    newBall();
                    delay = 50;
                    ball.setLeft(true);
                    player1.setSpeedPowerUp(false);
                    player2.setSpeedPowerUp(false);
                }
            } else if (ball.getX() > SCREEN_WIDTH - UNIT_SIZE) {
                //player1 scores
                score1++;
                ball = null;
                if (ball2 == null) {
                    newBall();
                    delay = 50;
                    ball.setLeft(false);
                    player1.setSpeedPowerUp(false);
                    player2.setSpeedPowerUp(false);
                }
            }
            //end game
            if (score1 >= SCORE_GOAL || score2 >= SCORE_GOAL) {
                running = false;
            }
        }
    }

    public void checkConditionsBall2() {
        if (ball2 != null) {
            if (ball2.getX() < UNIT_SIZE) {
                //player2 scores
                score2++;
                ball2 = null;
                if (ball == null) {
                    powerUp = null;
                    newBall();
                    delay = 50;
                    ball.setLeft(true);
                    player1.setSpeedPowerUp(false);
                    player2.setSpeedPowerUp(false);
                }
            } else if (ball2.getX() > SCREEN_WIDTH - UNIT_SIZE) {
                //player1 scores
                score1++;
                ball2 = null;
                if (ball == null) {
                    powerUp = null;
                    newBall();
                    delay = 50;
                    ball.setLeft(false);
                    player1.setSpeedPowerUp(false);
                    player2.setSpeedPowerUp(false);
                }
            }
            if (ball2 != null && ball2.getY() < 0) {
                //ball2 reaches top barrier
                ball2.setUp(false);
            }
            if (ball2 != null && ball2.getY() > SCREEN_HEIGHT - UNIT_SIZE) {
                //ball2 reaches bottom barrier
                ball2.setUp(true);
            }
            //ball2 collides with player 1
            if (ball2 != null) {
                if ((player1.getY(0) <= ball2.getY() && ball2.getY() <= (player1.getY(PLAYER_LENGTH - 1)))) {
                    if (player1.getX() <= ball2.getX() && ball2.getX() <= player1.getX() + UNIT_SIZE) {
                        ball2.setLeft(false);
                        delay += (delay / 20);
                    }
                }
                //ball2 collides with player 2
                if ((player2.getY(0) <= ball2.getY() && ball2.getY() <= (player2.getY(PLAYER_LENGTH - 1)))) {
                    if (player2.getX() - UNIT_SIZE <= ball2.getX() && ball2.getX() <= player2.getX()) {
                        ball2.setLeft(true);
                        delay += (delay / 20);
                    }
                }
            }
            //end game
            if (score1 >= SCORE_GOAL || score2 >= SCORE_GOAL) {
                running = false;
            }
        }
    }

    public void newPowerUp() {
        powerUp = new PowerUp();
    }

    public void checkConditionsPowerUp() {
        if (800 < powerUp.getX()) {
            powerUp.setLeft(true);
        }
        if (400 > powerUp.getX()) {
            powerUp.setLeft(false);
        }
        //ball collides with power up
        if ((powerUp.getY() <= ball.getY()) && (ball.getY() <= (powerUp.getY() + (UNIT_SIZE * 6)))) {
            if ((powerUp.getX() <= ball.getX()) && (ball.getX() <= (powerUp.getX() + (UNIT_SIZE * 6)))) {
                if (powerUp.getPowerUp()) {
                    if (ball2 == null || ball == null) {
                        ball2 = new Ball();
                        powerUp = null;
                    }
                } else if (ball.moveLeft && !powerUp.getPowerUp()) {
                    player2.setSpeedPowerUp(true);
                    powerUp = null;
                } else if (!ball.moveLeft && !powerUp.getPowerUp()) {
                    player1.setSpeedPowerUp(true);
                    powerUp = null;
                }
            }
        }
    }
    public void gameOver(Graphics g) {
        if (score1 < score2) {
            //Score
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score:" + score1 + " - " + score2, (SCREEN_WIDTH - metrics1.stringWidth("Score:" + score1 + " - " + score2)) / 2, g.getFont().getSize());

            //Game Over text
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Player 2 Wins!", (SCREEN_WIDTH - metrics2.stringWidth("Player 2 Wins! ")) / 2, SCREEN_HEIGHT / 2);
        } else {
            //Score
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score:" + score1 + " - " + score2, (SCREEN_WIDTH - metrics1.stringWidth("Score:" + score1 + " - " + score2)) / 2, g.getFont().getSize());

            //Game Over text
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Player 1 Wins!", (SCREEN_WIDTH - metrics2.stringWidth("Player 1 Wins! ")) / 2, SCREEN_HEIGHT / 2);
        }
    }

    public void move() {
        if (wIsHeld) {
            player1.moveUp();
        }
        if (sIsHeld) {
            player1.moveDown();
        }
        if (upIsHeld) {
            player2.moveUp();
        }
        if (downIsHeld) {
            player2.moveDown();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            if (ball != null) {
                ball.move();
                checkConditionsBall1();
            }
            if (ball2 != null) {
                ball2.move();
                checkConditionsBall2();
            }
            if (random.nextInt(500) == 0 && powerUp == null) {
                if (ball2 == null){
                    newPowerUp();
                }
            }
            if (powerUp != null) {
                powerUp.move();
                checkConditionsPowerUp();
            }
        }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    wIsHeld = true;
                    break;
                case KeyEvent.VK_S:
                    sIsHeld = true;
                    break;
                case KeyEvent.VK_UP:
                    upIsHeld = true;
                    break;
                case KeyEvent.VK_DOWN:
                    downIsHeld = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    wIsHeld = false;
                    break;
                case KeyEvent.VK_S:
                    sIsHeld = false;
                    break;
                case KeyEvent.VK_UP:
                    upIsHeld = false;
                    break;
                case KeyEvent.VK_DOWN:
                    downIsHeld = false;
                    break;
            }
        }
    }
}