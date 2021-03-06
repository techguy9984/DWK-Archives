package com.cpjd.stayfrosty.save;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

import com.cpjd.stayfrosty.launcher.Launcher;
import com.cpjd.tools.Hardware;
import com.cpjd.tools.Log;

/*
 * -Head.init(); must be called for any file operations to happen
 * It will check if files exist, and attempt to create them.
 * All files will be stored in APPDATA in a folder named after the
 * application name. 
 * 
 * Head also contains the base-level reading and writing methods
 */
public class Head {
	
	// File names
	public static String foldername = Launcher.TITLE;
	public static String launchername = "launcher.save";
	public static String savename = "save.save";
	public static String statsname = "stats.save";
	public static String controlsname = "controls.save";
	
	// File hierarchy locations
	private static String appdata;
	private static File directory;
	
	// Files
	public static File save;
	public static File stats;
	public static File controls;
	public static File launcher;
	
	public static void init() {
		// Log
		Log.log("Intializing file loading", 5);
		
		// Find the appdata location based of operating system
		String[] base = Hardware.osName.split("\\s+");
		if(base[0].equalsIgnoreCase("Windows")) {
			appdata = System.getenv("APPDATA");
		} else {
			Log.logError(null, Log.OS_UNRECOGNIZED);
		}
		
		// Set all the file locations
		directory = new File(appdata + File.separator + foldername);
		save = new File(appdata + File.separator + foldername + File.separator + savename);
		stats = new File(appdata + File.separator + foldername + File.separator + statsname);
		controls = new File(appdata + File.separator + foldername + File.separator + controlsname);
		launcher = new File(appdata + File.separator + foldername + File.separator + launchername);
		
		// Check to see if the files exist, if not, create new ones
		if(!directory.exists()) {
			Log.log("Creating save directory", 1);
			directory.mkdir();
		}
		if(!save.exists()) {
			try {
				Log.log("Creating save file", 1);
				save.createNewFile();
			} catch(Exception e) {
				Log.logError(e, Log.SAVE_ERROR);
			}
		}
		if(!stats.exists()) {
			try {
				Log.log("Creating stats file", 1);
				stats.createNewFile();
			} catch(Exception e) {
				Log.logError(e, Log.SAVE_ERROR);
			}
		}
		if(!controls.exists()) {
			try {
				Log.log("Creating controls file", 1);
				controls.createNewFile();
			} catch(Exception e) {
				Log.logError(e, Log.SAVE_ERROR);
			}
		}
		if(!launcher.exists()) {
			try {
				Log.log("Creating launcher file", 1);
				launcher.createNewFile();
				SaveDefaults.putLaunchDefaults();
			} catch(Exception e) {
				Log.logError(e, Log.SAVE_ERROR);
			}
		}
	}

	public static void writeAppend(File file, String line) {
		try {
			FileOutputStream os = new FileOutputStream(file, true);
			PrintWriter out = new PrintWriter(os);
			
			out.println(line);
			
			out.close();
		} catch(Exception e) {
			Log.logError(e, Log.SAVE_ERROR);
		}
	}
	
	public static void writeAll(File file, String[] data) {
		try {
			FileOutputStream os = new FileOutputStream(file);
			PrintWriter out = new PrintWriter(os);
			
			// Write all the data
			for(int i = 0; i < data.length; i++) {
				out.println(data[i]);
			}
			
			out.close();
		} catch(Exception e) {
			Log.logError(e, Log.SAVE_ERROR);
		}
	}
	
	public static int load(File file, int line) {
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			// Skip to the correct line
			for(int i = 0; i < line; i++) {
				br.readLine();
			}
			int temp = Integer.parseInt(br.readLine());
			
			br.close();
			return temp;
			
		} catch (Exception e) {
			Log.logError(e, Log.SAVE_ERROR);
		}
		return 0;
	}
	
	public static int[] loadAll(File file, int items) {
		try {
			int[] temp = new int[items];
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			for(int i = 0; i < items; i++) {
				temp[i] = Integer.parseInt(br.readLine());
			}
			
			br.close();
			return temp;
		} catch (NumberFormatException e) {
			Log.logError(e, Log.INPUT_FORMAT_ERROR);
		} catch (Exception e) {
			Log.logError(e, Log.SAVE_ERROR);
		}
		return null;
		
	}
}
