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
    
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        
        int canvasW = 720;
        int canvasH = 720;
        int canvasCenterX = canvasW / 2;
        int canvasCenterY = canvasH / 2;

        
        // Segundo o exemplo em alemão, serve para reduzir o antialiasing (serrilhado)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        // Corpo
        int bodyW = 200;
        int bodyH = 100;
        int bodyX = canvasCenterX - (bodyW / 2);
        int bodyY = canvasCenterY - (bodyH / 2);
        Rectangle2D.Double bodyRect = new Rectangle2D.Double(
                bodyX, bodyY, bodyW, bodyH
        );
        Area bodyArea = new Area(bodyRect);
        g2d.setPaint(Color.GRAY);
        g2d.fill(bodyArea);
        
        
        // Círculo da cabeça
        int headCircleW = 60;
        int headCircleH = 60;
        int headCircleX = (bodyX + bodyW) - (headCircleW + 20);
        int headCircleY = bodyY - (headCircleH);
        Ellipse2D.Double headCircle = new Ellipse2D.Double(
                headCircleX, headCircleY, headCircleW, headCircleH
        );
        Area headCircleArea = new Area(headCircle);
        
        
        // Retângulo da cabeça
        int headRectW = headCircleW;
        int headRectH = headCircleH / 2;
        int headRectX = headCircleX;
        int headRectY = headCircleY + (headCircleH / 2);
        Rectangle2D.Double headRect = new Rectangle2D.Double(
                headRectX, headRectY, headRectW, headRectH
        );
        Area headRectArea = new Area(headRect);
        
        
        // Cabeça
        headRectArea.add(headCircleArea);
        g2d.setPaint(Color.GRAY);
        g2d.fill(headRectArea);
        
        
        // Olho
        int eyeCircleW = 25;
        int eyeCircleH = 25;
        int eyeCircleX = headCircleX + (headCircleW / 2) - (eyeCircleW / 2);
        int eyeCircleY = headCircleY + (headCircleH / 2) - (eyeCircleH / 2);
        Ellipse2D.Double eyeCircle = new Ellipse2D.Double(
                eyeCircleX, eyeCircleY, eyeCircleW, eyeCircleH
        );
        Area eyeCircleArea = new Area(eyeCircle);
        g2d.setPaint(Color.RED);
        g2d.fill(eyeCircleArea);
        
        
        // Antena
//        GeneralPath antennaGp = GeneralPath();
//        int originX = headCircleX + (headCircleW / 2);
//        int originY = headCircleY;
//        antennaGp.moveTo(originX, originY);
//        antennaGp.lineTo(originX, originY - 10);
    }

    public static void main(String[] args) {
        Robot robot = new Robot();
        robot.setTitle("Robot");
        robot.setSize(720, 720);
        robot.setVisible(true);
    }
}
