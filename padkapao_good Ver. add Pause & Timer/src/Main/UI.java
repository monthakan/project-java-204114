package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.util.Timer;

public class UI {
    
    GameManager gm;
    
    JFrame window;
    public JTextArea timerText;
    public JPanel bgPanel[] = new JPanel[7];
    public JLabel bgLabel[] = new JLabel[7];
    
    String stove = "";
    boolean start = false, pause = false;
    boolean isKapao = false, onStove = false;
    boolean voice;
    
    private int remainingTime;
    
    
    // main ui
    public UI(GameManager gm) {
        
        this.gm = gm;
        
        //title scene
        createMainField();
        generateTitle();
            
        window.setVisible(true);
    }
    
    // create game resolution
    public void createMainField(){
        
        window = new JFrame();
        window.setSize(1920,1080); // resolution
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
    }
    
    // create background
    public void createBackground(int bgNum, String bgFileName){
    
        bgPanel[bgNum] = new JPanel();
        
        // panel setting
        bgPanel[bgNum].setBounds(0,0,1920,1080); // x, y, width, height
        bgPanel[bgNum].setBackground(Color.gray);
        bgPanel[bgNum].setLayout(null);
        
        // label setting
        bgLabel[bgNum] = new JLabel();
        bgLabel[bgNum].setBounds(0, 0, 1920,1080);
        
        // add background image
        ImageIcon bgIcon = new ImageIcon(getClass().getClassLoader().getResource(bgFileName));
        bgLabel[bgNum].setIcon(bgIcon);
    }
    
    // create object
    public void createObject(int bgNum, int objx, int objy, int objWidth, int objHeight, String objFileName, String Command){
    	
    	// create object
        JLabel objectLabel = new JLabel();
        
        // object setting
        objectLabel.setBounds(objx, objy, objWidth, objHeight); // x, y, width, height
        ImageIcon objectIcon = new ImageIcon(getClass().getClassLoader().getResource(objFileName));
        objectLabel.setIcon(objectIcon);
        
        // object actions
        objectLabel.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {}
			// left click
			public void mousePressed(MouseEvent e) {
				
				if(SwingUtilities.isRightMouseButton(e) && Command.equals("stove")) {
				
					
				}
				
				if(SwingUtilities.isLeftMouseButton(e) && !Command.equals("stove")) {
					
					System.out.println("Left Click!");
					// clear kapao with rice and add kapao with rice in package
					if (Command == "insertKapao") {
						
						window.remove(bgPanel[3]);
						generateStoveScene();
						
						// refresh
				        window.revalidate();
				        window.repaint();
						
						packKapao(Command);
						
					// pack kapao
					}else if (Command == "ready2pack"){
						
						System.out.println("PACK ENTER");
						packKapao(Command);
						
					// change stove image	
					} else {
						
						onStove = true;
						changeStove(Command);
						timer(3, "STOVE");
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
    
    // create arrow for change page
    public void createArrowButton(int bgNum, int x, int y, int Width, int Height, String arrowFileName, String Command) {
    	
    	//add arrow image
    	ImageIcon arrowIcon = new ImageIcon(getClass().getClassLoader().getResource(arrowFileName));
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
    
    // create start button
    public void createStartButton(int bgNum, int x, int y, int Width, int Height, String startFileName, String Command) {
    	
    	// add start image
    	ImageIcon startIcon = new ImageIcon(getClass().getClassLoader().getResource(startFileName));
    	
    	// start setting
    	JButton startButton = new JButton();
    	startButton.setBounds(x, y, Width, Height);
    	startButton.setBackground(null);
    	startButton.setContentAreaFilled(false);
    	startButton.setFocusPainted(false);
    	startButton.setIcon(startIcon);
    	startButton.addActionListener(gm.aHandler);
    	startButton.setActionCommand(Command);
    	startButton.setBorderPainted(false);

    	
    	// Start actions
    	startButton.addMouseListener(new MouseListener() {
    	public void mouseClicked(MouseEvent e) {}
		// left click
		public void mousePressed(MouseEvent e) {
			
			if(SwingUtilities.isLeftMouseButton(e)) {
				
				//gameplay scenes
		        generateDialogueScene();
		        generateSelectIngScene();
		        generateStoveScene();
		        generatePackageScene();
			}
		}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
    	});
    	
    	// add panel
    	bgPanel[bgNum].add(startButton);
    }
	
    
    // voice option[NOT READY]
    public void createVoiceButton(int bgNum, int x, int y, int Width, int Height, String voiceFileName, String Command) {
    	
    	// create object
        JLabel voiceLabel = new JLabel();
        
        // object setting
        voiceLabel.setBounds(x, y, Width, Height); // x, y, width, height
        ImageIcon voiceIcon = new ImageIcon(getClass().getClassLoader().getResource(voiceFileName));
        voiceLabel.setIcon(voiceIcon);
        
        // object actions
        voiceLabel.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {}
			// left click
			public void mousePressed(MouseEvent e) {
				
				if(SwingUtilities.isLeftMouseButton(e)) {
					
					System.out.println("VOICE CHANGE");
					changeVoice(voice);
				}
			}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
        });
        
        //add panel
        bgPanel[bgNum].add(voiceLabel);
    }
    
 // pause option[NOT READY]
    public void createPauseButton(int bgNum, int x, int y, int Width, int Height, String voiceFileName, String Command) {
    	
    	// create object
        JLabel pauseLabel = new JLabel();
        
        // object setting
        pauseLabel.setBounds(x, y, Width, Height); // x, y, width, height
        ImageIcon pauseIcon = new ImageIcon(getClass().getClassLoader().getResource(voiceFileName));
        pauseLabel.setIcon(pauseIcon);
        
        // object actions
        pauseLabel.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {}
			// left click
			public void mousePressed(MouseEvent e) {
				
				if(SwingUtilities.isLeftMouseButton(e)) {
					
					int bgNow = gm.sChanger.getbgNow(); 
					System.out.println("PAUSE" + bgNow);
					if (pause == false) {
						
						pause = true;
						generatePauseScene();
						bgPanel[bgNow].setVisible(false);
					} else {
						
						pause = false;
						window.remove(bgPanel[6]);
						timer(remainingTime, "Continous");
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
    
    // ------------------------------------------------------------------------------
    // generate window
    
	// generate menu
    public void generateTitle() {

    	// screen0
    	createBackground(0,"menubg1920x1080.png");
    	createStartButton(0, 800, 400, 300, 100, "start1_400x210.png", "start");
    	createVoiceButton(0, 0, 0, 100, 100, "open90x80.png", "");
    	
    	// add to window
    	bgPanel[0].add(bgLabel[0]);
		window.add(bgPanel[0]);
    }
    
    //generate select dialogue scene
    public void generateDialogueScene() {
    	
    	// screen1
    	createBackground(1,"menubg1920x1080.png");
    	
    	createArrowButton(1, 1620, 400, 300, 300, "rightArrow300x300.png", "goSelectIngScreen");
		createPauseButton(1, 1500, 0, 300, 300, "pause300x300.png","");
    	
    	// add to window
    	bgPanel[1].add(bgLabel[1]);
    	window.add(bgPanel[1]);
    	
    	// Start timer
        timer(300, "OVERALL");
    }
    
    //generate select ingredient scene
    public void generateSelectIngScene() {
    	
    	//screen2
    	createBackground(2,"menubg1920x1080.png");
    	createPauseButton(2, 1500, 0, 300, 300, "pause300x300.png","");
    	
    	// add ingredients
    	createObject(2, 200, 300, 300, 300, "kapao300x300.png", "addkapao");
    	createObject(2, 550, 300, 300, 300, "micedpork 300x300.png", "addmpork");
    	
    	createArrowButton(2, 0, 400, 300, 300, "leftArrow300x300.png", "goDialogueScreen");
    	createArrowButton(2, 1620, 400, 300, 300, "rightArrow300x300.png", "goStoveScreen");
    	
    	// add to window
    	bgPanel[2].add(bgLabel[2]);
    	window.add(bgPanel[2]);
    }
    
    // generate stove scenes
    public void generateStoveScene() {
    	
    	// screen3
    	createBackground(3,"menubg1920x1080.png");
    	createPauseButton(3, 1500, 0, 300, 300, "pause300x300.png","");
    	
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
    	createPauseButton(4, 1500, 0, 300, 300, "pause300x300.png","");
    	
    	createArrowButton(4, 0, 400, 300, 300, "leftArrow300x300.png", "goStoveScreen");
    	
    	// add to window
    	bgPanel[4].add(bgLabel[4]);
    	window.add(bgPanel[4]);
    }
    
    public void generateGameOverScene() {
    	
    	createBackground(5,"gameoverbg1920x1080.png");
    	
    	createArrowButton(5, 960, 500, 300, 300, "restart300x300.png", "restart");
    	
    	bgPanel[5].add(bgLabel[5]);
    	window.add(bgPanel[5]);
    }
    
    public void generatePauseScene() {
    	
    	createBackground(6,"gameoverbg1920x1080.png");
    	createPauseButton(6, 1500, 0, 300, 300, "pause300x300.png","");
    	
    	bgPanel[6].add(bgLabel[6]);
    	window.add(bgPanel[6]);
    }
    
    // ---------------------------------------------------------------------------------------
    // fuction
    
    // change voice button [bug]
    public void changeVoice(boolean voice) {
    	
    	// delete
    	window.remove(bgPanel[0]);
    	
    	// create new
    	createBackground(0,"menubg1920x1080.png");
    	createStartButton(0, 800, 400, 300, 100, "start1_400x210.png", "start");
    	System.out.println(voice);
    	if(voice == false) {

    		createVoiceButton(0, 0, 0, 100, 100, "open90x80.png","");
			voice = true;
		} else {
			
			createVoiceButton(0, 0, 0, 100, 100, "mute60x80.png","");
			voice = false;
			System.out.println(voice);
		}
    	
    	// add to window
    	bgPanel[0].add(bgLabel[0]);
    	window.add(bgPanel[0]);
    	
    	// refresh
        window.revalidate();
        window.repaint();
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
    
    // pack Kapao image
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