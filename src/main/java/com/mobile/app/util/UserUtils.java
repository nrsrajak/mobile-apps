package com.mobile.app.util;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;



@Component
public class UserUtils {
	
	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET = "01234567890ABCDEFGHIKLMNOPQRSTVXYZabcdefghiklmnopqrstvxyz";
	private final int ITERATIONS = 10000;
	private final int KEY_LENGHT = 256;
	
	public String generatedUserId(int lenght) {
		return generatedRandomString(lenght);
	}

	private String generatedRandomString(int lenght) {
		StringBuilder returnValue = new StringBuilder(lenght);
		for(int i = 0; i < lenght; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return new String(returnValue);
	}
	
	
	

}
