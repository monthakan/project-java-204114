package entity;

import java.awt.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class animeCast extends JPanel {
    private Image character;
    private int x = -200; // ตำแหน่งเริ่มต้น (ซ่อนตัวละครไว้ก่อน)
    private int y = 100;  
    private boolean isMoving = false; 
    private boolean isVisible = false; 
    private final Font orderFont = new Font("Mali", Font.BOLD, 25); // ตั้งค่า Font ของข้อความ

    private String currentOrder = "รอ"; // ข้อความ Order ที่สุ่มมา

    private final String[] characterList = { //array รูปภาพตัวละคร
        "/resources/picture/ลูกค้า01.png",
        "/resources/picture/ลูกค้า02.png",
        "/resources/picture/ลูกค้า03.png",
        "/resources/picture/ลูกค้า04.png",
        "/resources/picture/ลูกค้า05.png",
        "/resources/picture/ลูกค้า06.png",
        "/resources/picture/ลูกค้า07.png",
        "/resources/picture/ลูกค้า08.png",
        "/resources/picture/ลูกค้า09.png",
        "/resources/picture/ลูกค้า10.png",
        "/resources/picture/ลูกค้า11.png",
        "/resources/picture/ลูกค้า12.png",
        "/resources/picture/ลูกค้า13.png",
        "/resources/picture/ลูกค้า14.png",
        "/resources/picture/ลูกค้า15.png",
        "/resources/picture/ลูกค้า16.png",
        "/resources/picture/ลูกค้า17.png",
        "/resources/picture/ลูกค้า18.png",
        "/resources/picture/ลูกค้า19.png",
        "/resources/picture/ลูกค้า20.png",
    };
    private final String[] OrderList = {
        "ขอหมูสับผัดกะเพราหอมๆ เพิ่มความกรุบกรอบด้วยถั่วฝักยาว",
        "เอาเส้นใหญ่ผัดซอสพริกเผา ใส่เส้นเส้นเล็กๆและผักสด",
        "ขอเส้นใหญ่ผัดซอสพริกเผา ใส่เส้นเส้นเล็กๆและผักสด",
        "สั่งเส้นใหญ่ผัดซอสพริกเผา ใส่เส้นเส้นเล็กๆและผักสด",
    };
    private void  loadRandomOrder() {
        Random random = new Random();
        int index = random.nextInt(OrderList.length); // เลือก index แบบสุ่ม
        currentOrder = OrderList[index];
        System.out.println("สุ่มออเดอร์: " + currentOrder); // Debug เช็กค่า
        
    }
    private void loadRandomCharacter() {
        Random random = new Random();
        int index = random.nextInt(characterList.length); // เลือก index แบบสุ่ม
        URL characterLocation = getClass().getResource(characterList[index]);

        if (characterLocation != null) {
            character = new ImageIcon(characterLocation).getImage();
        } else {
            System.err.println("ไม่พบไฟล์: " + characterList[index]);
        }
    }
    public animeCast() {
        setPreferredSize(new Dimension(500, 300)); // กำหนดขนาดของพื้นที่ที่ตัวละครจะวาดอยู่
        loadRandomCharacter();
        loadRandomOrder();

        // ตั้งเวลาให้ตัวละครปรากฏหลังจาก 3 วินาที
        Timer delayTimer = new Timer();
        delayTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                isVisible = true;
                repaint();
                startMoving();
            }
        },900);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (isVisible && character != null) {
            g.drawImage(character, x, y, this);
        }

        // แสดงกล่องข้อความเสมอหาก `currentOrder` มีค่า
        if (currentOrder != null && !currentOrder.isEmpty()) {
            drawOrderBubble(g);
        }
    }

    // ฟังก์ชันเริ่มการเคลื่อนไหวของตัวละคร
    public void startMoving() {
        isMoving = true;
        Timer moveTimer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (x < 95) { // เคลื่อนไหวไปตำแหน่งเป้าหมาย
                    x += 3.5;  
                    repaint();
                } else {
                    moveTimer.cancel();
                    isMoving = false;
                }
            }
        };

        moveTimer.scheduleAtFixedRate(task, 0, 20);
    }

    // ฟังก์ชันรีเซ็ตตำแหน่งของตัวละคร
    public void resetPosition() {
        isVisible = false; // ซ่อนตัวละครก่อนเริ่มใหม่
        repaint();

        Timer delayTimer = new Timer();
        delayTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    x = -200; // รีเซ็ตตำแหน่ง x
                    loadRandomCharacter(); // สุ่มตัวละครใหม่
                    loadRandomOrder(); // สุ่ม Order ใหม่
                    isVisible = true; 
                    repaint();
                    startMoving();
                });
            }
        }, 1000);
    }
    //todo -> ใส่ delry ข้อความ กรอบสวยๆและการกด
    private void drawOrderBubble(Graphics g) {
        g.setFont(orderFont);
        FontMetrics metrics = g.getFontMetrics(orderFont); // ใช้เพื่อหาขนาดของตัวอักษร
        int maxWidth = 590; // ความกว้างสูงสุดของกล่องข้อความ
        int maxHeight = 300; // ความสูงสูงสุดของกล่องข้อความ
    
        // แบ่งข้อความให้เป็นหลายบรรทัดถ้าจำเป็น
        String[] lines = wrapText(currentOrder, metrics, maxWidth);
    
        // คำนวณความกว้างและความสูงของกล่องข้อความ
        int textWidth = 0;
        for (String line : lines) {
            textWidth = Math.max(textWidth, metrics.stringWidth(line)); // คำนวณความกว้างที่มากที่สุด
        }
        textWidth += 40; // เพิ่ม padding ซ้าย-ขวา
    
        int textHeight = (lines.length * metrics.getHeight()) + 60; // ความสูงของกล่องข้อความ + padding ด้านบน-ล่าง
    
        int bubbleX = 95 + 821; // ตำแหน่ง X ของกล่อง
        int bubbleY = 200; // ตำแหน่ง Y ของกล่อง
    
        // วาดกล่องข้อความ
        g.setColor(new Color(255, 240, 218, 255)); // ตั้งค่าสีพื้นหลัง
        g.fillRoundRect(bubbleX, bubbleY, textWidth, textHeight, 15, 15);
        g.setColor(new Color(188, 140, 133)); // ตั้งค่าสีขอบ
        for (int i = 0; i < 3; i++) { // วาดซ้ำ 3 ครั้งเพื่อเพิ่มความหนาของขอบ
            g.drawRoundRect(bubbleX - i, bubbleY - i, textWidth + i * 2, textHeight + i * 2, 20, 20);
        }
    
        // วาดข้อความในแต่ละบรรทัด
        g.setColor(Color.BLACK);
        int yPos = bubbleY + 40; // เริ่มจากตำแหน่งเริ่มต้น
        for (String line : lines) {
            g.drawString(line, bubbleX + 10, yPos); // วาดข้อความในบรรทัด
            yPos += metrics.getHeight(); // ปรับตำแหน่ง Y สำหรับบรรทัดถัดไป
        }
    }
    
    // ฟังก์ชันที่ใช้แบ่งข้อความเป็นหลายบรรทัด
    private String[] wrapText(String text, FontMetrics metrics, int maxWidth) {
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
        List<String> lines = new ArrayList<>();
    
        for (String word : words) {
            // ตรวจสอบว่าข้อความในบรรทัดปัจจุบันเกินความกว้างที่กำหนด
            if (metrics.stringWidth(currentLine.toString() + word) > maxWidth) {
                lines.add(currentLine.toString()); // ถ้าข้อความเกินไป ให้เพิ่มบรรทัดใหม่
                currentLine = new StringBuilder(word); // เริ่มบรรทัดใหม่
            } else {
                if (currentLine.length() > 0) {
                    currentLine.append(" "); // ถ้ามีคำอยู่แล้ว ให้เพิ่มช่องว่าง
                }
                currentLine.append(word); // เพิ่มคำใหม่
            }
        }
        lines.add(currentLine.toString()); // เพิ่มบรรทัดสุดท้าย
    
        return lines.toArray(new String[0]); // คืนค่าอาเรย์ของบรรทัดที่แยกแล้ว
    }
}