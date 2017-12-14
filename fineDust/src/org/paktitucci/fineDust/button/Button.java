package org.paktitucci.fineDust.button;

import java.util.ArrayList;
import java.util.List;

import org.paktitucci.fineDust.properties.ButtonType;
import org.paktitucci.fineDust.properties.InitButtonInfo;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

public class Button {
	
	ReplyKeyboardMarkup keyboardMarkup = null;

	public Button(ButtonType buttonType) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        this.keyboardMarkup = new ReplyKeyboardMarkup();
        
        switch(buttonType) {
        	case INIT : 
        		KeyboardRow row = new KeyboardRow();
                row.add(InitButtonInfo.INIT_BUTTON_1.getName());
                keyboard.add(row);
                
                row = new KeyboardRow();
                row.add(InitButtonInfo.INIT_BUTTON_2.getName());
                keyboard.add(row);
                
                row = new KeyboardRow();
                row.add(InitButtonInfo.INIT_BUTTON_3.getName());
                keyboard.add(row);
                
                keyboardMarkup.setKeyboard(keyboard);
                break;
            default :
            	break;
        }
        
        
    }
	
	public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
		return this.keyboardMarkup;
	}
	
}
