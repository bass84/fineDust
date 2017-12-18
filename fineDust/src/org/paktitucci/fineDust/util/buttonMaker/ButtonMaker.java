package org.paktitucci.fineDust.util.buttonMaker;

import org.paktitucci.fineDust.button.Button;
import org.paktitucci.fineDust.handlers.FineDustHandlers;
import org.paktitucci.fineDust.properties.ButtonType;

public class ButtonMaker {
	
	private Button currentButton;
	
	public Button getCurrentButton(String message) {
		if(FineDustHandlers.getCurrentButtonInfo() == null && message.equals("/start")) {
			this.currentButton = ButtonList.getButton(ButtonType.INIT);
			this.currentButton.getReplyKeyboardMarkup().setOneTimeKeyboard(true);
		}else {
			this.currentButton = null;
		}
		return this.currentButton;
		
	}
}
