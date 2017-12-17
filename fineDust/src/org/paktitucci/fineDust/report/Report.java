package org.paktitucci.fineDust.report;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.paktitucci.fineDust.config.Config;
import org.paktitucci.fineDust.util.textHandler.RegistProcessor;


public class Report {
	
	public static final String TM_SPOT_URL = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getTMStdrCrdnt?pageNo=1&_returnType=json&numOfRows=10&ServiceKey=" + Config.OPEN_API_TOKEN;
	
	public static final String NEARBY_MEASURING_STATION_URL = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList?_returnType=json&pageNo=1&numOfRows=10&ServiceKey=" + Config.OPEN_API_TOKEN;
	
	public static final String FINE_DUST_INFO_URL = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?_returnType=json&dataTerm=DAILY&pageNo=1&numOfRows=10&ver=1.3&ServiceKey=" + Config.OPEN_API_TOKEN;
	
	public static JSONObject getResponse(String requestUrl) throws IOException {
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        JSONObject responseJson = new JSONObject(sb.toString());
        //System.out.println(responseJson.toString());
        
        return responseJson;
        
	}
	
	

	public static JSONArray getTMSpot(String locationName) {
		JSONArray list = null;
		
		try {
			locationName = URLEncoder.encode(locationName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String addParam = "&umdName=" + locationName;
		
		try {
			list = Report.getResponse(Report.TM_SPOT_URL + addParam).getJSONArray("list");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
	
	
	
	public static JSONArray getNearbyMeasuringStation(String tmX, String tmY) {
		JSONArray list = null;
		String addParam = "&tmX=" + tmX + "&tmY=" + tmY;
		
		try {
			list = Report.getResponse(Report.NEARBY_MEASURING_STATION_URL + addParam).getJSONArray("list");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public static JSONArray getFineDust(String stationName) {
		JSONArray list = null;
		String addParam = "&stationName=" + stationName;
		
		try {
			list = Report.getResponse(Report.FINE_DUST_INFO_URL + addParam).getJSONArray("list");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static String getGrade(String grade) {
		String returnGrade = "";
		switch(grade) {
			case "1" : 
				returnGrade = "좋음";
				break;
			case "2" : 
				returnGrade = "보통";
				break;
			case "3" : 
				returnGrade = "나쁨";
				break;
			case "4" : 
				returnGrade = "매우나쁨";
				break;
		}
		
		return returnGrade;
	}
	
	
	
	
	
	public static String getFineDustInfoResult(String locationName, Long chatId) {
		JSONObject fineDustInfo = Report.getFineDustInfo(locationName, chatId);

		String result = fineDustInfo.getString("dataTime") + "에 " + fineDustInfo.getString("addr") + "에서 측정한 미세먼지 지수는 " + fineDustInfo.getString("pm10Value") + "(으)로 \'"
				+ Report.getGrade(fineDustInfo.getString("pm10Grade1h"))
				+ "\'에 해당합니다.\n\n";

		result += "0 ~ 30 : 좋음\n";
		result += "31 ~ 80 : 보통\n";
		result += "81 ~ 150 : 나쁨\n";
		result += "151 ~  : 매우나쁨\n";

		changeFineDustInfo(locationName, chatId, fineDustInfo.getString("pm10Grade1h"), fineDustInfo.getString("pm10Value"));

		return result;
	}


	public static JSONObject getFineDustInfo(String locationName, Long chatId) {
        JSONObject tmSpot = Report.getTMSpot(locationName).getJSONObject(0);
        System.out.println("tmSpot = " + tmSpot);

        JSONObject measuringStation = Report.getNearbyMeasuringStation(tmSpot.getString("tmX"), tmSpot.getString("tmY")).getJSONObject(0);
        System.out.println("measuringStation = " + measuringStation);

        JSONObject fineDustInfo = Report.getFineDust(measuringStation.getString("stationName")).getJSONObject(0);
        System.out.println("fineDustInfo = " + fineDustInfo.toString());
        fineDustInfo.put("addr", measuringStation.getString("addr"));

        return fineDustInfo;
    }



	public static void changeFineDustInfo(String locationName, Long chatId, String fineDustStatus, String fineDustValue) {
        int userIndex = RegistProcessor.getUserIndex(chatId);
        int fineDustInfoIndex = RegistProcessor.getFineDustInfoIndex(userIndex, locationName);

        if(userIndex > -1 && fineDustInfoIndex > -1) {
			RegistProcessor.getUserList().get(userIndex).getFineDustInfoList().get(fineDustInfoIndex).setFineDustStatus(fineDustStatus);
			RegistProcessor.getUserList().get(userIndex).getFineDustInfoList().get(fineDustInfoIndex).setFineDustValue(fineDustValue);
        }
    }




	
}
