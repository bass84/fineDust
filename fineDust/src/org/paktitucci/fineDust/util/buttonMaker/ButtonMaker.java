package org.paktitucci.fineDust.util.buttonMaker;

import org.paktitucci.fineDust.button.Button;
import org.paktitucci.fineDust.properties.ButtonType;

public class ButtonMaker {
	
	Button currentButton;
	
	public Button getCurrentButton(String message) {
		switch(message) {
			case "/start" :
				this.currentButton = ButtonList.getButton(ButtonType.INIT);
				this.currentButton.getReplyKeyboardMarkup().setOneTimeKeyboard(true);
				break;
			default :
				return null;
		}
		return this.currentButton;
		
	}
}
