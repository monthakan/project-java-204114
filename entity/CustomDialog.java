package entity;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
public class CustomDialog extends JPanel {
    private String message = ""; // ข้อความที่จะแสดง
    private Font orderFont = new Font("Book Antiqua", Font.BOLD, 30);
    private boolean isVisible = true;
    private Timer timer;
    private int currentIndex = 0;
    private JLabel timerText = new JLabel(); // Define and initialize timerText

    private String savedMessage = "";  // จำข้อความเดิมก่อนซ่อน


    public CustomDialog() {
        this.setOpaque(false); // ให้ JPanel โปร่งใส
        
        this.add(timerText); // Add the timerText to the panel
        timerText.setFont(new Font("Book Antiqua", Font.BOLD, 26));
        timerText.setBounds(10, 10, 150, 30); // Set timer position
        timerText.setForeground(Color.BLACK); // Set color
        timerText.setOpaque(false); // Transparent background
        
        // ให้ timerText แสดงเสมอ
        timerText.setVisible(true); 
    }

    public void setMessage(String message) {
        this.message = message;
        repaint(); // สั่งให้วาดใหม่
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isVisible) { 
            g.setFont(orderFont);
            drawOrderBubble(g);
        }
    }

    private boolean isPaused = false; // เก็บสถานะการ Pause

    public void showDialog(boolean show) {
        if (show) {
            // ทำงานตอน resume
            if (!isPaused) {
                // ถ้าไม่อยู่ในสถานะ pause ให้แสดงข้อความ
                if (savedMessage != null) {
                    message = savedMessage;  // รีสตาร์ทข้อความเดิม
                } else {
                    message = ""; // ถ้าข้อความยังไม่ได้กำหนด
                }
                currentIndex = message.length(); // ให้แสดงครบเลย ไม่ต้องพิมพ์ใหม่
                System.out.println("เปิดข้อความแล้ว: " + message);
            } else {
                // ถ้าเป็นการ resume หลังจาก pause
                System.out.println("Resume, แต่ไม่มีข้อความ");
            }
        } else {
            // ซ่อนข้อความเมื่อ Pause
            savedMessage = message;  // เก็บข้อความไว้ก่อนซ่อน
            message = "";  // ล้างข้อความชั่วคราว
            currentIndex = 0;
            isPaused = true; // ตั้งค่าการ Pause
            System.out.println("ล้างข้อความแล้ว: " + savedMessage);
        }

        isVisible = show;
        this.setVisible(show);
        this.revalidate();  // เพิ่มการ revalidate เพื่อตรวจสอบ UI ใหม่
        this.repaint();
    }

    
    


    public void startTypingEffect(String newMessage) {
        if (newMessage == null || newMessage.isEmpty()) {
            System.out.println("ข้อความว่าง ไม่สามารถพิมพ์ได้");
            return;
        }
        this.message = newMessage;
        currentIndex = 0;
    
        if (timer != null) {
            timer.cancel(); // หยุด Timer ก่อนที่จะเริ่มใหม่
        }
    
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {  // 🔹 อัปเดต UI ใน EDT
                    if (currentIndex < message.length()) {
                        currentIndex++;
                        repaint();  // Update only the message display
                    } else {
                        cancel(); // หยุด Timer เมื่อข้อความแสดงครบ
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(task, 0, 100); // Update every 100ms
    }

    private void drawOrderBubble(Graphics g) {
        FontMetrics metrics = g.getFontMetrics(orderFont);
        int maxWidth = 600; // กำหนดความกว้างของกรอบ
        int textHeight = 300; // กำหนดความสูงของกรอบ
    
        int padding = 20; // ระยะห่างของข้อความจากขอบกล่อง
        int textMaxWidth = maxWidth - (padding * 2); // กำหนดพื้นที่ข้อความที่เหลืออยู่
    
        String visibleText = message.substring(0, Math.min(currentIndex, message.length())); // แสดงเฉพาะตัวอักษรที่พิมพ์แล้ว
        String[] lines = wrapText(visibleText, metrics, textMaxWidth);
    
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
        }
    }

    private String[] wrapText(String text, FontMetrics metrics, int maxWidth) {
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
        List<String> lines = new ArrayList<>();
    
        for (String word : words) {
            if (metrics.stringWidth(currentLine.toString() + word) > maxWidth + 20) { //
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
