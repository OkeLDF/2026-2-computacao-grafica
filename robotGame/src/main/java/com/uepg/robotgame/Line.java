package com.uepg.robotgame;

import java.awt.Color;
import java.awt.Graphics;

public class Line {
    private int startX; 
    private int startY; 
    private int endX; 
    private int endY;
    private int lineSize;

    private Color color;
    private float opacity = 1.0f;
    
    public Line(int startX, int startY, int endX, int endY, int lineSize, Color color){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.lineSize = lineSize;
        this.color = color;
    }
    
    public Line(int startX, int startY, int endX, int endY, int lineSize){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.lineSize = lineSize;
        this.color = new Color(139, 218, 13);
    }

    public void bresenham(Graphics g) {
        
        int alpha = Math.round(opacity * 255);

        g.setColor(new Color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                alpha
        ));
        
        int x = startX;
        int y = startY;

        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        int sx = startX < endX ? 1 : -1;
        int sy = startY < endY ? 1 : -1;

        int error = dx - dy;

        while (true) {

            g.fillRect(x, y, lineSize, lineSize);

            if (x == endX && y == endY) {
                break;
            }

            int e2 = 2 * error;

            if (e2 > -dy) {
                error -= dy;
                x += sx;
            }

            if (e2 < dx) {
                error += dx;
                y += sy;
            }
        }
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public void setStartX(int newStartX) {
        this.startX = newStartX;
    }

    public void setStartY(int newStartY) {
        this.startY = newStartY;
    }

    public void setEndX(int newEndX) {
        this.endX = newEndX;
    }

    public void setEndY(int newEndY) {
        this.endY = newEndY;
    }
}
