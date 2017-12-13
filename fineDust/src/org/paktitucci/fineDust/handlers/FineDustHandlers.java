package org.paktitucci.fineDust.handlers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.paktitucci.fineDust.button.Button;
import org.paktitucci.fineDust.config.Config;
import org.paktitucci.fineDust.properties.ButtonType;
import org.paktitucci.fineDust.properties.InitButtonInfo;
import org.paktitucci.fineDust.report.Report;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class FineDustHandlers extends TelegramLongPollingBot{

	private static Button initButton;
	
	private static Button getInitButton() {
		if(initButton == null) initButton = new Button(ButtonType.INIT);
		return initButton;
	}
	
	@Override
	public String getBotUsername() {
		return Config.BOT_USER_NAME;
	}

	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		String userName = update.getMessage().getChat().getFirstName() + " " + update.getMessage().getChat().getLastName(); 
		String introduceText = "";
		String locationName = "";
		String result = "";
		
		if(update.hasMessage() && update.getMessage().hasText()) {
			SendMessage sendMessage = new SendMessage() 
	                .setChatId(update.getMessage().getChatId());
	        
			if(message.getText().startsWith("#")) {
				locationName = message.getText().substring(1);
				result = Report.getfineDustInfo(locationName);
				introduceText = userName + "님 " + locationName + "을 등록하였습니다.\n\n";
				introduceText += result;
			}
			else {
				if(message.getText().equals(InitButtonInfo.INIT_BUTTON_START.getName())) {
					introduceText = userName + "님 미세먼지 알림봇에 들어오신 걸 환영합니다.\n아래의 버튼 메뉴에서 원하는 내용을 선택해주세요.";
					FineDustHandlers.initButton = FineDustHandlers.getInitButton();
					sendMessage.setReplyMarkup(FineDustHandlers.initButton.getReplyKeyboardMarkup());
					FineDustHandlers.initButton.getReplyKeyboardMarkup().setOneTimeKeyboard(true);
				}else if(message.getText().equals(InitButtonInfo.INIT_BUTTON_1.getName())) {
					introduceText = userName + "님 님 등록하시고자 하는 지역명을 \'# + 동\' 으로 입력해주세요.\n예) : \'#사당동\'";
				}
			}
			
			sendMessage.setText(introduceText);
			
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
