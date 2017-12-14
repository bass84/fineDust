package org.paktitucci.fineDust.util.textHandler;

import org.paktitucci.fineDust.report.Report;
import org.telegram.telegrambots.api.objects.Message;

public class TextProcessor implements TextProcessable{
	
	private String locationName;
	private String fineDustInfo;
	private String returnText;
	private String userName;
	
	public TextProcessor() {
		this.locationName = "";
		this.fineDustInfo = "";
		this.returnText = "";
		this.userName = "";
	}

	@Override
	public String processText(Message message) {
		
		if(message.getText().startsWith("#")) {
			this.userName = message.getChat().getFirstName() + " " + message.getChat().getLastName(); 
			this.locationName = message.getText().substring(1);
			this.fineDustInfo = Report.getfineDustInfo(this.locationName.trim());
			this.returnText = userName + "님 " + this.locationName + "을 등록하였습니다.\n\n" + this.fineDustInfo;
		}else if(message.getText().equals("/start")) {
			this.returnText = this.userName + "님 미세먼지 알림봇에 들어오신 걸 환영합니다.\n아래의 버튼 메뉴에서 원하는 내용을 선택해주세요.";
		}
		
		
		return this.returnText;
	}

}
