package org.paktitucci.fineDust.util.textHandler;

public class ProcessorList {
	
	private static TextProcessable textProcessor = new TextProcessor();
	private static TextProcessable buttonProcessor = new ButtonProcessor();
	private static TextProcessable registProcessor = new RegistProcessor();
	private static TextProcessable startProcessor = new StartProcessor();

	
	
	public static TextProcessable getTextProcessor() {
		return textProcessor;
	}

	public static TextProcessable getButtonProcessor() { return buttonProcessor; }

	public static TextProcessable getRegistProcessor() { return registProcessor; }

	public static TextProcessable getStartProcessor() { return startProcessor; }


	public static TextProcessable getCurrentProcessor(String message){


		if(message.startsWith("#")) {
			return getRegistProcessor();
		}else if(message.equals("/start")) {
			return getStartProcessor();
		}else {
			return getButtonProcessor();
		}

	}

}
