package in.spbhat;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.locks.LockSupport;

public class Focus {
    public Focus(int size, double delay, JFrame baseFrame) {
        Area windowShape;

        Image cursorImage = new ImageIcon(FocusPointer.class
                .getResource("media/cursor.png")).getImage()
                .getScaledInstance(size, size, Image.SCALE_SMOOTH);
        windowShape = new Area(new Ellipse2D.Double(0, 0, size, size));
        double ovalSize = 4;
        Area oval2 = new Area(new Ellipse2D.Double(size * 0.5 - ovalSize,
                size * 0.5 - ovalSize, ovalSize * 2, ovalSize * 2));
        windowShape.subtract(oval2);
        JWindow window = new JWindow(baseFrame) {
            @Override
            public void paint(Graphics g) {
                g.drawImage(cursorImage, 0, 0, this);
            }
        };
        window.setIconImage(cursorImage);
        window.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
        window.setAlwaysOnTop(true);
        window.setSize(size, size);
        window.setLocationRelativeTo(null);
        window.setShape(windowShape);
        window.setVisible(true);

        Thread mouseWatch = new Thread(() -> {
            float opacity = 1.0f;
            while (true) {
                PointerInfo pointerInfo = MouseInfo.getPointerInfo();
                LockSupport.parkNanos(50_000_000);
                Point location = pointerInfo.getLocation();
                double frameX = window.getLocation().x;
                double frameY = window.getLocation().y;
                double targetX = location.x - size * 0.5;
                double targetY = location.y - size * 0.5;
                double dx = (targetX - frameX) * (1 - delay);
                double dy = (targetY - frameY) * (1 - delay);
                double distSqr = dx * dx + dy * dy;
                if (distSqr < 5) {
                    opacity *= 0.8;
                } else {
                    opacity = 0.5f;
                }
                window.setOpacity(opacity);
                //window.setVisible(window.getOpacity() >= 0.01);
                int x = (int) Math.round(frameX + dx);
                int y = (int) Math.round(frameY + dy);
                window.setLocation(x, y);
            }
        });
        mouseWatch.setDaemon(true);
        mouseWatch.start();
    }
}
