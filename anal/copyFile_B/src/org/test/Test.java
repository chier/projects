package org.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long time = 86400000;
		System.out.println(time/1000);
		System.out.println(time/(1000*60));
		System.out.println(time/(1000*60*60));
		System.out.println(time/(1000*60*60*24));
	}

}
