package com.tpns.domain.utils;

public final class Utils {

	private Utils() {

	}

	public static void assertExistence(Object User) {
		if (null == User) {
			throw new IllegalArgumentException("No User could be founded!!!");
		}
	}

}
