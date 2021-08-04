package com.mobile.app.security;

import com.mobile.app.SpringApplicationContext;
import com.mobile.app.properties.AppProperties;

public class SecurityConstants {
	
	public static final long EXPIRATION_TIME = 84000000;// 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";

			
	public static String getTokenSecret(){
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");
		return appProperties.getTokenSecret("tokenSecret");
	}

}
