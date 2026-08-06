/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.strack.drawline;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.JFrame;
/**
 *
 * @author okeldf
 */
// JFrame com JPanel personalizado
public class DrawingFrame extends JFrame {
// contém botão de seleção do algoritmo e painel de desenho
    public DrawingFrame() {
        setTitle("Draw a Line");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(new DrawingPanel());
        
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}