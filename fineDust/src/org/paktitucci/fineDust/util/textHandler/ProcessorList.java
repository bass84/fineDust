package org.paktitucci.fineDust.util.textHandler;

import org.paktitucci.fineDust.handlers.FineDustHandlers;
import org.paktitucci.fineDust.properties.ButtonInfo;

public class ProcessorList {
	
	private static TextProcessable buttonProcessor = new ButtonProcessor();
	private static TextProcessable registProcessor = new RegistProcessor();
	private static TextProcessable startProcessor = new StartProcessor();


	public static TextProcessable getButtonProcessor() { return buttonProcessor; }

	public static TextProcessable getRegistProcessor() { return registProcessor; }

	public static TextProcessable getStartProcessor() { return startProcessor; }


	public static TextProcessable getCurrentProcessor(String message){
		if(message.equals("/start")) {
			return getStartProcessor();
		}else {
			if(FineDustHandlers.getCurrentButtonInfo() == null) {
				return getButtonProcessor();
			}else if(FineDustHandlers.getCurrentButtonInfo() == ButtonInfo.INIT_BUTTON_1) {
				return getRegistProcessor();
			}
			return null;
		}
		

	}

}
