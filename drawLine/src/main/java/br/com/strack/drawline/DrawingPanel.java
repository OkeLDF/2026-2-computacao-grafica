package br.com.strack.drawline;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
/**
 *
 * @author okeldf
 */
// JPanel que implementa o algoritmo de desenho
class DrawingPanel extends JPanel {
// mousePressed e mouseReleased definem os pontos da linha
// algoritmo de Bresenham implementado aqui
    public int mouseX;
    public int mouseY;
    public int startX;
    public int startY;
    public boolean drawing = false;
    public ArrayList<Line> lines = new ArrayList<>();
    
    public DrawingPanel(){
        setBackground(Color.WHITE);
        
        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }
            
            @Override
            public void mouseDragged(MouseEvent e){
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }
        });
        
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                drawing = true;

                startX = e.getX();
                startY = e.getY();

                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e){
                drawing = false;
                
                mouseX = e.getX();
                mouseY = e.getY();

                lines.add(new Line(startX, startY, mouseX, mouseY));

                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        lines.forEach(line -> line.bresenham(g));
        
        if (drawing) {
            Line currentLine = new Line(startX, startY, mouseX, mouseY);
            currentLine.bresenham(g);
        }
        
        g.setFont(new Font("Serif", Font.PLAIN, 20));
        g.drawString("(" + mouseX + ", " + mouseY + ")", 10, this.getHeight() - 20);
    }
}