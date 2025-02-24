package animation;

import entity.CustomDialog;
import entity.animeCast;
import java.awt.*; // เปลี่ยนไปใช้ CustomDialog
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*; // เพิ่ม import
public class enterthescene extends JPanel {
    private int x = -1000; // ตำแหน่งเริ่มต้น (ซ่อนตัวละครไว้ก่อน)
    private animeCast anime;
    private CustomDialog dialog;

    public enterthescene(animeCast anime, CustomDialog dialog) {
        this.anime = anime;
        this.dialog = dialog;
        setPreferredSize(new Dimension(500, 300)); // กำหนดขนาดของพื้นที่ที่ตัวละครจะวาดอยู่
    }
    
    public void startMoving() {
        Timer moveTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (x < 60) {
                    x += 3.5; // อัปเดตตำแหน่งตัวละคร
                    anime.updatePosition(x, 100); // อัปเดตตำแหน่งตัวละคร
                    //dialog.updatePosition(x, 100); // อัปเดตตำแหน่งกล่องข้อความ
                    repaint(); // วาดใหม่ทุกครั้ง
                } else {
                    moveTimer.cancel(); // หยุดเมื่อถึงจุดสุดท้าย
                }
            }
        };
        moveTimer.scheduleAtFixedRate(task, 0, 10); // อัปเดตทุก 10 ms
    }
    

    public void resetPosition() {
        x = -1000;
        anime.loadRandomCharacter();
        anime.setVisible(true); // ทำให้ตัวละครปรากฏ
        dialog.setVisible(false); // ทำให้กล่องข้อความแสดง

        Timer startMovingTimer = new Timer();
        startMovingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.setVisible(true);
                startMoving();
            }
        }, 300);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // วาด dialog
        if (dialog != null) {
            dialog.paint(g); // วาดกล่องข้อความ
        }
    }
}
