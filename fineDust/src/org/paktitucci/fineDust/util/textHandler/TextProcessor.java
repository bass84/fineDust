package org.paktitucci.fineDust.util.textHandler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.paktitucci.fineDust.model.FineDustInfo;
import org.paktitucci.fineDust.report.Report;
import org.paktitucci.fineDust.user.User;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;


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

			if(!isHaveUser(message.getChatId())) {
				this.user = new User(message.getChat().getId(), message.getChat().getFirstName(), message.getChat().getLastName());
				user.addLocationName(message.getText().substring(1).trim());
				userList.add(user);
			}else {
				this.user = this.getUser(message.getChatId());
				isLocationNameExist = user.addLocationName(message.getText().substring(1).trim());
			}


			String userName = message.getChat().getFirstName() + " " + message.getChat().getLastName();
			String locationName = message.getText().substring(1);
			this.fineDustInfo = Report.getFineDustInfoResult(locationName.trim(), message.getChatId());

			if(!isLocationNameExist) {
				this.returnText = userName + "님 " + locationName + "을 등록하였습니다.\n\n" + this.fineDustInfo;
			}else {
				this.returnText = userName + "님! 이미 등록하신 지역입니다.\n\n" + this.fineDustInfo;
			}

		}else if(message.getText().equals("/start")) {
			this.returnText = message.getChat().getFirstName() + " " + message.getChat().getLastName() + "님 미세먼지 알림봇에 들어오신 걸 환영합니다.\n아래의 버튼 메뉴에서 원하는 내용을 선택해주세요.";
		}


		return this.returnText;
	}




	/*public static String processText() {
		JSONObject currentFineDust = null;
		String returnText = "";

		for(User user : userList) {
			for(FineDustInfo userFineDust : user.getFineDustInfoList()) {
				currentFineDust =  Report.getFineDustInfo(userFineDust.getLocationName(), user.getId());
				String currentDustGrade = currentFineDust.getString("pm10Grade1h");
				String userDustGrade = userFineDust.getFineDustStatus();

				if(currentDustGrade != null && userDustGrade != null
						&& !currentDustGrade.equals("") && !userDustGrade.equals("") && !currentDustGrade.equals(userDustGrade)) {
					returnText += "등록하신 지역 " + userFineDust.getLocationName() + "의 미세먼지 지수가 \'" + userDustGrade + "\'에서 \'"
							+ currentDustGrade + "\'으로 변경되었습니다.\n\n";
					userFineDust.setFineDustStatus(currentDustGrade);

				}
			}
		}
		return returnText;
	}*/

	
	public static boolean isHaveUser(Long id) {
		for(User user : userList) {
			if(user.getId().equals(id)) return true;
		}
		
		return false;
	}

	public static int getFineDustInfoIndex(int userIndex, String locationName) {

		for(int i = 0; i < userList.get(userIndex).getFineDustInfoList().size(); i++) {
			if(userList.get(userIndex).getFineDustInfoList().get(i).getLocationName().equals(locationName)) return i;
		}

		return -1;
	}


	public static int getUserIndex(Long id) {
		for(int i = 0; i < userList.size(); i++) {
			if(userList.get(i).getId().equals(id)) return i;
		}
		return -1;
	}
	
	public User getUser(Long id) {
		for(User user : userList) {
			if(user.getId().equals(id)) return user;
		}
		
		return null;
	}

	public static List<User> getUserList() {
		return userList;
	}



	public String getFineDustStatus(String locationName, Long chatId) {
		int userIndex = TextProcessor.getUserIndex(chatId);
		int fineDustInfoIndex = getFineDustInfoIndex(userIndex, locationName);

		if(userIndex > -1 && fineDustInfoIndex > -1) {
			return TextProcessor.getUserList().get(userIndex).getFineDustInfoList().get(fineDustInfoIndex).getFineDustStatus();
		}
		return null;

	}

}
