package edu.usc.ict.nl.utils;

import java.nio.file.Paths;

public final class Sanitizer {

	private Sanitizer() { }
	
	public static String concat(String part1, String... others) {
		StringBuilder toReturn = new StringBuilder(part1);
		for (String s: others) {
			toReturn.append(s);
		}
		return toReturn.toString();
	}
	
	public static String file(String s) {
		return s;
	}
	
	public static String file(final String parent, final String child) {
		return Paths.get(parent, child).getFileName().toString();
	}
}
