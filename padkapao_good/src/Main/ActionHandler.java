
package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionHandler implements ActionListener{

	GameManager gm;
	
	public ActionHandler(GameManager gm) {
		
		this.gm = gm;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)  {
		
		String yourChoice = e.getActionCommand();
		
		switch(yourChoice) {
		
		//add item to stove
		
		//change scene
		case "start" : gm.sChanger.showScreen(); break;
		case "goScreen1": gm.sChanger.showScreen1(); break;
		case "goScreen2": gm.sChanger.showScreen2(); break;
		}
	}
}

