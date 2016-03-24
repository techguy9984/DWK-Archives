package com.cpjd.stayfrosty.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

import com.cpjd.stayfrosty.util.Error;

public class LauncherSave {
	
	private File launcher;
	private File folder; // In case it hasn't been started yet
	private final String launchnm = "launcher.save";
	
	private int resolution;
	private int quality;
	private int cursor;
	private int sound;
	private int joe;
	private int x481;
	
	public LauncherSave() {
		launcher = new File(System.getenv("APPDATA") + File.separator+"Bryan Simulator Beta 1.0"+File.separator+launchnm);
		folder = new File(System.getenv("APPDATA")+File.separator+"Bryan Simulator Beta 1.0");
		
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		if(!launcher.exists()) {
			try {
				launcher.createNewFile();
				setDefaults();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		reload();
		
	}
	
	private void setDefaults() {
		/* 0 - res
		 * 1 - quality
		 * 2 - cursor
		 * 3 - sound
		 * 4 - joe
		 */
		
		int[] defaults = {4,1,1,0,0,0};
		write(defaults);
	}
	
	private void write(int[] data) {
		try {
			FileOutputStream os = new FileOutputStream(launcher);
			PrintWriter out = new PrintWriter(os);
			
			for(int i = 0; i < data.length; i++) {
				out.println(data[i]);
			}
			out.close();
		} catch(Exception e) {
			Error.error(e,Error.FILE_WRITE_ERROR);
		}
	}
	
	private int read(int line) {
		
		try {
			FileReader fr = new FileReader(launcher);
			BufferedReader br = new BufferedReader(fr);
			
			// Skip to the correct line
			for(int i = 0; i < line; i++) {
				br.readLine();
			}
			int temp = Integer.parseInt(br.readLine());
			
			br.close();
			return temp;
			
		} catch (Exception e) {
			Error.error(e, Error.FILE_READ_ERROR);
		}
		return 0;
	}
	
	public void reload() {
		resolution = read(0);
		quality = read(1);
		cursor = read(2);
		sound = read(3);
		joe = read(4);
		x481 = read(5);
		
	}
	
	// Saves all the data, should coincide with level saving
	public void save(int[] data) {
		write(data);
		reload();
	}

	public File getFolder() {
		return folder;
	}

	public int getResolution() {
		return resolution;
	}

	public int getQuality() {
		return quality;
	}

	public int getCursor() {
		return cursor;
	}

	public int getSound() {
		return sound;
	}

	public int getJoe() {
		return joe;
	}

	public int getX481() {
		return x481;
	}
	
}
