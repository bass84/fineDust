package org.paktitucci.fineDust.util.textHandler;

import org.paktitucci.fineDust.handlers.FineDustHandlers;
import org.paktitucci.fineDust.properties.ButtonInfo;
import org.telegram.telegrambots.api.objects.Message;

public class ButtonProcessor implements TextProcessable{
	
	private String returnText;
	private String userName;
	
	public ButtonProcessor() {
		this.returnText = "";
		this.userName = "";
	}
	
	
	@Override
	public String processText(Message message) {
		this.userName = message.getChat().getFirstName() + " " + message.getChat().getLastName();
		
		if(message.getText().equals(ButtonInfo.INIT_BUTTON_1.getName())) {
			FineDustHandlers.setCurrentButtonInfo(ButtonInfo.INIT_BUTTON_1);
			this.returnText = this.userName + "님 님 등록하시고자 하는 지역명을 \'동\' 으로 입력해주세요.\n예) : \'사당동\'";
		}else {
			this.returnText = this.userName + "님 잘못 입력하셨습니다.\n아래의 버튼 메뉴에서 원하는 내용을 선택해주세요.";
		}
		
		
		return this.returnText;
	}

}
