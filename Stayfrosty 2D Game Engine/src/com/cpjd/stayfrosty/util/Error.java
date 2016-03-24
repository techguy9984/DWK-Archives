package com.cpjd.stayfrosty.util;

import javax.swing.JOptionPane;

@SuppressWarnings("unused")
public class Error {
	
	// Error codes
	public static final int THREAD_ERROR = 0;
	public static final int IO_IMAGE_ERROR = 1;
	public static final int FILE_WRITE_ERROR = 2;
	public static final int FILE_READ_ERROR = 3;
	public static final int OS_UNRECOGNIZED = 4;
	public static final int NUMBER_FORMAT_ERROR = 5;
	
	public static void error(Exception e, int ERROR_CODE) {
		e.printStackTrace();
		
		if(ERROR_CODE < 100) fatal(ERROR_CODE);
		if(ERROR_CODE > 100 && ERROR_CODE < 200) nonfatal(ERROR_CODE);
	}
	
	// < 100
	private static void fatal(int ERROR_CODE) {
		JOptionPane.showMessageDialog(null,"A fatal error occurred.\nError code: "+ERROR_CODE,"Fatal Error",JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}
	
	// > 100 & < 200
	private static void nonfatal(int ERROR_CODE) {
		JOptionPane.showMessageDialog(null, "An error occured.\nError code: "+ERROR_CODE,"Error", JOptionPane.ERROR_MESSAGE);
	}
	
	// > 200 && < 300
	private static void warn(int WARNING_CODE, String message) {
		JOptionPane.showMessageDialog(null, message + "\nError code: "+WARNING_CODE,"Warning",JOptionPane.WARNING_MESSAGE);
	}

	
	
}
