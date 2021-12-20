package com.geminibot.geminibot;

import com.geminibot.geminibot.consumers.SymbolsGeminiConsumer;
import com.geminibot.geminibot.entities.postgres.Symbol;
import com.geminibot.geminibot.repositories.SymbolRepository;
import com.geminibot.geminibot.repositories.TradeRepository;
import com.geminibot.geminibot.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;

@SpringBootApplication
public class GeminiBotApplication {



	public static void main(String[] args) throws InvalidKeyException {
		SpringApplication.run(GeminiBotApplication.class, args);

	}

}

