package com.geminibot.geminibot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.InvalidKeyException;

@SpringBootApplication
public class GeminiBotApplication {


	public static void main(String[] args) throws InvalidKeyException {
		SpringApplication.run(GeminiBotApplication.class, args);


//        System.out.println(caller.getAllTransfersForAccount());
	}
}
