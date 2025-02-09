package Main;

public class SceneChanger {

	GameManager gm;
	boolean start=false;
	
	public SceneChanger(GameManager gm)  {this.gm = gm;}
	
	// show dialouge screen
	public void showDialogueScreen() {

		gm.ui.bgPanel[0].setVisible(false);
		gm.ui.bgPanel[1].setVisible(true);
		gm.ui.bgPanel[2].setVisible(false);
		gm.ui.bgPanel[3].setVisible(false);
		gm.ui.bgPanel[4].setVisible(false);
	}
	// show select ingredient screen
	public void showSelectIngScreen() {

		gm.ui.bgPanel[0].setVisible(false);
		gm.ui.bgPanel[1].setVisible(false);
		gm.ui.bgPanel[2].setVisible(true);
		gm.ui.bgPanel[3].setVisible(false);
		gm.ui.bgPanel[4].setVisible(false);
	}
	// show stove screen
	public void showStoveScreen() {

		gm.ui.bgPanel[0].setVisible(false);
		gm.ui.bgPanel[1].setVisible(false);
		gm.ui.bgPanel[2].setVisible(false);
		gm.ui.bgPanel[3].setVisible(true);
		gm.ui.bgPanel[4].setVisible(false);
	}
	// show package screen
	public void showPackageScreen() {
		
		gm.ui.bgPanel[0].setVisible(false);
		gm.ui.bgPanel[1].setVisible(false);
		gm.ui.bgPanel[2].setVisible(false);
		gm.ui.bgPanel[3].setVisible(false);
		gm.ui.bgPanel[4].setVisible(true);
	}
}