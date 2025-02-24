package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class SoundButton {
    private static SoundButton instance; // อินสแตนซ์เดียวของปุ่ม
    private static Clip clip; // ใช้เสียงร่วมกัน
    private JButton soundButton;
    private boolean isMuted = false;

    // คอนสตรักเตอร์ส่วนตัว เพื่อป้องกันการสร้างอินสแตนซ์ใหม่
    private SoundButton(JFrame window) {
        soundButton = new JButton(); 
        
        URL soundOnLocation = getClass().getResource("/resources/picture/soundOn.png");
        URL soundOffLocation = getClass().getResource("/resources/picture/soundOff.png");

        if (soundOnLocation != null && soundOffLocation != null) {
            ImageIcon soundOn = new ImageIcon(soundOnLocation);
            ImageIcon soundOff = new ImageIcon(soundOffLocation);
            soundButton.setIcon(soundOn);
            soundButton.setPressedIcon(soundOff);

            soundButton.setBounds(10, 0, 120, 120); 
            soundButton.setContentAreaFilled(false);
            soundButton.setBorderPainted(false);
            soundButton.setFocusPainted(false);
            
            // โหลดเสียงเพียงครั้งเดียว
            if (clip == null) {
                try {
                    URL soundFile = getClass().getResource("/resources/audio/backgroundSound.wav");
                    if (soundFile != null) {
                        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                        clip = AudioSystem.getClip();
                        clip.open(ais);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                        System.out.println("เล่นเสียงเรียบร้อย!");
                    } else {
                        System.err.println("ไม่พบไฟล์เสียง");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            soundButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toggleSound();
                }
            });

            window.add(soundButton);
        } else {
            System.err.println("ไม่พบไฟล์ภาพ soundOn.png หรือ soundOff.png");
            soundButton.setText("Sound On/Off");
        }
    }

    // เมธอดสำหรับเรียกใช้อินสแตนซ์เดียวของ SoundButton
    public static SoundButton getInstance(JFrame window) {
        if (instance == null) {
            instance = new SoundButton(window);
        }
        return instance;
    }

    public void toggleSound() {
        if (clip != null) {
            if (isMuted) {
                clip.start();
                soundButton.setIcon(new ImageIcon(getClass().getResource("/resources/picture/soundOn.png")));
            } else {
                clip.stop();
                soundButton.setIcon(new ImageIcon(getClass().getResource("/resources/picture/soundOff.png")));
            }
            isMuted = !isMuted;
        }
    }

    public boolean isMuted() {
        return isMuted;
    }

    public JButton getSoundButton() {
        return soundButton;
    }
}
