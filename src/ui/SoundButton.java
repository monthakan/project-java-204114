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




/*note -> มีปุ่มเปิดและปิด */
public class SoundButton {
    private JButton soundButton;
    private boolean isClicked = false;
    private boolean isMuted = false;
    private Clip clip;
    public SoundButton(JFrame window){
        soundButton = new JButton(); //สร้างปุ่มเสียง
        
        URL soundOnLocation = getClass().getResource("/resources/picture/soundOn.png");
        URL soundOffLocation = getClass().getResource("/resources/picture/soundOff.png");

        if (soundOnLocation != null && soundOffLocation != null) {
            ImageIcon soundOn = new ImageIcon(soundOnLocation);
            ImageIcon soundOff = new ImageIcon(soundOffLocation);
            soundButton.setIcon(soundOn); //กำหนดรูปภาพให้กับปุ่มเสียง
            soundButton.setPressedIcon(soundOff); //กำหนดรูปภาพเมื่อเมาส์ไปชี้ที่ปุ่มเสียง

            soundButton.setBounds(0, 0, 120, 130); //กำหนดขนาดและตำแหน่งของปุ่มเสียง
            soundButton.setBackground(null);
            soundButton.setContentAreaFilled(false);
            soundButton.setBorderPainted(false);
            soundButton.setFocusPainted(false);
            try {
                URL soundFile = getClass().getResource("/resources/audio/backgroundSound.wav");
                if (soundFile == null) {
                    System.err.println("ไม่พบไฟล์เสียง:" );
                } else {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                    clip = AudioSystem.getClip();
                    clip.open(ais);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                    System.out.println("เล่นเสียงเรียบร้อย!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            soundButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(clip != null){
                        if(isMuted){
                            clip.start();
                            soundButton.setIcon(soundOn);
                        }else{
                            clip.stop();
                            soundButton.setIcon(soundOff);
                        }
                        isMuted = !isMuted; //สถานะเป็นเสียงเปิดหรือปิด
                    }
                }
            });
            window.add(soundButton); //เพิ่มปุ่มเสียงลงในหน้าต่าง

        } else {
            System.err.println("ไม่พบไฟล์: soundOn.png and soundOff.png");
            soundButton.setText("SoundOn and SoundOff");
        }
    }
    public JButton getSoundButton() { //เมื่อเรียกใช้งานจะส่งค่ากลับไปที่ soundButton
        return soundButton;
    }
}