package org.paktitucci.fineDust.handlers;

import org.paktitucci.fineDust.button.Button;
import org.paktitucci.fineDust.config.Config;
import org.paktitucci.fineDust.properties.ButtonType;
import org.paktitucci.fineDust.util.buttonMaker.ButtonMaker;
import org.paktitucci.fineDust.util.textHandler.ResponseTextMaker;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class FineDustHandlers extends TelegramLongPollingBot{

	private Button currentButton;
	private static ResponseTextMaker responseTextMaker;
	private static ButtonMaker buttonMaker;
	
	private static ResponseTextMaker getResponseTextMaker() {
		if(responseTextMaker == null) responseTextMaker = new ResponseTextMaker();
		return responseTextMaker;
	}
	
	private static ButtonMaker getButtonMaker() {
		if(buttonMaker == null) buttonMaker = new ButtonMaker();
		return buttonMaker;
	}
	
	
	
	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		String introduceText = "";
		
		if(update.hasMessage() && update.getMessage().hasText()) {
			SendMessage sendMessage = new SendMessage()
	                .setChatId(update.getMessage().getChatId());
			
			responseTextMaker = getResponseTextMaker();
			buttonMaker = getButtonMaker();
			introduceText = responseTextMaker.getReturnText(message);
			this.currentButton = buttonMaker.getCurrentButton(message.getText());
			if(this.currentButton != null) sendMessage.setReplyMarkup(this.currentButton.getReplyKeyboardMarkup());
			sendMessage.setText(introduceText);
			
	        try {
	            execute(sendMessage); 
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	@Override
	public String getBotUsername() {
		return Config.BOT_USER_NAME;
	}

	@Override
	public String getBotToken() {
		return Config.BOT_TOKEN;
	}

}
