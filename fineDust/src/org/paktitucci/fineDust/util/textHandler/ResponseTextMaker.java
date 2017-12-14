package org.paktitucci.fineDust.util.textHandler;

import org.telegram.telegrambots.api.objects.Message;

public class ResponseTextMaker {
	
	private TextProcessable currentProcessor;
	
	
	public String getResponseText(Message message) {
		this.currentProcessor = ProcessorList.getCurrentProcessor(message.getText());
		return this.currentProcessor.processText(message);
	}
}
