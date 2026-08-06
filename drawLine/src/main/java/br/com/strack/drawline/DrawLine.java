/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package br.com.strack.drawline;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
/**
 *
 * @author okeldf
 */
public class DrawLine {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrawingFrame());
    }
}