package com.cpjd.stayfrosty.save;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.tools.Hardware;
import com.cpjd.tools.Log;

// Manages reading and writing of files
public class Head {
	
	// Number of lines (or items)
	public static int NUM_SAVE_ITEMS = 41;
	public static int NUM_STATS_ITEMS = 1;
	public static int NUM_CONTROLS_ITEMS = 20;
	
	// File names
	public static String foldername = GamePanel.GAME_TITLE;
	public static String savename = "save.save";
	public static String statsname = "stats.save";
	public static String controlsname = "controls.save";
	
	// File hierarchy locations
	private static String appdata;
	private static File directory;
	
	// Files
	private static File save;
	private static File stats;
	private static File controls;
	
	public static void init() {
		// Find the appdata location based of operating system
		String[] base = Hardware.osName.split("\\s+");
		if(base[0].equalsIgnoreCase("Windows")) {
			appdata = System.getenv("APPDATA");
		} else {
			Log.logError(null, Log.OS_UNRECOGNIZED);
		}
		
		// Set all the file locations
		directory = new File(appdata + File.separator + foldername);
		save = new File(appdata + File.separator + savename);
		stats = new File(appdata + File.separator + statsname);
		controls = new File(appdata + File.separator + controlsname);
		
		// Check to see if the files exist, if not, create new ones
		if(!directory.exists()) {
			directory.mkdir();
		}
		if(!save.exists()) {
			try {
				save.createNewFile();
			} catch(Exception e) {
				Log.logError(e, Log.SAVE_ERROR);
			}
		}
		if(!stats.exists()) {
			try {
				stats.createNewFile();
			} catch(Exception e) {
				Log.logError(e, Log.SAVE_ERROR);
			}
		}
		if(!controls.exists()) {
			try {
				controls.createNewFile();
			} catch(Exception e) {
				Log.logError(e, Log.SAVE_ERROR);
			}
		}
	}

	public static void write(File file, int line, int data) {
		try {
			FileOutputStream os = new FileOutputStream(file, true);
			PrintWriter out = new PrintWriter(os);
			
			for(int i = 0; i < line; i++) {
				out.println();
			}
			
			out.close();
		} catch(Exception e) {
			Log.logError(e, Log.SAVE_ERROR);
		}
	}
	
	public static void writeAll(File file, int[] data) {
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
