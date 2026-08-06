/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        
        g.drawString("(" + mouseX + ", " + mouseY + ")", 10, 20);
    }
}