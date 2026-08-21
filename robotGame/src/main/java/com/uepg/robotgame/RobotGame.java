package com.uepg.robotgame;

import javax.swing.SwingUtilities;

/**
 *
 * @author okeldf
 */

public class RobotGame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RobotGameFrame());
    }
}
