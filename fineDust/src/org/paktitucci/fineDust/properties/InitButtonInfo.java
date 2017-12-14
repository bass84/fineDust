package org.paktitucci.fineDust.properties;


public enum InitButtonInfo {
	INIT_BUTTON_1("1. 새로운 지역을 등록합니다.")
	, INIT_BUTTON_2("2. 등록한 지역 리스트를 조회합니다.")
	, INIT_BUTTON_3("3. 등록한 지역을 삭제합니다..");
	
	final private String name;
	
	private InitButtonInfo(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	
}
