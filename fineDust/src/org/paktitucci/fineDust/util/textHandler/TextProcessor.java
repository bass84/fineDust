package org.paktitucci.fineDust.util.textHandler;

import java.util.ArrayList;
import java.util.List;

import org.paktitucci.fineDust.report.Report;
import org.paktitucci.fineDust.user.User;
import org.telegram.telegrambots.api.objects.Message;

public class TextProcessor implements TextProcessable{
	
	private static List<User> userList = new ArrayList<User>();
	private String fineDustInfo;
	private String returnText;
	private User user;
	
	public TextProcessor() {
		this.fineDustInfo = "";
		this.returnText = "";
	}

	@Override
	public String processText(Message message) {
		
		if(message.getText().startsWith("#")) {
			boolean isLocationNameExist = false;
			
			if(!isHaveUser(userList, message.getChatId())) {
				this.user = new User(message.getChat().getId(), message.getChat().getFirstName(), message.getChat().getLastName());
				user.addLocationName(message.getText().substring(1).trim());
				userList.add(user);
			}else {
				this.user = this.getUser(message.getChatId());
				isLocationNameExist = user.addLocationName(message.getText().substring(1).trim());
			}
			
			
			String userName = message.getChat().getFirstName() + " " + message.getChat().getLastName(); 
			String locationName = message.getText().substring(1);
			this.fineDustInfo = Report.getfineDustInfo(locationName.trim());
			
			if(!isLocationNameExist) {
				this.returnText = userName + "님 " + locationName + "을 등록하였습니다.\n\n" + this.fineDustInfo;
			}else {
				this.returnText = userName + "님! 이미 등록하신 지역입니다.";
			}
			
		}else if(message.getText().equals("/start")) {
			this.returnText = message.getChat().getFirstName() + " " + message.getChat().getLastName() + "님 미세먼지 알림봇에 들어오신 걸 환영합니다.\n아래의 버튼 메뉴에서 원하는 내용을 선택해주세요.";
		}
		
		
		return this.returnText;
	}
	
	public boolean isHaveUser(List<User> userList, Long id) {
		for(User user : userList) {
			if(user.getId().equals(id)) return true;
		}
		
		return false;
	}
	
	public User getUser(Long id) {
		for(User user : userList) {
			if(user.getId().equals(id)) return user;
		}
		
		return null;
	}

}
