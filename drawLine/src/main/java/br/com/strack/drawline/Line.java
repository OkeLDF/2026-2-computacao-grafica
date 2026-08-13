package br.com.strack.drawline;

import java.awt.Graphics;

public class Line {
    private int startX; 
    private int startY; 
    private int endX; 
    private int endY;
    
    public Line(int startX, int startY, int endX, int endY){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void bresenham(Graphics g) {
        int x = startX;
        int y = startY;

        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        int sx = startX < endX ? 1 : -1;
        int sy = startY < endY ? 1 : -1;

        int error = dx - dy;

        while (true) {

            g.fillRect(x, y, 1, 1);

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
