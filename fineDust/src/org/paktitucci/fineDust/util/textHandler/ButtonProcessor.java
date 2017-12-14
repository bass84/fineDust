package org.paktitucci.fineDust.util.textHandler;

import org.paktitucci.fineDust.properties.InitButtonInfo;
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
		
		if(message.getText().equals(InitButtonInfo.INIT_BUTTON_1.getName())) {
			this.returnText = this.userName + "님 님 등록하시고자 하는 지역명을 \'# + 동\' 으로 입력해주세요.\n예) : \'#사당동\'";
		}
		
		
		return this.returnText;
	}

}
