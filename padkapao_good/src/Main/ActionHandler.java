
package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionHandler implements ActionListener{

	GameManager gm;
	public ActionHandler(GameManager gm) {this.gm = gm;}
	
	
	@Override
	public void actionPerformed(ActionEvent e)  {
		
		// recieve command
		String yourChoice = e.getActionCommand();
		
		// select case
		switch(yourChoice) {
		
		//add item to stove
		
		//change scene
		case "start" : gm.sChanger.showDialogueScreen(); break;
		case "goDialogueScreen":gm.sChanger.showDialogueScreen(); break;
		case "goSelectIngScreen":gm.sChanger.showSelectIngScreen(); break;
		case "goStoveScreen":gm.sChanger.showStoveScreen(); break;
		case "goPackageScreen":gm.sChanger.showPackageScreen(); break;
		}
	}
}