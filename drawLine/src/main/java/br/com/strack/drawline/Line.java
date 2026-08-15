package br.com.strack.drawline;

import java.awt.Color;
import java.awt.Graphics;

public class Line {
    private int startX; 
    private int startY; 
    private int endX; 
    private int endY;
    private int lineSize;
    private boolean usingBresenham;
    
    public Line(int startX, int startY, int endX, int endY, boolean usingBresenham, int lineSize){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.usingBresenham = usingBresenham;
        this.lineSize = lineSize;
    }

    public void draw(Graphics g) {
        if (this.usingBresenham) {
            this.bresenham(g);
        }
        else{
            this.naive(g);
        }
    }

    public void naive(Graphics g) {

        g.setColor(new Color(40, 80, 196));

        int x;
        double dy = endY - startY;
        double dx = endX - startX;
        double m = dy / dx;
        double y = startY;

        for (x = startX; x <= endX; x++) {

            g.fillRect(x, (int) Math.round(y), lineSize, lineSize);

            y = y + m;
        }
    }

    public void bresenham(Graphics g) {
        
        g.setColor(new Color(196, 40, 40));
        
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

    public boolean getUsingBresenham() {
        return usingBresenham;
    }

    public void setUsingBresenham(boolean usingBresenham) {
        this.usingBresenham = usingBresenham;
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
