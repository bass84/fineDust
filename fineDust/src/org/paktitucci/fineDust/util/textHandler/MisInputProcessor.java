package org.paktitucci.fineDust.util.textHandler;

import org.telegram.telegrambots.api.objects.Message;

public class MisInputProcessor implements TextProcessable{

	@Override
	public String processText(Message message) {
		return message.getChat().getFirstName() + " "
                + message.getChat().getLastName() + "님 잘못 입력하셨습니다.\n아래의 버튼 메뉴에서 원하는 내용을 선택해주세요.";
	}

}
