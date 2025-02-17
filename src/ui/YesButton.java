package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;
public class YesButton {
    private JButton yesButton;
    public YesButton(JFrame window){
        yesButton = new JButton();
        URL yesLocation = getClass().getResource("/resources/picture/yesButton.png");
        if (yesLocation != null) {
            ImageIcon yesIcon = new ImageIcon(yesLocation);
            yesButton.setIcon(yesIcon);
        } else {
            System.err.println("ไม่พบไฟล์: yesButton.png");
            yesButton.setText("Yes");
        }
        yesButton.setBounds(821, 240, 340,320); //เหลือปรับให้เล็กลง
        yesButton.setBackground(null);
        yesButton.setContentAreaFilled(false);
        yesButton.setBorderPainted(false);
        yesButton.setFocusPainted(false);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(window, "You clicked Yes");
            }
        });
    }
    public JButton getYesButton() {
        return yesButton;
    }
    
}