package Main;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class UI {

    GameManager gm;
    boolean start = false;

    JFrame window;
    public JPanel bgPanel[] = new JPanel[10];
    public JLabel bgLabel[] = new JLabel[10];

    public UI(GameManager gm) {
        this.gm = gm;
        createMainField();
        generateMenu();
        window.setVisible(true);
    }

    public void createMainField() { //สร้างหน้าต่างเกม
        window = new JFrame();
        window.setSize(1920, 1080);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
    }

    public void createBackground(int bgNum, String bgFileName) {
        bgPanel[bgNum] = new JPanel();
        bgPanel[bgNum].setBounds(0, 0, 1920, 1080);
        bgPanel[bgNum].setLayout(null);

        bgLabel[bgNum] = new JLabel();
        bgLabel[bgNum].setBounds(0, 0, 1920, 1080);

        URL location = getClass().getResource("/ui/" + bgFileName);
        if (location != null) {
            ImageIcon icon = new ImageIcon(location);
            bgLabel[bgNum].setIcon(icon);
        } else {
            System.err.println("ไม่พบไฟล์: " + bgFileName);
        }

        bgPanel[bgNum].add(bgLabel[bgNum]);
        window.add(bgPanel[bgNum]);
    }

    public void createObject(int bgNum, int objx, int objy, int objWidth, int objHeight, String objFileName, String Command) {
        JLabel objectLabel = new JLabel();
        objectLabel.setBounds(objx, objy, objWidth, objHeight);

        URL objLocation = getClass().getResource("/ui/"  + objFileName);
        if (objLocation != null) {
            ImageIcon objectIcon = new ImageIcon(objLocation);
            objectLabel.setIcon(objectIcon);
        } else {
            System.err.println("ไม่พบไฟล์: " + objFileName);
        }

        objectLabel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && !Command.equals("stove")) {
                    System.out.println("Left Click!");
                    changeStove(Command);
                }
            }
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });

        bgPanel[bgNum].add(objectLabel);
    }

    public void changeStove(String Command) {
        window.remove(bgPanel[1]);
        createBackground(1, "menubg1920x1080.png");

        createObject(1, 200, 300, 300, 300, "kapao300x300.png", "addkapao");
        createObject(1, 550, 300, 300, 300, "micedpork_300x300.png", "addmpork");

        createArrowButton(1, 1620, 400, 300, 300, "rightArrow300x300.png", "goScreen2");

        switch (Command) {
            case "addkapao": createObject(1, 200, 700, 300, 300, "kapao300x300.png", "addkapao"); break;
            case "addmpork": createObject(1, 200, 700, 300, 300, "micedpork_300x300.png", "stove"); break;
        }

        bgPanel[1].add(bgLabel[1]);
        window.add(bgPanel[1]);

        window.revalidate();
        window.repaint();
    }

    public void createArrowButton(int bgNum, int x, int y, int Width, int Height, String arrowFileName, String Command) {
        URL arrowLocation = getClass().getResource("/ui/" + arrowFileName);
        if (arrowLocation != null) {
            ImageIcon arrowIcon = new ImageIcon(arrowLocation);
            JButton arrowButton = new JButton();
            arrowButton.setBounds(x, y, Width, Height);
            arrowButton.setBackground(null);
            arrowButton.setContentAreaFilled(false);
            arrowButton.setFocusPainted(false);
            arrowButton.setIcon(arrowIcon);
            arrowButton.addActionListener(gm.aHandler);
            arrowButton.setActionCommand(Command);
            arrowButton.setBorderPainted(false);
            bgPanel[bgNum].add(arrowButton);
        } else {
            System.err.println("ไม่พบไฟล์: " + arrowFileName);
        }
    }

    public void createStartButton(int bgNum, int x, int y, int Width, int Height, String arrowFileName, String Command) {
        URL startLocation = getClass().getResource("/ui/" + arrowFileName);
        if (startLocation != null) {
            ImageIcon startIcon = new ImageIcon(startLocation);
            JButton startButton = new JButton();
            startButton.setBounds(x, y, Width, Height);
            startButton.setBackground(null);
            startButton.setContentAreaFilled(false);
            startButton.setFocusPainted(false);
            startButton.setIcon(startIcon);
            startButton.addActionListener(gm.aHandler);
            startButton.setActionCommand(Command);
            startButton.setBorderPainted(false);

            startButton.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                }
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if(SwingUtilities.isLeftMouseButton(e)) {
                            // change icon when clicked
                            ImageIcon clickedIcon = new ImageIcon(getClass().getResource("/ui/playButtonClicked.png"));
                            startButton.setIcon(clickedIcon);
                            generateScene();
                            System.out.println("Start!");
                        }
                    }
                    
                }
                public void mouseReleased(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {
                    // change icon when mouse entered
                    ImageIcon hoverIcon = new ImageIcon(getClass().getResource("/ui/playButtonClicked.png"));
                    Image image = hoverIcon.getImage(); // transform it
                    ////Image scaledImage = image.getScaledInstance(startButton.getWidth(), startButton.getHeight(), Image.SCALE_SMOOTH); // ปรับขนาดภาพ
                    startButton.setIcon(hoverIcon);
                }
                public void mouseExited(MouseEvent e) {
                    // change icon when mouse exited
                    if(!startButton.getBounds().contains(e.getPoint())){
                        startButton.setIcon(startIcon); // กลับไปเป็น icon ปกติ
                    }
                }
            });

            bgPanel[bgNum].add(startButton); //add button to panel
        } else {
            System.err.println("ไม่พบไฟล์: " + arrowFileName);
        }
    }

    public void generateMenu() {
        createBackground(0, "menubg1920x1080.png");
        createStartButton(0, 750, 280, 500, 500, "playButton.png", "start");
        //createStartButton(0, 750, 280, 500, 500, "playButtonClicked.png", "start");

        bgPanel[0].add(bgLabel[0]);
        window.add(bgPanel[0]);
    }

    public void generateScene() {
        createBackground(1, "menubg1920x1080.png");
        createObject(1, 200, 300, 300, 300, "kapao300x300.png", "addkapao");
        createObject(1, 550, 300, 300, 300, "micedpork_300x300.png", "addmpork");
        createObject(1, 200, 700, 300, 300, "stove300x300.png", "stove");
        createArrowButton(1, 1620, 400, 300, 300, "rightArrow300x300.png", "goScreen2");

        bgPanel[1].add(bgLabel[1]);
        window.add(bgPanel[1]);

        createBackground(2, "package1920x1080.png");
        createArrowButton(2, 0, 400, 300, 300, "leftArrow300x300.png", "goScreen1");

        bgPanel[2].add(bgLabel[2]);
        window.add(bgPanel[2]);
        bgPanel[2].setVisible(false);
    }
}
