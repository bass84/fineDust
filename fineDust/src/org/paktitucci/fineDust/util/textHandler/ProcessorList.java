package org.paktitucci.fineDust.util.textHandler;

public class ProcessorList {
	
	private static TextProcessable textProcessor = new TextProcessor();
	private static TextProcessable buttonProcessor = new ButtonProcessor();
	
	
	public static TextProcessable getTextProcessor() {
		return textProcessor;
	}
	
	public static TextProcessable getButtonProcessor() {
		return buttonProcessor;
	}
	
	public static TextProcessable getCurrentProcessor(String message){
		if(message.startsWith("#") || message.startsWith("/")) return getTextProcessor();
		else return getButtonProcessor();
	}

}
