package org.test;

import java.io.File;

import org.util.IOUtil;

public class TestMain {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		
		IOUtil.copyFile(new File("d:/说明.txt"), new File("d:/开玩笑.txt"));
	}
}
