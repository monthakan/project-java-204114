package ui;

import Main.GameManager;
import entity.CustomDialog;
import entity.OrderMessage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;

public class WhatButton {
    private JButton whatButton;
    private JButton yesButton;
    private CustomDialog dialog;
    private OrderMessage orderMessage;
    private String whatMessage;
    private String firstMessage;
    private String hintMessage;

    public WhatButton(JFrame parentFrame, GameManager gm) {
        dialog = new CustomDialog();
        dialog.setBounds(0, -120, parentFrame.getWidth(), parentFrame.getHeight());
        parentFrame.add(dialog);
        dialog.setVisible(false);

        orderMessage = OrderMessage.getInstance();
        whatMessage = orderMessage.getWhat();
        firstMessage = orderMessage.getMessage();

        whatButton = new JButton();
        URL whatLocation = getClass().getResource("/resources/picture/whatButton.png");
        if (whatLocation != null) {
            ImageIcon whatIcon = new ImageIcon(whatLocation);
            int originalWidth = whatIcon.getIconWidth();
            int originalHeight = whatIcon.getIconHeight();
            double newWidth = originalWidth / 1.5;
            double newHeight = originalHeight / 1.5;
            Image scaledImage = whatIcon.getImage().getScaledInstance((int) newWidth, (int) newHeight, Image.SCALE_SMOOTH);
            whatButton.setIcon(new ImageIcon(scaledImage));
        } else {
            System.err.println("ไม่พบไฟล์: whatButton.png");
            whatButton.setText("what");
        }
        
        whatButton.setBounds(840, 450, 220, 220);
        whatButton.setBackground(null);
        whatButton.setContentAreaFilled(false);
        whatButton.setFocusPainted(false);
        whatButton.setBorderPainted(false);
        whatButton.setVisible(false); // Initially hidden
        parentFrame.add(whatButton);

        // Create yesButton
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

        yesButton.setBounds(1090, 450, 220, 119);
        yesButton.setBackground(null);
        yesButton.setContentAreaFilled(false);
        yesButton.setFocusPainted(false);
        yesButton.setBorderPainted(false);
       // yesButton.setVisible(false);


        // ซ่อนปุ่มก่อน
        yesButton.setVisible(false);
        parentFrame.add(yesButton);

        // Setup action for whatButton
        whatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (whatMessage == null || whatMessage.isEmpty()) {
                    System.out.println("ข้อความว่าง");
                } else {
                    dialog.setMessage(whatMessage);
                    dialog.startTypingEffect(whatMessage);
                    dialog.setVisible(true);
                    System.out.println("แสดงข้อความ: " + whatMessage);
                }
            }
        });

        yesButton.setActionCommand("goSelectIngScreen");

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false); // ซ่อน dialog
                whatButton.setVisible(false); // ซ่อน whatButton
                yesButton.setVisible(false); // ซ่อน yesButton
                System.out.println("ปิดข้อความและปุ่ม");

                gm.aHandler.actionPerformed(e); // เรียก actionPerformed ของ GameManager เพื่อไปหน้าถัดไป
            }
        });
        
        // Show hintButton and yesButton after a delay
        Timer showButtonsTimer = new Timer(1900, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                whatButton.setVisible(true);
                yesButton.setVisible(true);
                System.out.println("แสดงปุ่ม hintButton และ yesButton");
            }
        });
        showButtonsTimer.setRepeats(false);
        showButtonsTimer.start();

        // Show the first message in CustomDialog after a delay
        Timer messageTimer = new Timer(1800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.startTypingEffect(whatMessage);
                showCustomDialog(firstMessage); // Show the first message
            }
        });
        messageTimer.setRepeats(false);
        messageTimer.start();
    }

    public void showCustomDialog(String message) {
        dialog.setMessage(message); // Set the message for the dialog
        dialog.setVisible(true); // Show dialog
        dialog.revalidate();
        dialog.repaint();
        System.out.println("แสดงข้อความเริ่มต้น: " + message);
    }

    public JButton getWhatButton() {
        return whatButton;
    }

    public JButton getYesButton() {
        return yesButton;
    }
}


