package in.spbhat;

import javax.swing.*;

public class FocusPointer {

    public static void main(String[] args) {
        JFrame baseFrame = new JFrame("Mouse Focus");
        new Focus(50, 0.1, baseFrame);
        new Focus(45, 0.2, baseFrame);
        new Focus(40, 0.3, baseFrame);
        new Focus(35, 0.4, baseFrame);
        new Focus(30, 0.5, baseFrame);
        baseFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        baseFrame.setVisible(true);
    }
}
