package org.paktitucci.fineDust.util.buttonMaker;

import org.paktitucci.fineDust.button.Button;
import org.paktitucci.fineDust.properties.ButtonType;

public class ButtonList {

	private static Button initButton = new Button(ButtonType.INIT);
	
	public static Button getButton(ButtonType buttonType) {
		switch(buttonType) {
			case INIT : 
				return initButton;
		}
		
		return null;
	}
}
