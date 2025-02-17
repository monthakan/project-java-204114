package Main;

import entity.animeCast;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import ui.ExitButton;
import ui.SoundButton;
import ui.YesButton;


public class UI {

    GameManager gm;

    JFrame window;
    public JTextArea messageText;
    public JPanel bgPanel[] = new JPanel[5];
    public JLabel bgLabel[] = new JLabel[5];

    String stove = "";
    boolean start = false;
    boolean isKapao = false;
    boolean voice;
    boolean onStove = false;
    private JTextArea timerText;
    private int remainingTime;
    private boolean pause = false;

    public UI(GameManager gm) {
        this.gm = gm;
        
        createMainField();
        generateTitle();
        window.setVisible(true);
    }

    public void createMainField() { //สร้างหน้าต่างเกม
        window = new JFrame("Padkapao Good");
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

        URL location = getClass().getResource("/resources/picture/" + bgFileName);
        if (location != null) {
            ImageIcon icon = new ImageIcon(location);
            bgLabel[bgNum].setIcon(icon);
        } else {
            System.err.println("ไม่พบไฟล์: " + bgFileName);
        }

        // bgPanel[bgNum].add(bgLabel[bgNum]); 
        // window.add(bgPanel[bgNum]);
    }

    public void createObject(int bgNum, int objx, int objy, int objWidth, int objHeight, String objFileName, String Command){
    	
    	// create object
        JLabel objectLabel = new JLabel();
        
        // object setting
        objectLabel.setBounds(objx, objy, objWidth, objHeight); // x, y, width, height
        URL objLocation = getClass().getResource("/resources/picture/" + objFileName);
        if (objLocation != null) {
            ImageIcon objectIcon = new ImageIcon(objLocation);
        objectLabel.setIcon(objectIcon);
        } else {
            System.err.println("ไม่พบไฟล์: " + objFileName);
        }
        
        
        // object actions
        objectLabel.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {}
			// left click
			public void mousePressed(MouseEvent e) {
				
				if(SwingUtilities.isLeftMouseButton(e) && !Command.equals("stove")) {
					
					System.out.println("Left Click!");
					// clear kapao with rice and add kapao with rice in package
					if (Command.equals("insertKapao")) {
						
						window.remove(bgPanel[3]);
						generateStoveScene();
						
						// refresh
				        window.revalidate();
				        window.repaint();
						
						packKapao(Command);
						
					// pack kapao
					}else if (Command.equals("ready2pack")){ 
						
						System.out.println("PACK ENTER");
						packKapao(Command);
						
					// change stove image	
					} else {
						
						changeStove(Command);
					}
				}
			}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
        });
        
        //add panel
        bgPanel[bgNum].add(objectLabel);
    }
    public void createArrowButton(int bgNum, int x, int y, int Width, int Height, String arrowFileName, String Command) {
    	
    	//add arrow image
        URL arrowLocation = getClass().getResource("/resources/picture/" + arrowFileName);
        if (arrowLocation == null) {
            System.err.println("ไม่พบไฟล์: " + arrowFileName);
        } else{
            ImageIcon arrowIcon = new ImageIcon(arrowLocation);
            JButton arrowButton = new JButton();
            arrowButton.setIcon(arrowIcon);
            
            //arrow setting
            arrowButton.setBounds(x, y, Width, Height);
            arrowButton.setBackground(null);
            arrowButton.setContentAreaFilled(false);
            arrowButton.setFocusPainted(false);
            arrowButton.addActionListener(gm.aHandler);
            arrowButton.setActionCommand(Command);
            arrowButton.setBorderPainted(false);
            
            //add panel
            bgPanel[bgNum].add(arrowButton);
        }
    	
    }

    public void createStartButton(int bgNum, int x, int y, int Width, int Height, String arrowFileName, String Command) {
        URL startLocation = getClass().getResource("/resources/picture/" + arrowFileName);
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
                            ImageIcon clickedIcon = new ImageIcon(getClass().getResource("/resources/picture/playButtonClicked.png"));
                            startButton.setIcon(clickedIcon);
                            generateDialogueScene();
                            generateSelectIngScene();
                            generateStoveScene();
                            generatePackageScene();
                            System.out.println("Start!");
                        }
                    }
                    
                }
                public void mouseReleased(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {
                    // change icon when mouse entered
                    ImageIcon hoverIcon = new ImageIcon(getClass().getResource("/resources/picture/playButtonClicked.png"));
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
    public void generateTitle(){ //หน้าแรกของเกม pageone

        // สร้างหน้าจอเมนู
        bgPanel[0] = new JPanel();
        bgPanel[0].setBounds(0, 0, 1920, 1080);
        bgPanel[0].setLayout(null);

        createBackground(0, "menubg1920x1080.png");
        //createExitButton(0, 1800, 0, 120, 130, "ExitButton.png", "exit");
        // เรียกใช้งาน class ExitButton และสร้างปุ่ม Exit
        ExitButton exit = new ExitButton(window); // สร้างปุ่ม Exit
        bgPanel[0].add(exit.getExitButton()); // เพิ่มปุ่ม Exit ลงในหน้าจอ

        // เรียกใช้งาน class SoundButton
        SoundButton sound = new SoundButton(window); // สร้างปุ่มเสียง
        bgPanel[0].add(sound.getSoundButton()); // เพิ่มปุ่มเสียงลงในหน้าจอ
        
        createStartButton(0, 740, 320, 500, 500, "playButton.png", "start");
        //createStartButton(0, 750, 280, 500, 500, "playButtonClicked.png", "start");

        bgPanel[0].add(bgLabel[0]);
        window.add(bgPanel[0]);
    }

    public void generateDialogueScene() { //page2 ->สั่งข้าว
    
        // screen1
        createBackground(1, "secondpage.png");
    
        // เรียกใช้งาน class ตัวละคร
        animeCast anime = new animeCast();
        anime.setOpaque(false);  // ทำให้ตัวละครมีพื้นหลังโปร่งใส
        anime.setBounds(0, -100, 1920, 1080);  // ตั้งค่าตำแหน่งเริ่มต้นของตัวละคร
        window.add(bgLabel[1]);
        anime.startMoving();  // เริ่มการเคลื่อนที่ของตัวละคร
        bgPanel[1].add(anime); // เพิ่มตัวละครลงในหน้าจอ

        YesButton yes = new YesButton(window);
        bgPanel[1].add(yes.getYesButton());
    
        // ปุ่มลูกศรไปหน้าถัดไป
        createArrowButton(1, 1620, 400, 300, 300, "rightArrow300x300.png", "goSelectIngScreen");
    
        // เพิ่ม bgPanel[1] ลงใน window
        bgPanel[1].add(bgLabel[1]);
        window.add(bgPanel[1]);
    
        // รีเฟรชหน้าจอ
        window.revalidate();
        window.repaint();
    
        // เริ่มการเคลื่อนที่ของตัวละคร
        //moveCharacterIn(anime);
    }
    
    
    
    //generate select ingredient scene
    public void generateSelectIngScene() { //page3 ->เลือกวัตถุดิบ
    	
    	//screen2
    	createBackground(2,"thirdpage.png");
        
    	
    	// add ingredients
    	createObject(2, 200, 300, 300, 300, "kapao300x300.png", "addkapao");
    	createObject(2, 550, 300, 300, 300, "micedpork 300x300.png", "addmpork");
    	
    	createArrowButton(2, 0, 400, 300, 300, "leftArrow300x300.png", "goDialogueScreen");
    	createArrowButton(2, 1620, 400, 300, 300, "rightArrow300x300.png", "goStoveScreen");
    	
    	// add to window
    	bgPanel[2].add(bgLabel[2]);
    	window.add(bgPanel[2]);
    }
    public void generateStoveScene() {
    	
    	// screen3
    	createBackground(3,"menubg1920x1080.png");
    	
    	createObject(3, 600, 700, 300, 300,	 "rice300x300.png", "addRice");
    	createObject(3, 200, 700, 300, 300,	 "stove300x300.png", "stove");
    	
    	createArrowButton(3, 0, 400, 300, 300, "leftArrow300x300.png", "goSelectIngScreen");
    	createArrowButton(3, 1620, 400, 300, 300, "rightArrow300x300.png", "goPackageScreen");
    	
    	// add to window
    	bgPanel[3].add(bgLabel[3]);
    	window.add(bgPanel[3]);
    }
    
    // generate package scenes
    public void generatePackageScene() {
    	
    	// screen4
    	createBackground(4,"packagebg1920x1080.png");
    	
    	createArrowButton(4, 0, 400, 300, 300, "leftArrow300x300.png", "goStoveScreen");
    	
    	// add to window
    	bgPanel[4].add(bgLabel[4]);
    	window.add(bgPanel[4]);
    }
    //change stove image
    public void changeStove(String Command) {
    	
        // delete
    	window.remove(bgPanel[3]);
            
        // create new
    	createBackground(3,"menubg1920x1080.png");
    	
    	createObject(3, 600, 700, 300, 300,	 "rice300x300.png", "addRice");
    	
    	createArrowButton(3, 0, 400, 300, 300, "leftArrow300x300.png", "goSelectIngScreen");
    	createArrowButton(3, 1620, 400, 300, 300, "rightArrow300x300.png", "goPackageScreen");
        
        //create stove new stove
        System.out.println(Command);
        
        switch(Command) {
        	
        	//default : createObject(3, 200, 700, 300, 300, "stove300x300.png", "stove"); break;
        	// change image at stove
        	case "addkapao" : 
        		
        		// add kapao
        		createObject(3, 200, 700, 300, 300, "kapao300x300.png", "addkapao");
        		isKapao = true;
        		break;
        		
    		case "addmpork" : 
    			
    			// add micedpork
    			createObject(3, 200, 700, 300, 300, "micedpork 300x300.png", "addmpork");
    			isKapao = true;
    			break;
    		
    		//add rice
    		case "addRice" : 

    			// have kapao in stove?
    			if(isKapao) {
    				isKapao = false;
    				onStove = false;
    				createObject(3, 200, 700, 300, 300,	 "stove300x300.png", "stove");
    				createObject(3, 1000, 700, 300, 300, "kapaoRice300x300.png", "insertKapao"); 
    			}
    			break;
    		case "BURNED" : createObject(3, 200, 700, 300, 300,	 "burned 300x300.png", ""); break;
        }

        //add panel
        bgPanel[3].add(bgLabel[3]);
        window.add(bgPanel[3]);
        
        // refresh
        window.revalidate();
        window.repaint();
    }
    public void packKapao(String Command) {
    	
    	// delete
    	window.remove(bgPanel[4]);
    	
    	// create new
    	createBackground(4,"packagebg1920x1080.png");
    	
    	createArrowButton(4, 0, 400, 300, 300, "leftArrow300x300.png", "goStoveScreen");
    	
    	switch (Command) {
    	
    		case "insertKapao" : 
    			
    			// add kapao with rice
    			createObject(4, 200, 500, 300, 300, "kapaoRice300x300.png", "ready2pack"); 
    			break;
    		case "ready2pack":
    			
    			// pack kapao
    			System.out.println("PACK TIME");
    			createObject(4, 900, 500, 300, 300, "package300x300.png", "NULL"); 
    			break;
    	}
    	
    	// add to window
    	bgPanel[4].add(bgLabel[4]);
    	window.add(bgPanel[4]);
    	
    	// refresh
        window.revalidate();
        window.repaint();
    }
    // timer
    	
    public void generateGameOverScene() {
    	
    	createBackground(5,"gameoverbg1920x1080.png");
    	
    	createArrowButton(5, 960, 500, 300, 300, "restart300x300.png", "restart");
    	
    	bgPanel[5].add(bgLabel[5]);
    	window.add(bgPanel[5]);
    }
    // timer
    public void timer(int time, String Command) {
    	Timer timer = new Timer();
    	
    	timerText = new JTextArea();
        
        bgPanel[1].setLayout(null);

        // Set timer display properties
        timerText.setBounds(960, 100, 50, 30);
        timerText.setForeground(Color.white);
        timerText.setBackground(Color.black);
        timerText.setOpaque(true); // Ensure visibility
        timerText.setEditable(false);
        timerText.setFont(new Font("Book Antiqua", Font.PLAIN, 26));
        
     // Add the timer text to the panel
        bgPanel[1].add(timerText);
        bgPanel[1].setComponentZOrder(timerText, 0); // Bring to front

        // Refresh UI
        bgPanel[1].revalidate();
        bgPanel[1].repaint();
        
    	TimerTask task = new TimerTask() { 
    	
    	int count = time;

    	@Override
    	public void run() {
    		
    		// decrease time
    		SwingUtilities.invokeLater(() -> {
                timerText.setText(""+count); // Update text
            });
           
    		System.out.println(count);
    		count--;   
    		
    		// functions
    		if(count < 0 && Command.equals("OVERALL")) {
    			
    			// create game over scene
    			generateGameOverScene();
    			gm.sChanger.showGameOver();
    			timer.cancel();
    			
    		} else if (count < 0 && Command.equals("STOVE") && onStove == true) {
    			
    			// create burned
    			changeStove("BURNED");
    			timer.cancel();
    		} else if (pause == true) {
    			
    			// pause & keep time
    			remainingTime = count;
    			timer.cancel();
    		}
    	}};
    	// delay
    	timer.scheduleAtFixedRate(task, 0, 1000);
    }
    
    public void reset() {
    	
    	System.out.println("restart!");
    	
    	// delete everything
    	window.remove(window);
    	
    	//create title scene
        createMainField();
        generateTitle();
        window.setVisible(true);
        
        // Refresh UI
        window.revalidate();
        window.repaint();
    }
}
