package entity;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class CustomDialog extends JPanel {
    private String message = ""; // ข้อความที่จะแสดง
    private Font orderFont = new Font("mali", Font.BOLD, 30);
    private boolean isVisible = true;
    private Timer timer;
    private int currentIndex = 0;

    public CustomDialog() {
        this.setOpaque(false); // ให้ JPanel โปร่งใส
    }

    public void setMessage(String message) {
        this.message = message;
        repaint(); // สั่งให้วาดใหม่
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isVisible) {
            // วาดคำสั่ง
            g.setFont(orderFont);
            drawOrderBubble(g);
        }
    }
    public void startTypingEffect(String newMessage) {
        if (newMessage == null || newMessage.isEmpty()) {
            System.out.println("ข้อความว่าง ไม่สามารถพิมพ์ได้");
            return;
        }
        this.message = newMessage;  // กำหนดข้อความใหม่
        currentIndex = 0;  // รีเซ็ตตำแหน่งพิมพ์
        repaint();  // รีเฟรชหน้าจอ
    
        if (timer != null) {
            timer.cancel();
        }
    
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                currentIndex++;
                if (currentIndex > message.length()) {
                    timer.cancel();  // หยุดเมื่อพิมพ์ครบ
                }
                repaint();  // วาดใหม่
            }
        };
        timer.scheduleAtFixedRate(task, 0, 100);
    }
    
    
    private void drawOrderBubble(Graphics g) {
        FontMetrics metrics = g.getFontMetrics(orderFont);
        int maxWidth = 600; // กำหนดความกว้างของกรอบ
        int textHeight = 300; // กำหนดความสูงของกรอบ
    
        int padding = 20; // ระยะห่างของข้อความจากขอบกล่อง
        int textMaxWidth = maxWidth - (padding * 2); // กำหนดพื้นที่ข้อความที่เหลืออยู่
    
        String visibleText = message.substring(0, Math.min(currentIndex, message.length())); // แสดงเฉพาะตัวอักษรที่พิมพ์แล้ว
        String[] lines = wrapText(visibleText, metrics, textMaxWidth); // ✅ ใช้ visibleText แทน message
    
        int bubbleX = 780;
        int bubbleY = 255;
    
        // วาดพื้นหลังของกล่องข้อความ
        g.setColor(new Color(255, 240, 218, 255));
        g.fillRoundRect(bubbleX, bubbleY, maxWidth, textHeight, 15, 15);
    
        g.setColor(new Color(188, 140, 133));
        for (int i = 0; i < 3; i++) {
            g.drawRoundRect(bubbleX - i, bubbleY - i, maxWidth + i * 2, textHeight + i * 2, 20, 20);
        }
    
        // วาดข้อความให้อยู่ตรงกลางของกรอบ โดยมีระยะขอบ (padding)
        g.setFont(orderFont);
        g.setColor(Color.BLACK);
        
        int yPos = bubbleY + padding + metrics.getAscent();
        int maxLines = (textHeight - (padding * 2)) / metrics.getHeight(); // จำนวนบรรทัดที่พอดี
    
        for (int i = 0; i < Math.min(lines.length, maxLines); i++) {
            g.drawString(lines[i], bubbleX + padding, yPos);
            yPos += metrics.getHeight();
           // System.out.println("พิมพ์บรรทัดที่ " + i);
        }
    }
    

    private String[] wrapText(String text, FontMetrics metrics, int maxWidth) {
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
        List<String> lines = new ArrayList<>();
    
        for (String word : words) {
            if (metrics.stringWidth(currentLine.toString() + word) > maxWidth + 20 ) { //
                lines.add(currentLine.toString().trim()); // เก็บบรรทัดที่เต็มแล้ว
                currentLine = new StringBuilder(word); // เริ่มบรรทัดใหม่
            } else {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            }
        }
        lines.add(currentLine.toString().trim()); // เพิ่มบรรทัดสุดท้าย
        return lines.toArray(new String[0]);
    }
    
}