package org.paktitucci.fineDust.main;

import org.paktitucci.fineDust.handlers.FineDustHandlers;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;



public class FineDustMain {
	public static void main(String[] args) {

		ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new FineDustHandlers());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }



    }
}
