package org.paktitucci.fineDust.util.textHandler;

import org.telegram.telegrambots.api.objects.Message;

public interface TextProcessable {

	public String processText(Message message);
}
