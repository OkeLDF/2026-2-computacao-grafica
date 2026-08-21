package com.uepg.robotgame;

import java.lang.Math;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class AlienRobot {

    private double x;
    private double y;

    private double scale = 0.5;

    // -------------------------------------------------------------------------
    // Walking animation
    // -------------------------------------------------------------------------

    private double alpha = 0.0;

    private static final double LEG_RADIUS = 8.0;

    // 720 degrees per second
    private static final double WALK_SPEED = Math.toRadians(2160);

    private boolean walking = false;

    /*
     * +1 = positive movement direction
     * -1 = negative movement direction
     */
    private int movementDirection = 1;

    private MovementAxis movementAxis = MovementAxis.NONE;

    private enum MovementAxis {
        NONE,
        HORIZONTAL,
        VERTICAL
    }

    // -------------------------------------------------------------------------
    // Player movement
    // -------------------------------------------------------------------------

    private static final double MOVE_SPEED = 300.0;

    // -------------------------------------------------------------------------
    // Antenna
    // -------------------------------------------------------------------------
    
    private double antennaAngle = 0.0;

    // -------------------------------------------------------------------------
    // Laser
    // -------------------------------------------------------------------------

    private Line laser;

    private long laserStartTime;

    private static final long LASER_VISIBLE_TIME = 1_000_000L;
    private static final long LASER_FADE_TIME = 200_000_000L;

    private static final Color LASER_COLOR = new Color(139, 218, 13);

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public AlienRobot(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public AlienRobot(double x, double y, double scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    // -------------------------------------------------------------------------
    // Position
    // -------------------------------------------------------------------------

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // -------------------------------------------------------------------------
    // Scale
    // -------------------------------------------------------------------------

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return scale;
    }

    // -------------------------------------------------------------------------
    // Movement
    // -------------------------------------------------------------------------

    public void walkRight() {
        walking = true;
        movementAxis = MovementAxis.HORIZONTAL;
        movementDirection = 1;
    }

    public void walkLeft() {
        walking = true;
        movementAxis = MovementAxis.HORIZONTAL;
        movementDirection = -1;
    }

    public void walkDown() {
        walking = true;
        movementAxis = MovementAxis.VERTICAL;
        movementDirection = 1;
    }

    public void walkUp() {
        walking = true;
        movementAxis = MovementAxis.VERTICAL;
        movementDirection = -1;
    }

    public void stopWalking() {
        walking = false;
        movementAxis = MovementAxis.NONE;
    }

    // -------------------------------------------------------------------------
    // Antenna
    // -------------------------------------------------------------------------

    public void lookAt(double mouseX, double mouseY) {

        /*
         * Antenna pivot in the original SVG:
         *
         * (250, 255)
         */
        double pivotX = x + 250 * scale;
        double pivotY = y + 255 * scale;

        antennaAngle = Math.atan2(
                mouseY - pivotY,
                mouseX - pivotX
        ) + Math.PI / 2;
    }

    // -------------------------------------------------------------------------
    // Update
    // -------------------------------------------------------------------------

    public void update(double deltaTime) {

        updateLaser();

        if (!walking) {
            return;
        }

        // -------------------------------------------------------------
        // Move player
        // -------------------------------------------------------------

        switch (movementAxis) {

            case HORIZONTAL:
                x += MOVE_SPEED * movementDirection * deltaTime;
                break;

            case VERTICAL:
                y += MOVE_SPEED * movementDirection * deltaTime;
                break;

            case NONE:
                break;
        }

        // -------------------------------------------------------------
        // Animate legs
        // -------------------------------------------------------------

        alpha += WALK_SPEED * deltaTime * movementDirection;

        /*
         * Keep alpha in the range [0, 2π).
         */
        alpha %= Math.PI * 2;

        if (alpha < 0) {
            alpha += Math.PI * 2;
        }
    }

    // -------------------------------------------------------------------------
    // Laser
    // -------------------------------------------------------------------------

    public void drawLaser(Graphics g) {

        if (laser != null) {
            laser.bresenham(g);
        }
    }

    public void removeLaser() {
        laser = null;
    }

    private void updateLaser() {

        if (laser == null) {
            return;
        }

        long elapsed =
                System.nanoTime() - laserStartTime;

        if (elapsed <= LASER_VISIBLE_TIME) {

            laser.setOpacity(1.0f);
            return;
        }

        long fadeElapsed = elapsed - LASER_VISIBLE_TIME;

        if (fadeElapsed >= LASER_FADE_TIME) {

            laser = null;
            return;
        }

        float opacity =
                1.0f
                - (float) fadeElapsed / LASER_FADE_TIME;

        laser.setOpacity(opacity);
    }

    public void shootLaser(
        int mouseX,
        int mouseY,
        int screenWidth,
        int screenHeight
    ) {

        /*
        * Antenna pivot in SVG coordinates.
        */
        double pivotX = 250;
        double pivotY = 255;

        /*
        * Antenna extremity in SVG coordinates.
        *
        * The green topper ends at approximately y = 105.
        * Its center is x = 250.
        */
        double tipX = 250;
        double tipY = 105;

        /*
        * Rotate the antenna tip around its pivot.
        */
        double relativeX = tipX - pivotX;
        double relativeY = tipY - pivotY;

        double rotatedX =
                pivotX
                + relativeX * Math.cos(antennaAngle)
                - relativeY * Math.sin(antennaAngle);

        double rotatedY =
                pivotY
                + relativeX * Math.sin(antennaAngle)
                + relativeY * Math.cos(antennaAngle);

        /*
        * Convert from the robot's SVG coordinate system
        * into screen coordinates.
        */
        int startX = (int) Math.round(
                x + rotatedX * scale
        );

        int startY = (int) Math.round(
                y + rotatedY * scale
        );

        /*
        * Direction from antenna tip to mouse.
        */
        double dx = mouseX - startX;
        double dy = mouseY - startY;

        if (dx == 0 && dy == 0) {
            return;
        }

        /*
        * Find the first intersection of the ray with
        * the screen boundary.
        */
        double t = Double.POSITIVE_INFINITY;

        // Right
        if (dx > 0) {

            t = Math.min(
                    t,
                    (screenWidth - 1 - startX) / dx
            );
        }

        // Left
        if (dx < 0) {

            t = Math.min(
                    t,
                    (0 - startX) / dx
            );
        }

        // Bottom
        if (dy > 0) {

            t = Math.min(
                    t,
                    (screenHeight - 1 - startY) / dy
            );
        }

        // Top
        if (dy < 0) {

            t = Math.min(
                    t,
                    (0 - startY) / dy
            );
        }

        int endX = (int) Math.round(
                startX + dx * t
        );

        int endY = (int) Math.round(
                startY + dy * t
        );

        /*
        * Create the laser using the eye color.
        */
        laser = new Line(
            startX,
            startY,
            endX,
            endY,
            5,
            LASER_COLOR
        );

        /*
        * Start the laser timer.
        */
        laserStartTime = System.nanoTime();
    }

    // -------------------------------------------------------------------------
    // Leg animation
    // -------------------------------------------------------------------------

    /*
     * Leg Set A:
     *
     *     LegBackR
     *     LegFrontL
     *
     * Leg Set B:
     *
     *     LegBackL
     *     LegFrontR
     */

    private double getLegSetADX() {

        if (movementAxis == MovementAxis.VERTICAL) {
            return 0;
        }

        return LEG_RADIUS * (Math.cos(alpha) - 1);
    }

    private double getLegSetADY() {

        return LEG_RADIUS * Math.sin(alpha);
    }

    private double getLegSetBDX() {

        if (movementAxis == MovementAxis.VERTICAL) {
            return 0;
        }

        return LEG_RADIUS * (1 - Math.cos(alpha));
    }

    private double getLegSetBDY() {

        return -LEG_RADIUS * Math.sin(alpha);
    }

    // -------------------------------------------------------------------------
    // Drawing
    // -------------------------------------------------------------------------

    public void draw(Graphics2D g2d) {

        AffineTransform originalTransform = g2d.getTransform();

        /*
         * Move robot into the game world.
         */
        g2d.translate(x, y);

        /*
         * Scale the original SVG.
         */
        g2d.scale(scale, scale);

        /*
         * Preserve drawing order.
         */

        drawLegBackR(g2d);
        drawLegBackL(g2d);

        drawAntenna(g2d);

        drawFullBody(g2d);

        drawLegFrontR(g2d);
        drawLegFrontL(g2d);

        g2d.setTransform(originalTransform);
    }

    // -------------------------------------------------------------------------
    // Back legs
    // -------------------------------------------------------------------------

    private void drawLegBackR(Graphics2D g2d) {

        double dx = getLegSetADX();
        double dy = getLegSetADY();

        g2d.setColor(new Color(88, 5, 180));

        g2d.fill(new Rectangle2D.Double(
                320 + dx,
                250 + dy,
                50,
                90
        ));
    }

    private void drawLegBackL(Graphics2D g2d) {

        double dx = getLegSetBDX();
        double dy = getLegSetBDY();

        g2d.setColor(new Color(88, 5, 180));

        g2d.fill(new Rectangle2D.Double(
                130 + dx,
                250 + dy,
                50,
                90
        ));
    }

    // -------------------------------------------------------------------------
    // Antenna
    // -------------------------------------------------------------------------

    private void drawAntenna(Graphics2D g2d) {

        /*
         * Antenna pivot:
         *
         * (250, 255)
         */

        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(250, 255);
        g2d.rotate(antennaAngle);
        g2d.translate(-250, -255);

        drawAntennaBase(g2d);
        drawAntennaTopper(g2d);

        g2d.setTransform(oldTransform);
    }

    private void drawAntennaBase(Graphics2D g2d) {

        g2d.setColor(new Color(139, 13, 218));

        g2d.fill(new Rectangle2D.Double(
                242,
                120.894,
                16,
                134.106
        ));
    }

    private void drawAntennaTopper(Graphics2D g2d) {

        g2d.setColor(new Color(139, 218, 13));

        g2d.fill(new Rectangle2D.Double(
                242,
                105,
                16,
                15.894
        ));
    }

    // -------------------------------------------------------------------------
    // Full body
    // -------------------------------------------------------------------------

    private void drawFullBody(Graphics2D g2d) {

        drawBaseBody(g2d);
        drawEyeL(g2d);
        drawEyeR(g2d);
    }

    private void drawBaseBody(Graphics2D g2d) {

        g2d.setColor(new Color(139, 13, 218));

        g2d.fillRect(
                160,
                180,
                180,
                140
        );
    }

    private void drawEyeL(Graphics2D g2d) {

        g2d.setColor(new Color(139, 218, 13));

        g2d.fillRect(
                208,
                220,
                16,
                30
        );
    }

    private void drawEyeR(Graphics2D g2d) {

        g2d.setColor(new Color(139, 218, 13));

        g2d.fillRect(
                276,
                220,
                16,
                30
        );
    }

    // -------------------------------------------------------------------------
    // Front legs
    // -------------------------------------------------------------------------

    private void drawLegFrontR(Graphics2D g2d) {

        double dx = getLegSetBDX();
        double dy = getLegSetBDY();

        g2d.setColor(new Color(212, 42, 255));

        g2d.fill(new Rectangle2D.Double(
                260 + dx,
                275 + dy,
                50,
                90
        ));
    }

    private void drawLegFrontL(Graphics2D g2d) {

        double dx = getLegSetADX();
        double dy = getLegSetADY();

        g2d.setColor(new Color(212, 42, 255));

        g2d.fill(new Rectangle2D.Double(
                190 + dx,
                275 + dy,
                50,
                90
        ));
    }
}