package Main;

public class SceneChanger {

	GameManager gm;
	boolean start=false;
	
	public SceneChanger(GameManager gm)  {
		
		this.gm = gm;
	}
	
	public void showScreen() {

		gm.ui.bgPanel[0].setVisible(false);
	}
	
	public void showScreen1() {

		gm.ui.bgPanel[1].setVisible(true);
		gm.ui.bgPanel[2].setVisible(false);
	}
	
	public void showScreen2() {
		
		gm.ui.bgPanel[1].setVisible(false);
		gm.ui.bgPanel[2].setVisible(true);
	}
}
