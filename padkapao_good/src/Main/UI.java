package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class UI {
    
    GameManager gm;
    boolean start = false;
    
    JFrame window;
    public JTextArea messageText;
    public JPanel bgPanel[] = new JPanel[10];
    public JLabel bgLabel[] = new JLabel[10];
    
    public UI(GameManager gm){
        
        this.gm = gm;
        
        createMainField();
        
        generateMenu();
        
        window.setVisible(true);
    }
    
    public void createMainField(){
        
        window = new JFrame();
        window.setSize(1920,1080); // resolution
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
        
        /*//text box
        messageText = new JTextArea("I need Kapao sap sap");
        messageText.setBounds(100,100,700,150); // x, y, width, height
        messageText.setBackground(Color.black);
        messageText.setForeground(Color.white);
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setFont(new Font("Book Antiqua",Font.PLAIN,26));
        window.add(messageText); */
    }
    
    public void createBackground(int bgNum, String bgFileName){
    
        bgPanel[bgNum] = new JPanel();
        bgPanel[bgNum].setBounds(0,0,1920,1080); // x, y, width, height
        bgPanel[bgNum].setBackground(Color.gray);
        bgPanel[bgNum].setLayout(null);
        //window.add(bgPanel[bgNum]);
        

        bgLabel[bgNum] = new JLabel();
        bgLabel[bgNum].setBounds(0, 0, 1920,1080);
        
        ImageIcon bgIcon = new ImageIcon(getClass().getClassLoader().getResource(bgFileName));
        bgLabel[bgNum].setIcon(bgIcon);
    }

    public void createObject(int bgNum, int objx, int objy, int objWidth, int objHeight, String objFileName, String Command){
    	
    	// create object
        JLabel objectLabel = new JLabel();
        objectLabel.setBounds(objx, objy, objWidth, objHeight); // x, y, width, height
        
        ImageIcon objectIcon = new ImageIcon(getClass().getClassLoader().getResource(objFileName));
        objectLabel.setIcon(objectIcon);
        
        JButton ActionButton = new JButton();
        objectLabel.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				
				if(SwingUtilities.isLeftMouseButton(e) && !Command.equals("stove")) {
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
        // delete
    	 window.remove(bgPanel[1]);
        
        // create new background
        createBackground(1,"menubg1920x1080.png");

        //add ingredients
        createObject(1, 200, 300, 300, 300, "kapao300x300.png", "addkapao");
        createObject(1, 550, 300, 300, 300, "micedpork 300x300.png", "addmpork");
        
        createArrowButton(1, 1620, 400, 300, 300, "rightArrow300x300.png", "goScreen2");
        System.out.println(Command);
        switch(Command) {
        case "addkapao" : createObject(1, 200, 700, 300, 300, "kapao300x300.png", "addkapao"); break;
        case "addmpork" : createObject(1, 200, 700, 300, 300, "micedpork 300x300.png", "stove"); break;
        }

        bgPanel[1].add(bgLabel[1]);
        window.add(bgPanel[1]);

        // refresh
        window.revalidate();
        window.repaint();
    }

    
    public void createArrowButton(int bgNum, int x, int y, int Width, int Height, String arrowFileName, String Command) {
    	
    	ImageIcon arrowIcon = new ImageIcon(getClass().getClassLoader().getResource(arrowFileName));
    	
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
    	
    }

public void createStartButton(int bgNum, int x, int y, int Width, int Height, String arrowFileName, String Command) {
    	
    	ImageIcon startIcon = new ImageIcon(getClass().getClassLoader().getResource(arrowFileName));
    	
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

			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				
				if(SwingUtilities.isLeftMouseButton(e)) {
					generateScene();
					System.out.println("Start!");
				}
			}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
        });
    	
    	bgPanel[bgNum].add(startButton);
    }

	public void createStoveTimer() {
		
	}

    public void generateMenu() {
    	
    	//menu screen
    	createBackground(0,"menubg1920x1080.png");
    	createStartButton(0, 800, 400, 300, 100, "playButton300x100.png", "start");
    	
    	bgPanel[0].add(bgLabel[0]);
    	window.add(bgPanel[0]);
    }
    public void generateScene() {
    	
    	//screen1
    	createBackground(1,"menubg1920x1080.png");
    	
    	createObject(1, 200, 300, 300, 300, "kapao300x300.png", "addkapao");
    	createObject(1, 550, 300, 300, 300, "micedpork 300x300.png", "addmpork");
    	
    	createObject(1, 200, 700, 300, 300,	 "stove300x300.png", "stove");
    	createArrowButton(1, 1620, 400, 300, 300, "rightArrow300x300.png", "goScreen2");
    	
    	bgPanel[1].add(bgLabel[1]);
    	window.add(bgPanel[1]);
    	
    	//screen2
    	createBackground(2,"package1920x1080.png");
    	
    	createArrowButton(2, 0, 400, 300, 300, "leftArrow300x300.png", "goScreen1");
    	
    	bgPanel[2].add(bgLabel[2]);
    	window.add(bgPanel[2]);
    	bgPanel[2].setVisible(false);
    }
}
