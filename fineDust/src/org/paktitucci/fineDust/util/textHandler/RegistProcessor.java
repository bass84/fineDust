package org.paktitucci.fineDust.util.textHandler;

import org.paktitucci.fineDust.handlers.FineDustHandlers;
import org.paktitucci.fineDust.report.Report;
import org.paktitucci.fineDust.user.User;
import org.telegram.telegrambots.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

public class RegistProcessor implements TextProcessable{

    private static List<User> userList = new ArrayList<User>();
    private String fineDustInfo;
    private String returnText;
    private User user;

    public RegistProcessor() {
        this.fineDustInfo = "";
        this.returnText = "";
    }

    @Override
    public String processText(Message message, FineDustHandlers fineDustHandlers) {
        boolean isLocationNameExist = false;

        if(!isHaveUser(message.getChatId())) {
            this.user = new User(message.getChat().getId(), message.getChat().getFirstName(), message.getChat().getLastName());
            user.addLocationName(message.getText().trim());
            userList.add(user);
        }else {
            this.user = this.getUser(message.getChatId());
            isLocationNameExist = user.addLocationName(message.getText().trim());
        }

        String userName = message.getChat().getFirstName() + " " + message.getChat().getLastName();
        String locationName = message.getText();
        this.fineDustInfo = Report.getFineDustInfoResult(locationName.trim(), message.getChatId());
        
        if(this.fineDustInfo == null) {
        	this.returnText = userName + "님! 입력하신 지역 \'" + locationName + "\'에 대한 정보가 없습니다.\n지역정보를 다시 입력하시거나 처음으로 돌아가시려면 \'/start\'를 입력하세요";
        }else {
        	if(!isLocationNameExist) {
        		this.returnText = userName + "님 " + locationName + "을 등록하였습니다.\n\n" + this.fineDustInfo;
        	}else {
        		this.returnText = userName + "님! 이미 등록하신 지역입니다.\n\n" + this.fineDustInfo;
        	}
        	FineDustHandlers.setCurrentButtonInfo(null);
        	fineDustHandlers.makeButton("/start");
        }

        
        return this.returnText;
    }



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

}
