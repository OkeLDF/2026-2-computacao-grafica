package com.uepg.robotgame;

import javax.swing.JFrame;

public class RobotGameFrame extends JFrame {
    public RobotGameFrame() {
        setTitle("A Robot Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(new RobotGamePanel());
        
        setSize(1900, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
