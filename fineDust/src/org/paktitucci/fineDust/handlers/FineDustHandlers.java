package org.paktitucci.fineDust.handlers;

import org.paktitucci.fineDust.config.Config;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class FineDustHandlers extends TelegramLongPollingBot{

	@Override
	public String getBotUsername() {
		return Config.BOT_USER_NAME;
	}

	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		if (update.hasMessage() && update.getMessage().hasText()) {
	        SendMessage sendMessage = new SendMessage() 
	                .setChatId(update.getMessage().getChatId())
	                .setText(update.getMessage().getText());
	        try {
	            execute(sendMessage); 
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
	    }
	}

	@Override
	public String getBotToken() {
		return Config.BOT_TOKEN;
	}

}
