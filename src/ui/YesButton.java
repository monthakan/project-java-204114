package ui;

import Main.GameManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;

public class YesButton {
    private JButton yesButton;

    public YesButton(JFrame parentFrame, GameManager gm) { // รับ GameManager เป็นพารามิเตอร์
        yesButton = new JButton();
        URL yesLocation = getClass().getResource("/resources/picture/yesButton.png");
        if (yesLocation != null) {
            ImageIcon yesIcon = new ImageIcon(yesLocation);
            int originalWidth = yesIcon.getIconWidth();
            int originalHeight = yesIcon.getIconHeight();
            double newWidth = originalWidth / 1.5;
            double newHeight = originalHeight / 1.5;
            Image scaledImage = yesIcon.getImage().getScaledInstance((int) newWidth, (int) newHeight, Image.SCALE_SMOOTH);
            yesButton.setIcon(new ImageIcon(scaledImage));
        } else {
            System.err.println("ไม่พบไฟล์: yesButton.png");
            yesButton.setText("Yes");
        }

        // ตั้งค่าตำแหน่งและขนาดปุ่ม
        yesButton.setBounds(1090, 450, 220, 119);
        yesButton.setBackground(null);
        yesButton.setContentAreaFilled(false);
        yesButton.setFocusPainted(false);
        yesButton.setBorderPainted(false);

        // ซ่อนปุ่มก่อน
        yesButton.setVisible(false);
        parentFrame.add(yesButton);

        // หน่วงเวลาก่อนแสดงปุ่ม (2 วินาที)
        Timer buttonTimer = new Timer(1900, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    yesButton.setVisible(true);
                    System.out.println("แสดงปุ่ม");
                });
            }
        });
        buttonTimer.setRepeats(false);
        buttonTimer.start();

        // ตั้ง ActionCommand สำหรับปุ่ม
        yesButton.setActionCommand("goSelectIngScreen");

        // เมื่อคลิกปุ่ม yesButton จะเรียกใช้ gm.aHandler (ตัวจัดการเหตุการณ์)
        yesButton.addActionListener(gm.aHandler); // เชื่อมต่อกับตัวจัดการเหตุการณ์
        

        // อัปเดต GUI
        parentFrame.repaint();
    }

    public JButton getButton() {
        return yesButton;
    }
}
