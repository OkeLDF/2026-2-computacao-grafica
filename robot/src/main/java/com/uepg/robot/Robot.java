/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
*/

package com.uepg.robot;

import java.awt.*;
import java.awt.geom.*;

/**
 *
 * @author okeldf
 */
public class Robot extends Frame {

    // Canvas
    private final int canvasW = 480;
    private final int canvasH = 480;
    private final int canvasCenterX = canvasW / 2;
    private final int canvasCenterY = canvasH / 2;

    // Corpo
    private final int bodyW = 200;
    private final int bodyH = 100;
    private final int bodyX = canvasCenterX - (bodyW / 2);
    private final int bodyY = canvasCenterY - (bodyH / 2);

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Area robot = new Area();

        robot.add(drawBody(g2d));
        robot.add(drawHead(g2d));
        robot.add(drawLeftLeg(g2d));
        robot.add(drawRightLeg(g2d));
        robot.add(drawLeftArm(g2d));
        robot.add(drawRightArm(g2d));

        // Outline do robô inteiro
        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));
        g2d.draw(robot);
    }

    private Area drawBody(Graphics2D g2d) {
        // Corpo
        Rectangle2D.Double bodyRect = new Rectangle2D.Double(
                bodyX, bodyY, bodyW, bodyH);

        Area body = new Area(bodyRect);

        g2d.setPaint(Color.GRAY);
        g2d.fill(body);

        // Botões
        int btnD = 10;
        int btnY = bodyY + 12;
        int btn1X = bodyX + 12;
        Ellipse2D.Double btn1 = new Ellipse2D.Double(
            btn1X, btnY, btnD, btnD);

        g2d.setPaint(Color.RED);
        g2d.fill(btn1);
        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(btn1);


        int btn2X = btn1X + btnD + 6;
        Ellipse2D.Double btn2 = new Ellipse2D.Double(
            btn2X, btnY, btnD, btnD);

        g2d.setPaint(Color.RED);
        g2d.fill(btn2);
        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(btn2);



        body.add(new Area(btn1));
        body.add(new Area(btn2));

        return body;
    }

    private Area drawHead(Graphics2D g2d) {

        // Círculo da cabeça
        int headCircleW = 60;
        int headCircleH = 60;
        int headCircleX = (bodyX + bodyW) - (headCircleW + 20);
        int headCircleY = bodyY - headCircleH;

        Ellipse2D.Double headCircle = new Ellipse2D.Double(
                headCircleX, headCircleY,
                headCircleW, headCircleH);

        // Retângulo da cabeça
        Rectangle2D.Double headRect = new Rectangle2D.Double(
                headCircleX,
                headCircleY + headCircleH / 2,
                headCircleW,
                headCircleH / 2);

        Area head = new Area(headCircle);
        head.add(new Area(headRect));

        // Cabeça
        g2d.setPaint(Color.GRAY);
        g2d.fill(head);

        // Olho
        int eyeCircleR = 25;
        int eyeX = headCircleX + headCircleW / 2 - eyeCircleR / 2;
        int eyeY = headCircleY + headCircleH / 2 - eyeCircleR / 2;
        Ellipse2D.Double eye = new Ellipse2D.Double(
            eyeX, eyeY, eyeCircleR, eyeCircleR);

        g2d.setPaint(Color.RED);
        g2d.fill(eye);
        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(eye);
        
        // Boca
        int mouthX = eyeX;
        int mouthY = eyeY + eyeCircleR + 8;
        int mouthW = eyeCircleR;
        int mouthH = 4;
        Rectangle2D.Double mouth = new Rectangle2D.Double(
            mouthX, mouthY, mouthW, mouthH);
            
        g2d.setPaint(Color.LIGHT_GRAY);
        g2d.fill(mouth);
        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(mouth);

        head.add(new Area(mouth));
        
        // Antena
        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));

        int antennaOriginX = headCircleX + headCircleW / 2;
        int antennaOriginY = headCircleY;
        int antennaBarSize = 20;
        int antennaFinSize = 8;
        int antennaTopR = 8;

        GeneralPath antenna = new GeneralPath();

        antenna.moveTo(antennaOriginX, antennaOriginY);
        antenna.lineTo(antennaOriginX, antennaOriginY - antennaBarSize);

        antenna.moveTo(
                antennaOriginX - antennaFinSize / 2,
                antennaOriginY - antennaBarSize / 3);
        antenna.lineTo(
                antennaOriginX + antennaFinSize / 2,
                antennaOriginY - antennaBarSize / 3);

        antenna.moveTo(
                antennaOriginX - antennaFinSize / 2,
                antennaOriginY - 2 * antennaBarSize / 3);
        antenna.lineTo(
                antennaOriginX + antennaFinSize / 2,
                antennaOriginY - 2 * antennaBarSize / 3);

        g2d.draw(antenna);

        Ellipse2D.Double antennaTop = new Ellipse2D.Double(
                antennaOriginX - 4,
                antennaOriginY - antennaBarSize - 8,
                antennaTopR, antennaTopR);

        g2d.fill(antennaTop);

        return head;
    }

    private Area drawLeftArm(Graphics2D g2d) {
        Area arm = new Area();


        return arm;
    }

    private Area drawRightArm(Graphics2D g2d) {
        Area arm = new Area();


        return arm;
    }

    private Area drawLeftLeg(Graphics2D g2d) {
        Area leg = new Area();


        return leg;
    }

    private Area drawRightLeg(Graphics2D g2d) {
        Area leg = new Area();


        return leg;
    }

    public static void main(String[] args) {
        Robot robot = new Robot();
        robot.setTitle("Robot");
        robot.setSize(robot.canvasW, robot.canvasH);
        robot.setVisible(true);
    }
}
