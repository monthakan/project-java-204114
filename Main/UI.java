package Main;

import animation.enterthescene;
import animation.exitthescene;
import entity.CustomDialog;
import entity.OrderMessage;
import entity.animeCast;
import sound.PlayButSound;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.AlphaComposite;
import java.net.URL;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import ui.ExitButton;
import ui.HintButton;
import ui.SoundButton;
import ui.WhatButton;
import ui.ContinousButton;
import ui.PauseScene;


public class UI {

	private Image backgroundImage;
	private int bgNow = 0; // Initialize bgNow

    GameManager gm;
    
    JFrame window;
	public JTextArea timerText, timerTextA, timerTextB, scoreText;
	private int remainingTime = 180; // Store the remaining time
	private int remainingTimei = 0; // Initialize remainingTimei
    private int remainingTimeA = 15; // Store the remaining time for stove A
    private int remainingTimeB = 15; // Store the remaining time for stove B
	private static boolean paused = false;
	public CustomDialog customDialog;
	public JPanel bgPanel[] = new JPanel[7];
	private JButton buttonYes;
	public JLabel bgLabel[] = new JLabel[7];
	private SoundButton soundButton;
	private ContinousButton contiButton;
	private animeCast anime; //instance of animeCast
	private JLabel serveAObject;
	private JLabel serveBObject;


    // overall timer  -> tan add
    private Timer timer = null; // เก็บ Timer ไว้ใช้ซ้ำ
	private int count = 0; // เวลาที่เหลืออยู่
	private boolean isPaused = false; // ตรวจสอบว่าหยุดเวลาหรือไม่

    
	private boolean pause = false, is2Box = true, aPass = false; // is2Box
	private boolean timeStarted = false;
	private boolean timerStarted = false;
	private boolean onStoveA = false, onStoveB = false;
    private boolean onBurnA = false, onBurnB = false;
    private boolean onKapaoA = false, onKapaoB = false;
    private boolean onBoxA = false, onBoxB = false;
    private boolean onCloseBoxA = false, onCloseBoxB = false;
    private boolean onTableA = false, onTableB = false;
    
	private int [] list_item = {0, 0, 0}; // 0 = kapao, 1 = rice, 2 = basil
	// TODO aomsin added this
	private ArrayList <String> bowl = new ArrayList<String>();
	private ArrayList <String> panA = new ArrayList<String>();
	private ArrayList <String> panB = new ArrayList<String>();
	private ArrayList <String> boxA = new ArrayList<String>();
	private ArrayList <String> boxB = new ArrayList<String>();

	// cooking animation
	private boolean onpanA=false,onpanB=false;
	private ArrayList <ArrayList<Integer>> panAing = new ArrayList<ArrayList<Integer>>();
	private ArrayList <ArrayList<Integer>> panBing = new ArrayList<ArrayList<Integer>>();
	private ArrayList <JLabel> obj1 = new ArrayList <JLabel>();
	private ArrayList <JLabel> obj2 = new ArrayList <JLabel>();
    
    
    // main ui
    public UI(GameManager gm) {
        
        this.gm = gm;
        
        //title scene
        createMainField();
        generateTitle();

		//tan add
		soundButton = SoundButton.getInstance(window);
		window.setLayout(null);
        //----------------------
        
        window.setVisible(true);
    }
    public void createMainField() { //สร้างหน้าต่างเกม
        window = new JFrame("Padkapao Good");
        window.setSize(1680, 1050);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
    }

    public void createBackground(int bgNum, String bgFileName) {
		//panel setting
        bgPanel[bgNum] = new JPanel();
        bgPanel[bgNum].setBounds(0, 0, 1680, 1050);
        bgPanel[bgNum].setLayout(null);

		//label setting
        bgLabel[bgNum] = new JLabel();
        bgLabel[bgNum].setBounds(0, 0, 1680, 1050);

		//add bg image
        URL location = getClass().getResource("/resources/picture/" + bgFileName);
        if (location != null) {
            ImageIcon icon = new ImageIcon(location);
            bgLabel[bgNum].setIcon(icon);
        } else {
            System.err.println("ไม่พบไฟล์: " + bgFileName);
        }

    }
	//create object
        
	public void createObject(int bgNum, int objx, int objy, int objWidth, int objHeight, String objFileName,String Command) {

		// create object
		JLabel objectLabel = new JLabel();

		// object setting
		objectLabel.setBounds(objx, objy, objWidth, objHeight); // x, y, width, height
		URL objLocation = getClass().getResource("/resources/picture/" + objFileName);
		if (objLocation != null) {
			ImageIcon objectIcon = new ImageIcon(objLocation);
			objectLabel.setIcon(objectIcon);
			int originalWidth = objectIcon.getIconWidth();
			int originalHeight = objectIcon.getIconHeight();
			double newWidth = originalWidth / 1.5;
			double newHeight = originalHeight / 1.5;
			Image scaledImage = objectIcon.getImage().getScaledInstance((int) newWidth, (int) newHeight, Image.SCALE_SMOOTH);
			objectLabel.setIcon(new ImageIcon(scaledImage));

		} else {
			System.err.println("ไม่พบไฟล์: " + objFileName);
		}

		if (Command.length() > 3 && Command.substring(0, 3).equals("ing")) {
			objectLabel.setIcon(null);
		}
		// if (objFileName.equals("rawKapao300x300.png")) {
		// 	objectLabel.setIcon(null);
		// }
		

		// object actions
		objectLabel.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
			}

			// left click
			public void mousePressed(MouseEvent e) {

				if (SwingUtilities.isRightMouseButton(e) && Command.equals("stove")) {}
				if (SwingUtilities.isLeftMouseButton(e) && !Command.equals("stove")) {

					System.out.println("Left Click!");
					new PlayButSound("/resources/audio/startgame.wav",6.0f); 
					//ปักจ้า


					if (Command.equals("COOK") && (onStoveA == false || onStoveB == false)) { //ทำอาหาร
						// delete prepare
						window.remove(bgPanel[2]);
						new PlayButSound("/resources/audio/FryingPanSizzling.wav",6.0f);  //---> เสียงตอนผัด

						// create new
						generateSelectIngScene();

						// stove a and stove b
						if (onStoveA == false) { 

							changeStove("rawA");
							onStoveA = true;
							timerStoveA(15);
						} else if (onStoveA == true && onStoveB == false) { 
							new PlayButSound("/resources/audio/FryingPanSizzling.wav",6.0f); 

							changeStove("rawB");
							onStoveB = true;
							timerStoveB(15);
						}

						if (onStoveA == false || onStoveB == false){clearContainer(bowl);}
							
						// refresh
						window.revalidate();
						window.repaint();
					} else if (Command.contains("ready2")) { //ใส่กล่อง

						// box function
						changeStove(Command); 

					} else if (Command.contains("serve")) { //เสิร์ฟ

						// serve function
						serve(Command);
					} else if (Command.equals("A") || Command.equals("B")) {

						// pass
						System.out.println("NO FUNCTION NAJA");
					} else if (Command.equals("TRASH")) { //ถังขยะ

						// renew select ingredient scene
						window.remove(bgPanel[2]);
						generateSelectIngScene();

						clearContainer(bowl);

						// refresh
						window.revalidate();
						window.repaint();
					}
					else if (Command.equals("TRASHSTOVE")) {//เคลียร์เตา

						window.remove(bgPanel[3]);
						generateStoveScene();

						clearContainer(panA);
						clearContainer(panB);
						onKapaoA = false;
						onStoveA = false;
						onKapaoB = false;
						onStoveB = false;

						// refresh
						window.revalidate();
						window.repaint();
					} else if (Command.length() > 5 && Command.substring(0, 5).equals("debug")){ // debug
						switch (Command){ // debug
							case "debug1":debug_viewcontainer(bowl);break; // debug
							case "debug2":debug_viewcontainer(panA);break; // debug
							case "debug3":debug_viewcontainer(panB);break; // debug
							case "debug4":debug_viewcontainer(boxA);break; // debug
							case "debug5":debug_viewcontainer(boxB);break; // debug
						} // debug
					} else {
						// change select ingredient scene
						prepare(Command);
					}
				}
			}

			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			
		});

		// add panel
		bgPanel[bgNum].add(objectLabel);
	}
	// TODO aomsin added this
	// alterative to createobject (cooking animation)
	public void createPicture(int objx, int objy, int objWidth, int objHeight, String objFileName,char stove){
		JLabel icon = new JLabel();

		// object setting
		icon.setBounds(objx, objy, objWidth, objHeight); // x, y, width, height
		URL objLocation = getClass().getResource("/resources/picture/" + objFileName);
		if (objLocation != null) {
			ImageIcon objectIcon = new ImageIcon(objLocation);
			icon.setIcon(objectIcon);
		} else {
			System.err.println("ไม่พบไฟล์: " + objFileName);
		}
		if (stove == 'A'){
			obj1.add(icon);
		}else if (stove == 'B'){
			obj2.add(icon);
		}
		bgPanel[3].add(icon);
	}
    public void createArrowButton(int bgNum, int x, int y, int Width, int Height, String arrowFileName, String Command) {
    	
    	//add arrow image
        URL arrowLocation = getClass().getResource("/resources/picture/" + arrowFileName);
        if (arrowLocation == null) {
            System.err.println("ไม่พบไฟล์: " + arrowFileName);
        } else{
			JButton arrowButton = new JButton();
			ImageIcon arrowIcon = new ImageIcon(arrowLocation);
			arrowButton.setIcon(arrowIcon);
            int originalWidth = arrowIcon.getIconWidth();
            int originalHeight = arrowIcon.getIconHeight();
            double newWidth = originalWidth / 1.5;
            double newHeight = originalHeight / 1.5;
            Image scaledImage = arrowIcon.getImage().getScaledInstance((int) newWidth, (int) newHeight, Image.SCALE_SMOOTH);
			arrowButton.setIcon(new ImageIcon(scaledImage));
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
							new PlayButSound("/resources/audio/startgame.wav",6.0f); 
                            ImageIcon clickedIcon = new ImageIcon(getClass().getResource("/resources/picture/playButtonClicked.png"));
                            startButton.setIcon(clickedIcon);
                            generateDialogueScene(); // สร้างหน้าจอสั่งข้าว
                            generateSelectIngScene(); // สร้างหน้าจอเลือกวัตถุดิบ
                            generateStoveScene(); // สร้างหน้าจอเตา
                            generateExtraScene(); // สร้างหน้าจอห่อข้าว
                            
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

    public static boolean isPaused() {
        return paused;
    }

    public static void setPaused(boolean paused) {
        UI.paused = paused;
    }
	public void createPauseButton(int bgNum, int x, int y, int width, int height, String imageName) {
		// create object
        JLabel pauseLabel = new JLabel();
        
        // object setting
        pauseLabel.setBounds(x, y, width, height); // x, y, width, height
		URL pauseLocation = getClass().getResource("/resources/picture/" + imageName);
		if (pauseLocation != null) {
		ImageIcon pauseIcon = new ImageIcon(pauseLocation);
		int originalWidth = pauseIcon.getIconWidth();
		int originalHeight = pauseIcon.getIconHeight();
		double newWidth = originalWidth / 1.5;
		double newHeight = originalHeight / 1.5;
		Image scaledImage = pauseIcon.getImage().getScaledInstance((int) newWidth, (int) newHeight, Image.SCALE_SMOOTH);
		pauseLabel.setIcon(new ImageIcon(scaledImage));
		} else {
		System.err.println("ไม่พบไฟล์: " + imageName);
		}
        
        // object actions
        pauseLabel.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {}
			// left click
			public void mousePressed(MouseEvent e) {
				
				if(SwingUtilities.isLeftMouseButton(e)) {
					
					// get bg now
					new PlayButSound("/resources/audio/startgame.wav",6.0f); 
					int bgNow = gm.sChanger.getbgNow(); 
					System.out.println("PAUSE" + bgNow);
					
					
					if (pause == false) {
						
						pause = true;
						
						
						if(bgNow==1){
							// สร้าง Background และ Pause Button ใหม่
							createBackground(6, "gamePaused1.png");
							gm.ui.customDialog.showDialog(false);

							createPauseButton(6, 0, 0, 20000, 20000, "gamePaused1.png");
							bgPanel[6].add(bgLabel[6]);
							window.add(bgPanel[6]);
							
							// รีเฟรช UI หลังเพิ่มคอมโพเนนต์ใหม่
							window.revalidate();
							window.repaint();

						}
						if(bgNow==2){
							createBackground(6, "gamePaused2.png");
							createPauseButton(6, 0, 0, 20000, 20000, "gamePaused2.png");
							bgPanel[6].add(bgLabel[6]);
    						window.add(bgPanel[6]);
						}
						if(bgNow==3){
							createBackground(6, "gamePaused3.png");
							createPauseButton(6, 0, 0, 20000, 20000, "gamePaused3.png");
							bgPanel[6].add(bgLabel[6]);
    						window.add(bgPanel[6]);
						}
						if(bgNow==4){
							createBackground(6, "gamePaused3.png");
							createPauseButton(6, 0, 0, 20000, 20000, "gamePaused3.png");
							bgPanel[6].add(bgLabel[6]);
    						window.add(bgPanel[6]);
						}
						if(bgNow==5){
							createBackground(6, "gamePaused5.png");
							createPauseButton(6, 0, 0, 20000, 20000, "gamePaused5.png");
							bgPanel[6].add(bgLabel[6]);
    						window.add(bgPanel[6]);
						}
						// hide bg now
						bgPanel[bgNow].setVisible(false);
						
					} else {
						
						pause = false;
						
						// delete pause screen
						if(bgNow==1){
							window.remove(bgPanel[6]);
							gm.ui.customDialog.showDialog(true); // ตอน Resume
							// continous time
							timer(remainingTime, "Continous");
							// show bg now
							bgPanel[bgNow].setVisible(true);
						}
						
						window.remove(bgPanel[6]);
						gm.ui.customDialog.showDialog(true); // ตอน Resume

						// continous time
						timer(remainingTime, "Continous");
						// show bg now
						bgPanel[bgNow].setVisible(true);
					}
			}}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
        });
        
        //add panel
        bgPanel[bgNum].add(pauseLabel);
	}
	
	//---------------------------------------------------------------------
    // generate window
    
	// generate menu
    public void generateTitle(){ //หน้าแรกของเกม pageone

        // สร้างหน้าจอเมนู
        bgPanel[0] = new JPanel();
        bgPanel[0].setBounds(0, 0, 1680, 1050);
        bgPanel[0].setLayout(null);

        createBackground(0, "firstpagebg.png");
        //createExitButton(0, 1800, 0, 120, 130, "ExitButton.png", "exit");
        // เรียกใช้งาน class ExitButton และสร้างปุ่ม Exit
        ExitButton exit = new ExitButton(window); // สร้างปุ่ม Exit
        bgPanel[0].add(exit.getExitButton()); // เพิ่มปุ่ม Exit ลงในหน้าจอ

        // เรียกใช้งาน class SoundButton
		SoundButton soundButton = SoundButton.getInstance(window);
        bgPanel[0].add(soundButton.getSoundButton()); // เพิ่มปุ่มเสียงลงในหน้าจอ
        
        createStartButton(0, 620, 300, 500, 500, "playButton.png", "start");
        //createStartButton(0, 750, 280, 500, 500, "playButtonClicked.png", "start");

        bgPanel[0].add(bgLabel[0]);
        window.add(bgPanel[0]);
    }
	public void generateDialogueScene() { // page2 -> สั่งข้าว

		createBackground(1, "secondpage.png");
		createObject(1, 140, 0, 200, 120, "clock.png", "");
		createPauseButton(1, 0, 0, 120, 120, "pause300x300.png");


		anime = new animeCast();
		gm.ui.customDialog = new CustomDialog(); //ใช้ customDialog ใน UI
		enterthescene scene = new enterthescene(anime, gm.ui.customDialog); //animation
	
		scene.setOpaque(false);
		scene.add(anime);
		window.add(scene);
		scene.resetPosition();
	
		anime.setOpaque(false);
		anime.setBounds(0, -122, 1650, 1020);
		window.add(bgLabel[1]);
		bgPanel[1].setLayout(null);
		bgPanel[1].add(anime);
	
		scene.resetPosition();
	
		Random random = new Random();
		boolean isWhatButton = random.nextBoolean();
		JButton button;
		if (isWhatButton) {
			WhatButton what = new WhatButton(window, gm);
			button = what.getWhatButton();
			buttonYes = what.getYesButton();
			System.out.println("สุ่มได้: WhatButton");
		} else {
			HintButton hint = new HintButton(window, gm);
			button = hint.getHintButton();
			buttonYes = hint.getYesButton();
			System.out.println("สุ่มได้: HintButton");
		}
		bgPanel[1].add(button);
		bgPanel[1].add(buttonYes);
		
	
		ExitButton exit = new ExitButton(window);
		bgPanel[1].add(exit.getExitButton());
	
		bgPanel[1].add(bgLabel[1]);
		window.add(bgPanel[1]);
	
		window.revalidate();
		window.repaint();
		
		if (!timeStarted) {
			timer(remainingTime, "OVERALL");
			timeStarted = true;
		} else {
			resumeTimer();  // ใช้เวลาเดิมเมื่อเรียกใหม่
		}
	}
	public void generateLoopScene() { 
		// page2 -> สั่งข้าว
	
		// สร้างพื้นหลังใหม่
		//animateCharacterExit();
		createBackground(1, "secondpage.png");
	
		// สร้าง object อื่นๆ
		createObject(1, 140, 0, 200, 120, "clock.png", "");
		createPauseButton(1, 0, 0, 120, 120, "pause300x300.png");
	
		bgPanel[1].add(timerText);
		bgPanel[1].setComponentZOrder(timerText, 0); // ทำให้ timerText อยู่ข้างบนสุด

	
		// สร้างและตั้งค่าตัวละครแอนิเมชั่น
		anime = new animeCast();
		gm.ui.customDialog = new CustomDialog(); // ใช้ customDialog ใน UI
		enterthescene scene = new enterthescene(anime, gm.ui.customDialog); // animation
	
		scene.setOpaque(false);
		scene.add(anime);
		window.add(scene);
		scene.resetPosition();
	
		anime.setOpaque(false);
		anime.setBounds(0, -122, 1650, 1020);
		window.add(bgLabel[1]);
		bgPanel[1].setLayout(null);
		bgPanel[1].add(anime);
	
		scene.resetPosition();
	
		// สุ่มปุ่มที่จะแสดง
		Random random = new Random();
		boolean isWhatButton = random.nextBoolean();
		JButton button;
		if (isWhatButton) {
			WhatButton what = new WhatButton(window, gm);
			button = what.getWhatButton();
			buttonYes = what.getYesButton();
			System.out.println("สุ่มได้: WhatButton");
		} else {
			HintButton hint = new HintButton(window, gm);
			button = hint.getHintButton();
			buttonYes = hint.getYesButton();
			System.out.println("สุ่มได้: HintButton");
		}
	
		// เพิ่มปุ่มที่สุ่มมา
		bgPanel[1].add(button);
		bgPanel[1].add(buttonYes);
	
		// สร้างปุ่มออก
		ExitButton exit = new ExitButton(window);
		bgPanel[1].add(exit.getExitButton());
	
		// เพิ่ม bgLabel[1] และอัพเดตหน้าจอ

		bgPanel[1].add(bgLabel[1]);
		window.add(bgPanel[1]);
	
		window.revalidate();
		window.repaint();
	}	
	

    
    public void generateSelectIngScene() { // page3 ->เลือกวัตถุดิบ

		// screen2

		createBackground(2, "thirdpage.png");
		createPauseButton(2, -20, 10, 130, 110, "pause300x300.png");

		createObject(2, 60, 5, 200, 150, "trash300x300.png", "TRASH");

		// add ingredients
		createObject(2, 508, 914, 200, 100, "ViewDebug200x100.png", "debug1"); // debug

		createObject(2, 46, 708, 350, 180, "350x180.png", "ingkapaosauce");
		createObject(2, 46, 491, 350, 180, "350x180.png", "ingkapao");
		createObject(2, 13, 266, 240, 190, "240x190.png", "ingdriedpepper");
		createObject(2, 279, 266, 240, 190, "240x190.png", "ingredpepper");
		createObject(2, 546, 266, 240, 190, "240x190.png", "inggarlic");
		createObject(2, 812, 266, 240, 190, "240x190.png", "ingonion");
		createObject(2, 1079, 266, 240, 190, "240x190.png", "ingmincedpork");
		createObject(2, 1345, 266, 240, 190, "240x190.png", "ingmeat");
		createObject(2, 1383, 511, 240, 190, "240x190.png", "ingchicken");
		createObject(2, 1383, 726, 240, 190, "240x190.png", "ingcrispypork");

		createArrowButton(2, 1000, -5, 280, 170, "extraButton.png", "goExtraScreen");

		//ต้องแก้
		createArrowButton(2, 1500, 400, 300, 300, "rightArrow300x300.png", "goStoveScreen");
		ExitButton exit = new ExitButton(window);
		bgPanel[2].add(exit.getExitButton());

		// add to window
		bgPanel[2].add(bgLabel[2]);
		window.add(bgPanel[2]);
	}

	public void generateExtraScene() { 

		// screen4

		createBackground(4, "fourthpage.png");
		createPauseButton(4, -25, 0, 130, 110, "pause300x300.png");

		// row1
		createObject(4, 248, 521, 200, 362, "Placeholder.png", "ingmushroom");
		createObject(4, 487, 521, 250, 180, "Placeholder.png", "ingcorn");
		createObject(4, 733, 521, 241, 180, "Placeholder.png", "ingmackerel");
		createObject(4, 973, 521, 241, 180, "Placeholder.png", "inggreenyellowbean");
		createObject(4, 1216, 521, 220, 183, "Placeholder.png", "ingbacon");
		createObject(4, 1444, 521, 194, 181, "Placeholder.png", "ingcowpea");
		// row2
		createObject(4, 248, 763, 241, 170, "Placeholder.png", "ingfriedegg");
		createObject(4, 487, 763, 240, 176, "Placeholder.png", "ingsaltyegg");
		createObject(4, 731, 763, 240, 179, "Placeholder.png", "ingomelet");
		createObject(4, 970, 763, 240, 177, "Placeholder.png", "ingcatfishflake");
		createObject(4, 1216, 763, 222, 176, "Placeholder.png", "ingchickenentrails");
		createObject(4, 1441, 763, 194, 174, "Placeholder.png", "ingtofu");

		createArrowButton(4, 1000, -5, 280, 170, "extraButton.png", "goSelectIngScreen");

		// add to window
		ExitButton exit = new ExitButton(window);
		bgPanel[4].add(exit.getExitButton());

		bgPanel[4].add(bgLabel[4]);
		window.add(bgPanel[4]);
	}
    // TODO edited
	public void addIngredientToContainer(String ingredient) {  //เพิ่มวัตถุดิบลงในชาม (วัตถุที่อยู่ในชาม)
		if (bowl.size() <= 20){
			bowl.add(ingredient);
		} else {
			System.out.println("FULL");
			return;
		}

		// x:551 y:643 bowl1
		// x:992 y:641 bowl2
		// size:265x154 - 50x50 (pngsize) -> 215x104
		int x = random(120);
		int y = random(40);
		//System.out.println(x+" "+y);

		// generate objext
		if (onStoveA == false){ // bowl1
			//onbowl = true;
			createObject(2, 566, 636, 300, 300, "rawKapao300x300.png", "COOK");
			createObject(2, 530+x, 620+y, 200, 200, ingredient+".png", "COOK");
			createObject(2, 530+x, 620+y, 200, 200, ingredient+".png", "COOK");

		}//else if (onbowl && onStoveA == false){}
		else if (onStoveB == false && onStoveA == true){ // bowl2
			//onbowl = true;
			//createObject(2, 944, 636, 300, 300, "rawKapao300x300.png", "COOK");
			createObject(2, 992+x, 643+y, 200, 200, ingredient+".png", "COOK");

		}else if (onStoveB == true && onStoveA == true){ // bowl1
			//onbowl = true;
			//createObject(2, 566, 636, 300, 300, "rawKapao300x300.png", "COOK");
			createObject(2, 500+x, 620+y, 200, 200, ingredient+".png", "COOK");

		}
	}

	// TODO edited
	public void clearContainer(ArrayList container) {
		container.clear();

	}

	// TODO edited
	public void debug_viewcontainer(ArrayList container) {
		System.out.print("Ing : ");
		System.out.println(container.stream().collect(Collectors.joining(" ")));

		
	}
	// TODO aomsin added this
	public int random(int max){
		return (int)(Math.round(Math.ceil(Math.random() * max*10))/10);
	}
	// TODO aomsin added this
	// cooking animation  -> bug
	public void moveIT(char stove){ //cooking animation
		final int amount = 15;
		final int max = 150;

		if (stove == 'A'){
			if (!onpanA){
				// scatter ingredients
				for (int i=0;i<panA.size();i++){
					onpanA = true;
					ArrayList<Integer> cord = new ArrayList<Integer>(); // {x,y}
					// stove is 300x300
					// image size is 50x50
					// max is 250x250
					cord.add(random(max)); // x
					cord.add(random(max)); // y
					// System.out.println("AAAAAAAAAAAAAAAA"+cord.get(0)+" "+cord.get(1));
					panAing.add(cord);
					
					createPicture(161+cord.get(0), 500+cord.get(1), 300, 200, panA.get(i)+".png",'A'); 
				}
			} else {
			// remove&clear stove ingredient
			for (int i=0;i<obj1.size();i++){
				bgPanel[3].remove(obj1.get(i));
				
			}
			obj1.clear();
			bgPanel[3].revalidate();
        	bgPanel[3].repaint();

			// 1=up(-y) 2=down(+y) 3=right(-x) 4=left(+x)
			// overflow = opposite direction
			for (int i=0;i<panA.size();i++){
				switch (random(20)){
					case 1:{
						if (panAing.get(i).get(1)-amount>=0){panAing.get(i).set(1,panAing.get(i).get(1)-amount);}
						else {panAing.get(i).set(1,panAing.get(i).get(1)+amount);} 
						break;
					}
					case 2:{
						if (panAing.get(i).get(1)+amount<=max){panAing.get(i).set(1,panAing.get(i).get(1)+amount);}
						else {panAing.get(i).set(1,panAing.get(i).get(1)-amount);}
						break;
					}
					case 3:{
						if (panAing.get(i).get(0)-amount>=0){panAing.get(i).set(0,panAing.get(i).get(0)-amount);}
						else {panAing.get(i).set(0,panAing.get(i).get(0)+amount);}
						break;
					}
					case 4:{
						if (panAing.get(i).get(0)+amount<=max){panAing.get(i).set(0,panAing.get(i).get(0)+amount);}
						else {panAing.get(i).set(0,panAing.get(i).get(0)-amount);}
						break;
					}
				}
				createPicture(150+panAing.get(i).get(0), 589+panAing.get(i).get(1), 300, 200, panA.get(i)+".png",'A');
			}
			}
			

		} if (stove == 'B'){
			new PlayButSound("/resources/audio/FryingPanSizzling.wav",6.0f); 
			if (!onpanB){
				// scatter ingredients
				for (int i=0;i<panB.size();i++){
					onpanB = true;
					ArrayList<Integer> cord = new ArrayList<Integer>(); // {x,y}
					// stove is 300x300
					// image size is 50x50
					// max is 250x250
					cord.add(random(max)); // x
					cord.add(random(max)); // y
					panBing.add(cord);
					
					createPicture(630+cord.get(0), 540+cord.get(1), 300, 200, panB.get(i)+".png",'B');
					new PlayButSound("/resources/audio/AddedIngredienttoFryingPan.wav",6.0f); 
				}
			} else {
			// remove&clear stove ingredient
			for (int i=0;i<obj2.size();i++){
				bgPanel[3].remove(obj2.get(i));
			}
			obj2.clear();
			bgPanel[3].revalidate();
        	bgPanel[3].repaint();

			// 1=up(-y) 2=down(+y) 3=right(-x) 4=left(+x)
			// overflow = opposite direction
			for (int i=0;i<panB.size();i++){
				switch (random(4)){
					case 1:{
						if (panBing.get(i).get(1)-amount>=0){panBing.get(i).set(1,panBing.get(i).get(1)-amount);}
						else {panBing.get(i).set(1,panBing.get(i).get(1)+amount);}
						break;
					}
					case 2:{
						if (panBing.get(i).get(1)+amount<=max){panBing.get(i).set(1,panBing.get(i).get(1)+amount);}
						else {panBing.get(i).set(1,panBing.get(i).get(1)-amount);}
						break;
					}
					case 3:{
						if (panBing.get(i).get(0)-amount>=0){panBing.get(i).set(0,panBing.get(i).get(0)-amount);}
						else {panBing.get(i).set(0,panBing.get(i).get(0)+amount);}
						break;
					}
					case 4:{
						if (panBing.get(i).get(0)+amount<=max){panBing.get(i).set(0,panBing.get(i).get(0)+amount);}
						else {panBing.get(i).set(0,panBing.get(i).get(0)-amount);}
						break;
					}
				}
				createPicture(660+panBing.get(i).get(0), 589+panBing.get(i).get(1), 300, 200, panA.get(i)+".png",'B');
			}
			}

		}
		//createObject(3, 181, 630, 300, 300, "stove300x300.png", "animate");

		
		bgPanel[3].add(bgLabel[3]);
		window.add(bgPanel[3]);
		bgPanel[3].revalidate();
        bgPanel[3].repaint();
	}
	// prepare item
	public void prepare(String Command) { //เลือกวัตถุดิบ

		// delete
		window.remove(bgPanel[2]);

		// //screen2
		// createBackground(2,"thirdpage.png");
		// createPauseButton(2, 1500, 0, 300, 300, "pause300x300.png","");
		// createObject(2, 0, 0, 300, 300, "trash300x300.png", "TRASH");

		// // add ingredients
		// createObject(2, 200, 300, 300, 300, "kapao300x300.png", "kapao");
		// createObject(2, 550, 300, 300, 300, "micedpork 300x300.png", "mpork");
		switch (Command) {

			// add ingredient to prepare

			case "ingkapaosauce":addIngredientToContainer("kapaosauce");break;
			case "ingkapao":addIngredientToContainer("kapao");break;
			case "ingdriedpepper":addIngredientToContainer("driedpepper");break;
			case "ingredpepper":addIngredientToContainer("redpepper");break;
			case "inggarlic":addIngredientToContainer("garlic");break;
			case "ingonion":addIngredientToContainer("onion");break;
			case "ingmincedpork":addIngredientToContainer("mincedpork");break;
			case "ingmeat":addIngredientToContainer("meat");break;
			case "ingchicken":addIngredientToContainer("chicken");break;
			case "ingcrispypork":addIngredientToContainer("crispypork");break;

			// sp.
			case "ingmushroom":addIngredientToContainer("mushroom");break;
			case "ingcorn":addIngredientToContainer("corn");break;
			case "ingmackerel":addIngredientToContainer("makerel");break;
			case "inggreenyellowbean":addIngredientToContainer("greenyellowbean");break;
			case "ingbacon":addIngredientToContainer("bacon");break;
			case "ingcowpea":addIngredientToContainer("cowpea");break;
			case "ingfriedegg":addIngredientToContainer("friedegg");break;
			case "ingsaltyegg":addIngredientToContainer("saltyegg");break;
			case "ingomelet":addIngredientToContainer("omelet");break;
			case "ingcatfishflake":addIngredientToContainer("catfishflake");break;
			case "ingchickenentrails":addIngredientToContainer("chickenentrails");break;
			case "ingtofu":addIngredientToContainer("tofu");break;
		}

		// gm.score.setItem(Command);

		createArrowButton(2, 1000, -5, 280, 170, "extraButton.png", "goExtraScreen");
		// createArrowButton(2, 0, 400, 300, 300, "leftArrow300x300.png", "goDialogueScreen");
		// createArrowButton(2, 1620, 400, 300, 300, "rightArrow300x300.png", "goStoveScreen");

		// add to window
		bgPanel[2].add(bgLabel[2]);
		window.add(bgPanel[2]);

		// refresh
		window.revalidate();
		window.repaint();
	}

	public void generateStoveScene() { //24 feb   ->>> หน้าผัดไม่จริง
		

		createBackground(3, "fifthpage.png");
		createPauseButton(3, -25, 0, 130, 110, "pause300x300.png");

		createObject(3, 60, 5, 200, 150, "trash300x300.png", "TRASHSTOVE");
		createArrowButton(3, -50, 400, 300, 300, "leftArrow300x300.png", "goSelectIngScreen");
		//กล่องข้าวที่ว่างเปล่า
		createObject(3, 200, 200, 300, 300, "openBox300x300.png", "ready2closeA");
		createObject(3, 600, 200, 300, 300, "openBox300x300.png", "ready2closeB");
		//-------
		createObject(3, 281, 530, 200, 100, "ViewDebug200x100.png", "debug2"); // debug
		createObject(3, 790, 530, 200, 100, "ViewDebug200x100.png", "debug3"); // debug
		createObject(3, 300, 100, 200, 100, "ViewDebug200x100.png", "debug4"); // debug
		createObject(3, 600, 100, 200, 100, "ViewDebug200x100.png", "debug5"); // debug

		//createArrowButton(3, 0, 400, 300, 300, "leftArrow300x300.png", "goSelectIngScreen");
		createArrowButton(3, -50, 400, 300, 300, "leftArrow300x300.png", "goSelectIngScreen");
		createArrowButton(3, 1500, 400, 300, 300, "rightArrow300x300.png", ""); //25 feb
		//createArrowButton(3, 1620, 400, 300, 300, "rightArrow300x300.png", "goDialogueScreen");
		bgPanel[3].add(bgLabel[3]);
		window.add(bgPanel[3]);
	}
    
    
    public void generateGameOverScene() { //หน้าเกมส์ over
		// กรณีที่ต้องแก้ไขเพิ่ม -> หมดเวลาที่หน้ารับออเดอร์
		// กรณีที่ต้องแก้ไขเพิ่ม -> หมดเวลาที่หน้าเตา
		// กรณีที่ต้องแก้ไขเพิ่ม -> หมดเวลาที่หน้าห่อข้าว
		// กรณีที่ต้องแก้ไขเพิ่ม -> หมดเวลาที่หน้าสั่งข้าว
		// กรณีที่ต้องแก้ไขเพิ่ม -> หมดเวลาที่หน้าเลือกวัตถุดิบ
		// กรณีที่ต้องแก้ไขเพิ่ม -> หมดเวลาที่หน้าแรกของเกม
    	
    	scoreText = new JTextArea();
		


        // Set timer display properties
    	scoreText.setBounds(100, 150, 50, 30);
    	scoreText.setForeground(Color.white);
    	scoreText.setBackground(Color.black);
    	scoreText.setOpaque(true); // Ensure visibility
    	scoreText.setEditable(false);
    	scoreText.setFont(new Font("Book Antiqua", Font.PLAIN, 26));
        
        scoreText.setText(gm.score.getScore()+"");
    	
    	createBackground(5,"gameover.png");
    	
    	createArrowButton(5, 960, 500, 300, 300, "restart300x300.png", "restart");
    	
    	bgPanel[5].add(bgLabel[5]);
    	bgPanel[5].add(scoreText);
        bgPanel[5].setComponentZOrder(scoreText, 0); // Bring to front
    	window.add(bgPanel[5]);
    }
	public void generatePauseScene() {
    	
    	
    	createPauseButton(6, 1500, 0, 300, 300, "continueButton.png");
    	
    	// add to window
    	bgPanel[6].add(bgLabel[6]);
    	window.add(bgPanel[6]);
    }

	
	
	
	
	

    
    // ---------------------------------------------------------------------------------------
	// fuction
	// change stove image
	// TODO edited
	public void changeStove(String Command) {  //หน้าผัดที่แท้จริง

		// delete
		window.remove(bgPanel[3]);

		// create new
		createBackground(3, "fifthpage.png");

		createPauseButton(3, -25, 10, 130, 110, "pause300x300.png");
		createObject(3, 60, 5, 200, 150, "trash300x300.png", "TRASHSTOVE");
		createObject(3, 281, 530, 200, 100, "ViewDebug200x100.png", "debug2"); // debug
		createObject(3, 790, 530, 200, 100, "ViewDebug200x100.png", "debug3"); // debug
		createObject(3, 300, 100, 200, 100, "ViewDebug200x100.png", "debug4"); // debug
		createObject(3, 600, 100, 200, 100, "ViewDebug200x100.png", "debug5"); // debug

		//createArrowButton(3, 0, 400, 300, 300, "leftArrow300x300.png", "goSelectIngScreen");
		createArrowButton(3, -50, 400, 300, 300, "leftArrow300x300.png", "goSelectIngScreen");
		createArrowButton(3, 1500, 400, 300, 300, "rightArrow300x300.png", "goDialogueScreen"); //25 feb

		// create stove new stove
		System.out.println(Command);

		switch (Command) {

			// add raw kapao
			case "rawA":
				//createObject(3, 181, 630, 300, 300, "rawKapao300x300.png", "A");
				
				panA.addAll(bowl);
				clearContainer(bowl);
				break;
			case "rawB":
				//createObject(3, 690, 630, 300, 300, "rawKapao300x300.png", "B");

				panB.addAll(bowl);
				clearContainer(bowl);
				break;

			// add kapao suk
			case "padkapaoA":

				onKapaoA = true;
				createObject(3, 181, 630, 300, 300, "padkapao.png", "ready2packA");
				break;
			case "padkapaoB":

				onKapaoB = true;
				createObject(3, 690, 630, 300, 300, "padkapao.png", "ready2packB");
				break;

			// add burned
			case "BURNED_A":

				createObject(3, 181, 630, 300, 300, "burned.png", "");
				onKapaoA = false;
				// onStoveA = false;
				break;

			case "BURNED_B":

				createObject(3, 690, 630, 300, 300, "burned.png", "");
				onKapaoB = false;
				// onStoveB = false;
				break;

			// add kapao to box
			case "ready2packA":

				createObject(3, 200, 200, 300, 300, "kapaoBox300x300.png", "ready2closeA");
				//createObject(3, 181, 630, 300, 300, "stove300x300.png", "A");
				onBoxA = true;
				onKapaoA = false;
				onStoveA = false;

				boxA.addAll(panA);
				clearContainer(panA);
				break;

			case "ready2packB": //รอปิด มีกะเพราอยู่

				createObject(3, 200, 200, 300, 300, "kapaoBox300x300.png", "ready2closeB");
				//createObject(3, 690, 630, 300, 300, "stove300x300.png", "B");
				onBoxB = true;
				onKapaoB = false;
				onStoveB = false;

				boxB.addAll(panB);
				clearContainer(panB);
				break;

			// close box
			case "ready2closeA": //ปิดกล่อง

				createObject(3, 200, 200, 300, 300, "closeBox300x300.png", "ready2serveA");
				onCloseBoxA = true;
				onBoxA = false;
				break;

			case "ready2closeB": //ใส่เสร้จ

				createObject(3, 600, 200, 300, 300, "closeBox300x300.png", "ready2serveB");
				onCloseBoxB = true;
				onBoxB = false;
				break;

			// transfer box to serve
			case "ready2serveA": //กล่องเปล่า

				onCloseBoxA = false;
				createObject(3, 200, 200, 300, 300, "openBox300x300.png", "A");
				serve("transfer");
				//ปัก

				clearContainer(boxA);
				break;

			case "ready2serveB":

				onCloseBoxB = false;
				createObject(3, 600, 200, 300, 300, "openBox300x300.png", "B");
				serve("transfer");

				clearContainer(boxB);
				break;
		}

		// requirement
        if (onStoveA && !onKapaoA) {
        	
        	// Add the timer text to the panel
            bgPanel[3].add(timerTextA);
            bgPanel[3].setComponentZOrder(timerTextA, 0); // Bring to front

            // Refresh UI
            bgPanel[3].revalidate();
            bgPanel[3].repaint();
        	//createObject(3, 181, 630, 300, 300, "rawKapao300x300.png", "A");
        	//timerStoveA(remainingTimeA);
        } else if (onStoveA && onKapaoA) {
        	
        	// Add the timer text to the panel
            bgPanel[3].add(timerTextA);
            bgPanel[3].setComponentZOrder(timerTextA, 0); // Bring to front

            // Refresh UI
            bgPanel[3].revalidate();
            bgPanel[3].repaint();
        	createObject(3, 181, 630, 300, 300, "padkapao.png", "ready2packA"); 
        	//timerStoveA(remainingTimeA);
        } else {
        		
        	//createObject(3, 181, 630, 300, 300, "stove300x300.png", "A");
        }
        
        if (onStoveB && !onKapaoB) {
        	
        	// Add the timer text to the panel
            bgPanel[3].add(timerTextB);
            bgPanel[3].setComponentZOrder(timerTextB, 0); // Bring to front

            // Refresh UI
            bgPanel[3].revalidate();
            bgPanel[3].repaint();
        	//createObject(3, 690, 630, 300, 300, "rawKapao300x300.png", "B");
        	//timerStoveB(remainingTimeB);
        } else if (onStoveB && onKapaoB) {
        	
        	// Add the timer text to the panel
            bgPanel[3].add(timerTextB);
            bgPanel[3].setComponentZOrder(timerTextB, 0); // Bring to front

            // Refresh UI
            bgPanel[3].revalidate();
            bgPanel[3].repaint();
        	createObject(3, 690, 630, 300, 300, "padkapao.png", "ready2packB"); 
        	//timerStoveB(remainingTimeB);
        } else {
        		
        	//createObject(3, 690, 630, 300, 300, "stove300x300.png", "B");
        }
        
        if (onBoxA) {
        	
        	createObject(3, 200, 200, 300, 300, "kapaoBox300x300.png", "ready2closeA");
        } else if (onCloseBoxA){
        	
        	createObject(3, 200, 200, 300, 300,	 "closeBox300x300.png", "ready2serveA");
        } else {
        		
        	createObject(3, 200, 200, 300, 300, "openBox300x300.png", "A");
        }
        
        if (onBoxB) {
        	
        	createObject(3, 600, 200, 300, 300, "kapaoBox300x300.png", "ready2closeB");
        	
        } else if (onCloseBoxB){
        	
        	createObject(3, 600, 200, 300, 300,	 "closeBox300x300.png", "ready2serveB");
        } else {
        		
        	createObject(3, 600, 200, 300, 300, "openBox300x300.png", "B");
        }
        
        //add panel
        bgPanel[3].add(bgLabel[3]);
        window.add(bgPanel[3]);
        
        // refresh
        window.revalidate();
        window.repaint();
	}
   // prepare item
    public void serve(String Command) {
	
	// delete screen
	//window.remove(bgPanel[1]);
	
	// screen1
	//createBackground(1,"secondpage.png");
	
	createArrowButton(1, -20, 400, 300, 300, "leftArrow300x300.png", "goStoveScreen");
	//createPauseButton(1, 1500, 0, 300, 300, "pause300x300.png","");
	
	switch (Command) {
	
	// move box from stove 2 serve
	case "transfer" :
		
		// add first box
		if (!onTableA) {
			createObject(1, 200, 700, 300, 300, "closeBox300x300.png", "serveA");
			onTableA = true;
		} else {
		
			// add second box
			createObject(1, 600, 700, 300, 300, "closeBox300x300.png", "serveB");
			onTableB = true;
		}
		
		break;
	
		// need score function
		case "serveA" : 
			gm.score.calScoreA();
			onTableA = false;
			break;
			
		case "serveB" : 
			gm.score.calScoreB();
			onTableB = false;
			//resetScene();
			break;
		}
	
	// requirement
	if (onTableA) {
		createObject(1, 200, 700, 300, 300, "closeBox300x300.png", "serveA");
	} 
	if (onTableB) {
		createObject(1, 600, 700, 300, 300, "closeBox300x300.png", "serveB");
	}
	
	// add to window
	bgPanel[1].add(timerText);
	bgPanel[1].add(bgLabel[1]);
	window.add(bgPanel[1]);
	
	// refresh
	window.revalidate();
	window.repaint();
	
	if (gm.sChanger.getbgNow() == 1)
		bgPanel[1].setVisible(true);
	else
		bgPanel[1].setVisible(false);

	// Open the dialogue scene after serving the order
    if (!onTableA && !onTableB) {
		//();
		window.remove(bgPanel[1]);
		generateLoopScene();

		// window.add(bgPanel[1]);
	}

}

	public void timer(int time, String Command) {
			
		Timer timer = new Timer();
		
		timerText = new JTextArea();
		
		bgPanel[1].setLayout(null);

		// Set timer display properties
		timerText.setBounds(200, 40, 100, 30);
		timerText.setBackground(new Color(255, 240, 218, 255));
        timerText.setForeground(new Color(0, 0, 0));
		timerText.setOpaque(true); // Ensure visibility
		timerText.setEditable(false);
		timerText.setFont(new Font("Book Antiqua", Font.BOLD, 26));
		
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
			System.out.println(Command + ": " +count);
			count--;
			
			
			// functions
			if(count < 0 && Command.equals("OVERALL")) {
				
				// create game over scene
				generateGameOverScene();
				gm.sChanger.showGameOver();
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

	// เมธอดสำหรับหยุดเวลา
	public void pauseTimer() {
		isPaused = true;
	}

	// เมธอดสำหรับทำให้เวลาทำงานต่อ
	public void resumeTimer() {
		isPaused = false;
	}

	// เมธอดสำหรับรีเซ็ต Timer เมื่อเริ่มใหม่
	public void resetTimer(int newTime) {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		timer(newTime, "OVERALL");
	}

    
     // stove a timer
	public void timerStoveA(int time) {

        Timer timerA = new Timer();

        timerTextA = new JTextArea();

        bgPanel[3].setLayout(null);

        // Set timer display properties
        timerTextA.setBounds(200, 0, 50, 30);
        timerTextA.setForeground(Color.white);
        timerTextA.setBackground(Color.black);
        timerTextA.setOpaque(true); // Ensure visibility
        timerTextA.setEditable(false);
        timerTextA.setFont(new Font("Book Antiqua", Font.PLAIN, 26));

        // Add the timer text to the panel
        bgPanel[3].add(timerTextA);
        bgPanel[3].setComponentZOrder(timerTextA, 0); // Bring to front

		

       // Refresh UI
		bgPanel[3].revalidate();
		bgPanel[3].repaint();

		TimerTask taskA = new TimerTask() {

			int countA = time;

			@Override
			public void run() {
				// TODO aomsin added this
				if (countA > 8 && onStoveA) {
					moveIT('A');					
						
				}

					SwingUtilities.invokeLater(() -> {
					timerTextA.setText("" + countA); // Update text
					});

				// decrease time
				System.out.println("StoveA : " + countA);
				countA--;

				// no anything on stove
				if (!onStoveA) {

					timerA.cancel();
					// kapao ready 2 box
				} else if (countA == 8 && onStoveA) {

					// create padkapao
					remainingTimeA = countA;
					changeStove("padkapaoA");
					// overcook
				} else if (countA < 0 && onStoveA) {

					// create burned
					changeStove("BURNED_A");
					//gm.score.resetItem();
					timerA.cancel();
				}
				}
			};
			// delay
			timerA.scheduleAtFixedRate(taskA, 0, 1000);

			// requirement
			if (gm.sChanger.getbgNow() == 3)
				bgPanel[3].setVisible(true);
			else
				bgPanel[3].setVisible(false);
		}

	 // stove b timer
	public void timerStoveB(int time) {

        Timer timerB = new Timer();

        timerTextB = new JTextArea();

        bgPanel[3].setLayout(null);

        // Set timer display properties
        timerTextB.setBounds(600, 0, 50, 30);
        timerTextB.setForeground(Color.white);
        timerTextB.setBackground(Color.black);
        timerTextB.setOpaque(true); // Ensure visibility
        timerTextB.setEditable(false);
        timerTextB.setFont(new Font("Book Antiqua", Font.PLAIN, 26));

        // Add the timer text to the panel
        bgPanel[3].add(timerTextB);
        bgPanel[3].setComponentZOrder(timerTextB, 0); // Bring to front

        // Refresh UI
        bgPanel[3].revalidate();
        bgPanel[3].repaint();

        TimerTask taskB = new TimerTask() {

            int countB = time;

            @Override
            public void run() {
                // TODO aomsin added this
                if (countB > 8 && onStoveB) {
					moveIT('B');
                }

                SwingUtilities.invokeLater(() -> {
                    timerTextB.setText("" + countB); // Update text
                });
                // decrease time
                System.out.println("StoveB : " + countB);
                countB--;
    		
    		// no anything on stove
			if (!onStoveB) {

				timerB.cancel();
				// kapao ready 2 box
			} else if (countB == 8 && onStoveB) {

				// create padkapao
				remainingTimeB = countB;
				changeStove("padkapaoB");
				// overcook
			} else if (countB < 0 && onStoveB) {

				// create burned
				changeStove("BURNED_B");
				//gm.score.resetItem();
				timerB.cancel();
			}
		}
	};
    	 // delay
		timerB.scheduleAtFixedRate(taskB, 0, 1000);

		 // requirement
		if (gm.sChanger.getbgNow() == 3)
			bgPanel[3].setVisible(true);
		else
			bgPanel[3].setVisible(false);
	}
 
	public void reset() {
 
		System.out.println("restart!");
		gm.score.resetScore();
 
		 // delete everything
		window.remove(window);
 
		 // create title scene
		createMainField();
		generateTitle();
		window.setVisible(true);

		 // Refresh UI
		window.revalidate();
		window.repaint();
	}
	public void resetLoop() {
 
		System.out.println("restart! Loop");
 
		 // delete everything
		window.remove(window);
 
		generateDialogueScene(); // สร้างหน้าจอสั่งข้าว
        generateSelectIngScene(); // สร้างหน้าจอเลือกวัตถุดิบ
        generateStoveScene(); // สร้างหน้าจอเตา
        generateExtraScene(); // สร้างหน้าจอห่อข้าว
		window.setVisible(true);

		 // Refresh UI
		window.revalidate();
		window.repaint();
	}
}
