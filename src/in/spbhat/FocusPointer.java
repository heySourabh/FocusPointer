package in.spbhat;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.locks.LockSupport;

public class FocusPointer {
    final static int width = 50;
    final static int height = 50;
    static Area windowShape;

    public static void main(String[] args) {
        Image cursorImage = new ImageIcon(FocusPointer.class
                .getResource("media/cursor.png")).getImage()
                .getScaledInstance(width, height, Image.SCALE_SMOOTH);
        windowShape = new Area(new Ellipse2D.Double(0, 0, width, height));
        double ovalSize = 4;
        Area oval2 = new Area(new Ellipse2D.Double(width * 0.5 - ovalSize,
                height * 0.5 - ovalSize, ovalSize * 2, ovalSize * 2));
        windowShape.subtract(oval2);
        JFrame frame = new JFrame() {
            @Override
            public void paint(Graphics g) {
                g.drawImage(cursorImage, 0, 0, this);
            }
        };
        frame.setIconImage(cursorImage);
        frame.setUndecorated(true);
        frame.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setShape(windowShape);
        frame.setVisible(true);

        Thread mouseWatch = new Thread(() -> {
            float opacity = 1.0f;
            while (true) {
                PointerInfo pointerInfo = MouseInfo.getPointerInfo();
                LockSupport.parkNanos(50_000_000);
                Point location = pointerInfo.getLocation();
                double frameX = frame.getLocation().x;
                double frameY = frame.getLocation().y;
                double targetX = location.x - width * 0.5;
                double targetY = location.y - height * 0.5;
                double dx = (targetX - frameX) * 0.9;
                double dy = (targetY - frameY) * 0.9;
                double distSqr = dx * dx + dy * dy;
                if (distSqr < 2) {
                    opacity *= 0.8;
                } else {
                    opacity = 1.0f;
                }
                frame.setOpacity(opacity);
                int x = (int) Math.round(frameX + dx);
                int y = (int) Math.round(frameY + dy);
                frame.setLocation(x, y);
            }
        });
        mouseWatch.setDaemon(true);
        mouseWatch.start();
    }
}
