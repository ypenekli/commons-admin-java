package com.yp.admin;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Constants {

	private Constants() {
	}	
	private static final ResourceBundle bundleSql;
	public static final ResourceBundle bundleMessage;

	static {		
		bundleSql = ResourceBundle.getBundle("admin.Queries");
		bundleMessage = ResourceBundle.getBundle("admin.Messages");
	}

	public static String getString(final String pKey) {
		try {
			return bundleMessage.getString(pKey);
		} catch (MissingResourceException e) {
			return String.valueOf('!') + pKey + '!';
		}
	}

	public static String getSgl(final String pKey) {
		try {
			return bundleSql.getString(pKey);
		} catch (MissingResourceException e) {
			return String.valueOf('!') + pKey + '!';
		}
	}
}