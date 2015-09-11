package edu.usc.ict.nl.utils;

import java.io.File;

public final class FileUtil {
	
	private FileUtil() { }
	
	public static String path(String s) {
		if (s == null) {
			return null;
		}
		return s.replace('\\', File.separatorChar).replace('/', File.separatorChar);
	}

}
