package com.uepg.robotgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RobotGamePanel extends JPanel implements KeyListener {

    private final AlienRobot robot;

    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;

    /*
     * The most recently pressed movement key.
     *
     * This determines which direction has priority.
     */
    private int lastMovementKey = -1;

    private long lastUpdate;

    public RobotGamePanel() {

        setBackground(Color.WHITE);

        robot = new AlienRobot(800, 600, 0.5);

        // ---------------------------------------------------------------------
        // Mouse
        // ---------------------------------------------------------------------

        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                robot.lookAt(e.getX(), e.getY());
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                robot.lookAt(e.getX(), e.getY());
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                robot.shootLaser(
                        e.getX(),
                        e.getY(),
                        getWidth(),
                        getHeight()
                );

                repaint();
            }
        });

        // ---------------------------------------------------------------------
        // Keyboard
        // ---------------------------------------------------------------------

        setFocusable(true);
        addKeyListener(this);

        // ---------------------------------------------------------------------
        // Game loop
        // ---------------------------------------------------------------------

        lastUpdate = System.nanoTime();

        Timer timer = new Timer(1000 / 60, e -> updateGame());

        timer.start();

        requestFocusInWindow();
    }

    // =========================================================================
    // KEYBOARD
    // =========================================================================

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:

                moveLeft = true;
                lastMovementKey = e.getKeyCode();
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:

                moveRight = true;
                lastMovementKey = e.getKeyCode();
                break;

            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:

                moveUp = true;
                lastMovementKey = e.getKeyCode();
                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:

                moveDown = true;
                lastMovementKey = e.getKeyCode();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:

                moveLeft = false;
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:

                moveRight = false;
                break;

            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:

                moveUp = false;
                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:

                moveDown = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used.
    }

    // =========================================================================
    // GAME LOOP
    // =========================================================================

    private void updateGame() {

        long now = System.nanoTime();

        double deltaTime =
                (now - lastUpdate) / 1_000_000_000.0;

        lastUpdate = now;

        updateMovement();

        robot.update(deltaTime);

        repaint();
    }

    // =========================================================================
    // MOVEMENT
    // =========================================================================

    private void updateMovement() {

        /*
         * The most recently pressed key has priority.
         */
        switch (lastMovementKey) {

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:

                if (moveLeft) {
                    robot.walkLeft();
                    return;
                }

                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:

                if (moveRight) {
                    robot.walkRight();
                    return;
                }

                break;

            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:

                if (moveUp) {
                    robot.walkUp();
                    return;
                }

                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:

                if (moveDown) {
                    robot.walkDown();
                    return;
                }

                break;
        }

        /*
         * The last pressed key was released.
         *
         * Find another movement key that is still being held.
         */
        selectAnotherPressedKey();
    }

    private void selectAnotherPressedKey() {

        /*
         * Priority here is only used when the previously active
         * key was released.
         */

        if (moveLeft) {

            lastMovementKey = KeyEvent.VK_A;
            robot.walkLeft();

        } else if (moveRight) {

            lastMovementKey = KeyEvent.VK_D;
            robot.walkRight();

        } else if (moveUp) {

            lastMovementKey = KeyEvent.VK_W;
            robot.walkUp();

        } else if (moveDown) {

            lastMovementKey = KeyEvent.VK_S;
            robot.walkDown();

        } else {

            lastMovementKey = -1;
            robot.stopWalking();
        }
    }

    // =========================================================================
    // DRAWING
    // =========================================================================

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        robot.draw(g2d);

        robot.drawLaser(g2d);

        g2d.dispose();
    }
}