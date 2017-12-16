package org.paktitucci.fineDust.handlers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.json.JSONObject;
import org.paktitucci.fineDust.button.Button;
import org.paktitucci.fineDust.config.Config;
import org.paktitucci.fineDust.model.FineDustInfo;
import org.paktitucci.fineDust.report.Report;
import org.paktitucci.fineDust.user.User;
import org.paktitucci.fineDust.util.buttonMaker.ButtonMaker;
import org.paktitucci.fineDust.util.textHandler.ResponseTextMaker;
import org.paktitucci.fineDust.util.textHandler.TextProcessor;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public class FineDustHandlers extends TelegramLongPollingBot{

	private Button currentButton;
	private static ResponseTextMaker responseTextMaker = new ResponseTextMaker();
	private static ButtonMaker buttonMaker = new ButtonMaker();
	private static boolean isStart;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	/*응답 내용 만드는 객체 생성*/
	private static ResponseTextMaker getResponseTextMaker() {
		if(responseTextMaker == null) responseTextMaker = new ResponseTextMaker();
		return responseTextMaker;
	}
	
	/*버튼 만드는 객체 생성*/
	private static ButtonMaker getButtonMaker() {
		if(buttonMaker == null) buttonMaker = new ButtonMaker();
		return buttonMaker;
	}
	
	
	
	@Override
	public void onUpdateReceived(Update update) {

		Message message = update.getMessage();
		String responseText = "";
		System.out.println(update.getMessage().getChat().toString());
		if(update.hasMessage() && update.getMessage().hasText()) {
			SendMessage sendMessage = new SendMessage()
	                .setChatId(update.getMessage().getChatId());
			
			/*응답 내용 만드는 객체 얻는다. 객체를 하나만 생성했기 때문에 매번 new하지 않는다.*/
			responseTextMaker = getResponseTextMaker();
			/*버튼 만드는 객체 얻는다. 객체를 하나만 생성했기 때문에 매번 new하지 않는다.*/
			buttonMaker = getButtonMaker();
			/*응답 내용 얻어온다.*/
			responseText = responseTextMaker.getResponseText(message);
			/*버튼 얻어온다.*/
			this.currentButton = buttonMaker.getCurrentButton(message.getText());
			/*버튼이 있다면 sendMessage에 set한다.*/
			if(this.currentButton != null) sendMessage.setReplyMarkup(this.currentButton.getReplyKeyboardMarkup());
			/*응답 내용을 sendMessage에 set한다.*/
			sendMessage.setText(responseText);
			
	        try {
	        	/*응답 내용을 클리이언트에 보낸다.*/
	            execute(sendMessage); 
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
	    }
	    if(!isStart) {
			isStart = true;
			autoAlertDustInfo();

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




	public void autoAlertDustInfo() {
		Calendar cal = Calendar.getInstance();
		int startMinute = this.getStartMinute(cal.get(Calendar.MINUTE));

		final Runnable beeper = new Runnable() {
			public void run() {
				JSONObject currentFineDust = null;
				String returnText = "";

				for(User user : TextProcessor.getUserList()) {
					for(FineDustInfo userFineDust : user.getFineDustInfoList()) {
						currentFineDust =  Report.getFineDustInfo(userFineDust.getLocationName(), user.getId());
						String currentDustGrade = currentFineDust.getString("pm10Grade1h");
						String userDustGrade = userFineDust.getFineDustStatus();


						//if(currentDustGrade != null && userDustGrade != null
						//		&& !currentDustGrade.equals("") && !userDustGrade.equals("") && !currentDustGrade.equals(userDustGrade)) {
							returnText = "등록하신 지역 " + userFineDust.getLocationName() + "의 미세먼지 지수가 \'" + Report.getGrade(userDustGrade) + "\'에서 \'"
									+ Report.getGrade(currentDustGrade) + "\'으로 변경되었습니다.\n";
							userFineDust.setFineDustStatus(currentDustGrade);

							SendMessage sendMessage = new SendMessage()
									.setChatId(user.getId()).setText(returnText);

							try {
	        					/*응답 내용을 클리이언트에 보낸다.*/
								execute(sendMessage);
							} catch (TelegramApiException e) {
								e.printStackTrace();
							}
						//}
					}
				}
			}
		};

		this.scheduler.scheduleAtFixedRate(beeper, startMinute, 20, SECONDS);
	}





	private int getStartMinute(int currentMinute) {
		int scheduleStartMinute = 35;

		int startMinute = scheduleStartMinute;

		if((scheduleStartMinute - currentMinute) < 0) {
			startMinute +=  60;
		}
		return startMinute;
	}

}
