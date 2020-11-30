package in.spbhat;

import javax.swing.*;
import java.awt.*;

public class FocusPointer {

    public static void main(String[] args) {
        ExistingInstanceChecker.checkAndExit();
        JFrame baseFrame = new JFrame("Mouse Focus");
        baseFrame.setUndecorated(true);
        int numOfFocus = 10;
        for (int focus = 0; focus < numOfFocus; focus++) {
            int size = 40 - focus * 2;
            double delay = 0.1 + focus * 0.05;
            new Focus(size, delay, baseFrame);
        }
        baseFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Image icon = new ImageIcon(FocusPointer.class
                .getResource("media/cursor.png")).getImage();
        baseFrame.setIconImage(icon);
        baseFrame.setVisible(true);
    }
}
