package Controller;

import java.awt.event.KeyEvent;

import View.GUI;

public class Controller {

	public Controller(String string, KeyEvent e) {
		
		int code = e.getKeyCode();
		
		switch(string) {
			case "Key Press":
				switch(KeyEvent.getKeyText(code)) {
					// Jump
					case "W":
						if(GUI.game.canJump())
							GUI.game.getSprite().setJumping(true);
						GUI.game.setJumpButtonReleased(false);
						break;
					// Move Left
					case "A":
						GUI.game.getSprite().setMovingLeft(true);
						break;
					// Move Right
					case "D":
						GUI.game.getSprite().setMovingRight(true);
						break;
				}
			break;
			
			case "Key Release":
				switch(KeyEvent.getKeyText(code)) {
					case "W":
						GUI.game.setJumpButtonReleased(true);
						break;
					case "A":
						GUI.game.getSprite().setMovingLeft(false);
						break;
					case "D":
						GUI.game.getSprite().setMovingRight(false);
						break;
				}
				break;
				
					
		}
	}
}
