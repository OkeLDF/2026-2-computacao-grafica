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
    private final Color robotBodyPrimaryColor = new Color(232, 232, 232);
    private final Color robotBodySecondaryColor = Color.GRAY;
    private final Color robotDetailColor = new Color(40,80, 255);
    private final Color robotLightsColor = new Color(255, 80, 80);

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

        robot.add(drawLeftLeg(g2d));
        robot.add(drawRightLeg(g2d));
        robot.add(drawLeftArm(g2d));
        robot.add(drawRightArm(g2d));
        robot.add(drawBody(g2d));
        robot.add(drawHead(g2d));

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

        g2d.setPaint(robotBodyPrimaryColor);
        g2d.fill(body);

        AffineTransform backup = g2d.getTransform();
        Rectangle2D.Double detail1Rect = new Rectangle2D.Double(
            bodyX + 50, bodyY - 40, 30, bodyH * 2
        );
        AffineTransform rotacao = new AffineTransform();
        rotacao.rotate(Math.toRadians(30), detail1Rect.getCenterX(), detail1Rect.getCenterY());
        Area detail1 = new Area(detail1Rect);
        detail1.transform(rotacao);
        Area bodyArea = new Area(bodyRect);
        detail1.intersect(bodyArea);
        g2d.setPaint(robotDetailColor);
        g2d.fill(detail1);

        Rectangle2D.Double detail2Rect = new Rectangle2D.Double(
            bodyX + 30, bodyY-40, 10, bodyH * 2
        );
        AffineTransform rotacao2 = new AffineTransform();
        rotacao2.rotate(Math.toRadians(30), detail2Rect.getCenterX(), detail2Rect.getCenterY());
        Area detail2 = new Area(detail2Rect);
        detail2.transform(rotacao2);
        detail2.intersect(bodyArea);
        g2d.setPaint(robotDetailColor);
        g2d.fill(detail2);
        g2d.setTransform(backup);

        // Botões
        int btnD = 10;
        int btnY = bodyY + 12;
        int btn1X = bodyX + 12;
        Ellipse2D.Double btn1 = new Ellipse2D.Double(
            btn1X, btnY, btnD, btnD);

        g2d.setPaint(robotLightsColor);
        g2d.fill(btn1);
        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(btn1);


        int btn2X = btn1X + btnD + 6;
        Ellipse2D.Double btn2 = new Ellipse2D.Double(
            btn2X, btnY, btnD, btnD);

        g2d.setPaint(robotLightsColor);
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
        g2d.setPaint(robotBodyPrimaryColor);
        g2d.fill(head);

        // Olho
        int eyeCircleR = 25;
        int eyeX = headCircleX + headCircleW / 2 - eyeCircleR / 2;
        int eyeY = headCircleY + headCircleH / 2 - eyeCircleR / 2;
        Ellipse2D.Double eye = new Ellipse2D.Double(
            eyeX, eyeY, eyeCircleR, eyeCircleR);

        g2d.setPaint(robotLightsColor);
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
        int outerR = 150;
        Ellipse2D.Double outerArmCircle = new Ellipse2D.Double(
            bodyX - (outerR / 2), bodyY + 10, outerR, outerR
        );
        
        int innerR = outerR - 50;
        Ellipse2D.Double innerArmCircle = new Ellipse2D.Double(
            outerArmCircle.getCenterX() - (innerR / 2), outerArmCircle.getCenterY() - (innerR / 2), innerR, innerR
        );

        Rectangle2D.Double subRect = new Rectangle2D.Double(
            0, bodyY + bodyH - 5, canvasCenterX, canvasCenterY
        );
        
        Area arm = new Area(outerArmCircle);
        arm.subtract(new Area(innerArmCircle));
        arm.subtract(new Area(subRect));
        g2d.setPaint(robotBodySecondaryColor);
        g2d.fill(arm);
        
        int outerHandR = 60;
        double handX = arm.getBounds().x - 16.5;
        int handY = arm.getBounds().y + arm.getBounds().height - 5;
        Ellipse2D.Double outerHandCircle = new Ellipse2D.Double(
            handX, handY, outerHandR, outerHandR
        );
        
        int innerHandR = outerHandR - 30;
        Ellipse2D.Double innerHandCircle = new Ellipse2D.Double(
            outerHandCircle.getCenterX() - (innerHandR / 2), outerHandCircle.getCenterY() - (innerHandR / 2), innerHandR, innerHandR
        );

        int subHandW = 15;
        Rectangle2D.Double subHandRect = new Rectangle2D.Double(
            innerHandCircle.getCenterX() - (subHandW / 2), innerHandCircle.getCenterY(), subHandW, 40
        );

        Area hand = new Area(outerHandCircle);
        hand.subtract(new Area(innerHandCircle));
        hand.subtract(new Area(subHandRect));
        g2d.setPaint(robotBodyPrimaryColor);
        g2d.fill(hand);

        arm.add(hand);

        return arm;
    }

    private Area drawRightArm(Graphics2D g2d) {
        int outerR = 150;
        Ellipse2D.Double outerArmCircle = new Ellipse2D.Double(
            bodyX + bodyW - (outerR / 2), bodyY + 10, outerR, outerR
        );
        
        int innerR = outerR - 50;
        Ellipse2D.Double innerArmCircle = new Ellipse2D.Double(
            outerArmCircle.getCenterX() - (innerR / 2), outerArmCircle.getCenterY() - (innerR / 2), innerR, innerR
        );

        Rectangle2D.Double subRect = new Rectangle2D.Double(
            canvasCenterX, bodyY + bodyH - 5, canvasCenterX, canvasCenterY
        );
        
        Area arm = new Area(outerArmCircle);
        arm.subtract(new Area(innerArmCircle));
        arm.subtract(new Area(subRect));
        g2d.setPaint(robotBodySecondaryColor);
        g2d.fill(arm);
        
        int outerHandR = 60;
        double handX = arm.getBounds().x + (arm.getBounds().width / 2) + 31.5;
        int handY = arm.getBounds().y + arm.getBounds().height - 5;
        Ellipse2D.Double outerHandCircle = new Ellipse2D.Double(
            handX, handY, outerHandR, outerHandR
        );
        
        int innerHandR = outerHandR - 30;
        Ellipse2D.Double innerHandCircle = new Ellipse2D.Double(
            outerHandCircle.getCenterX() - (innerHandR / 2), outerHandCircle.getCenterY() - (innerHandR / 2), innerHandR, innerHandR
        );

        int subHandW = 15;
        Rectangle2D.Double subHandRect = new Rectangle2D.Double(
            innerHandCircle.getCenterX() - (subHandW / 2), innerHandCircle.getCenterY(), subHandW, 40
        );

        Area hand = new Area(outerHandCircle);
        hand.subtract(new Area(innerHandCircle));
        hand.subtract(new Area(subHandRect));
        g2d.setPaint(robotBodyPrimaryColor);
        g2d.fill(hand);

        arm.add(hand);

        return arm;
    }

    private Area drawLeftLeg(Graphics2D g2d) {
        int legGap = 20;
        int legH = 80;
        int legW = 30;
        Rectangle2D.Double legRect = new Rectangle2D.Double(
            bodyX + (bodyW / 2) + legGap, bodyY + bodyH, legW, legH
        );
        g2d.setPaint(robotBodySecondaryColor);
        g2d.fill(legRect);

        Area leg = new Area(legRect);

        int[] xPoints = {
            leg.getBounds().x, leg.getBounds().x + legW, leg.getBounds().x + legW + 20, leg.getBounds().x
        };
        int[] yPoints = {
            leg.getBounds().y + legH, leg.getBounds().y + legH, leg.getBounds().y + legH + 20, leg.getBounds().y + legH + 20
        };
        int nPoints = 4;

        Polygon foot = new Polygon(xPoints, yPoints, nPoints);

        g2d.setPaint(robotBodyPrimaryColor);
        g2d.fill(foot);

        leg.add(new Area(foot));

        return leg;
    }

    private Area drawRightLeg(Graphics2D g2d) {
        int legGap = 20;
        int legH = 80;
        int legW = 30;
        Rectangle2D.Double legRect = new Rectangle2D.Double(
            bodyX + (bodyW / 2) - (legGap + legW), bodyY + bodyH, legW, legH
        );
        g2d.setPaint(robotBodySecondaryColor);
        g2d.fill(legRect);

        Area leg = new Area(legRect);

        int[] xPoints = {
            leg.getBounds().x, leg.getBounds().x - 20, leg.getBounds().x + legW, leg.getBounds().x + legW
        };
        int[] yPoints = {
            leg.getBounds().y + legH, leg.getBounds().y + legH + 20, leg.getBounds().y + legH + 20, leg.getBounds().y + legH
        };
        int nPoints = 4;

        Polygon foot = new Polygon(xPoints, yPoints, nPoints);

        g2d.setPaint(robotBodyPrimaryColor);
        g2d.fill(foot);

        leg.add(new Area(foot));

        return leg;
    }

    public static void main(String[] args) {
        Robot robot = new Robot();
        robot.setTitle("Robot");
        robot.setSize(robot.canvasW, robot.canvasH);
        robot.setVisible(true);
    }
}
