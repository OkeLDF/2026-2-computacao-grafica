package br.com.strack.drawline;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
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
    
    public DrawingPanel(){
        setBackground(Color.WHITE);
        
        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.setFont(new Font("Serif", Font.PLAIN, 20));
        g.drawString("(" + mouseX + ", " + mouseY + ")", 10, 25);
    }
}