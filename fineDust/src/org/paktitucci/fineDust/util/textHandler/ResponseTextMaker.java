package org.paktitucci.fineDust.util.textHandler;

import org.paktitucci.fineDust.handlers.FineDustHandlers;
import org.telegram.telegrambots.api.objects.Message;

public class ResponseTextMaker {
	
	private TextProcessable currentProcessor;
	
	
	public String getResponseText(Message message, FineDustHandlers fineDustHandlers) {
		this.currentProcessor = ProcessorList.getCurrentProcessor(message.getText());
 		String result = this.currentProcessor.processText(message, fineDustHandlers);
 		
		return result;
	}
}
