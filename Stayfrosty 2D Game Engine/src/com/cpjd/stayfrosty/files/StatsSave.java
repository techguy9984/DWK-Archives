package com.cpjd.stayfrosty.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

import com.cpjd.stayfrosty.util.Error;

// Saves all the stats
public class StatsSave {
	
	public static File stats;
	public static final String statsnm = "stats.save";
	
	private static int fired;
	private static int hit;
	private static int missed;
	private static int memes;
	private static int deaths;
	private static int killed;
	private static int powerup;
	private static int travel;
	public static int time;
	
	
	public StatsSave() {
		stats = new File(System.getenv("APPDATA") +"/"+Directory.foldernm+"/"+statsnm);
		
		checkExists();
		
		reload();
	}
	
	private void checkExists() {
		if(!stats.exists()) {
			try {
				stats.createNewFile();
				setDefaults();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setDefaults() {
		/* 0 fired
		 * 1 - hit
		 * 2 - missed
		 * 3 - memes
		 * 4 - deaths
		 * 5 - enemies killed
		 * 6 - powerup
		 * 7 - distance traveled
		 * 8 - time played
		 */
		
		int[] defaults = {0,0,0,0,0,0,0,0,0};
		write(defaults);
	}
	
	public static void write(int[] data) {
		try {
			FileOutputStream os = new FileOutputStream(stats);
			PrintWriter out = new PrintWriter(os);
			
			for(int i = 0; i < data.length; i++) {
				out.println(data[i]);
			}
			out.close();
		} catch(Exception e) {
			Error.error(e,Error.FILE_WRITE_ERROR);
		}
	}
	
	public static int read(int line) {
		
		try {
			FileReader fr = new FileReader(stats);
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
	
	public static void reload() {
		fired = read(0);
		hit = read(1);
		missed = read(2);
		memes = read(3);
		deaths = read(4);
		killed = read(5);
		powerup = read(6);
		travel = read(7);
		time = read(8);
		
	}
	
	// Saves all the data, should coincide with level saving
	public static void save(int[] data) {
		write(data);
		reload();
	}
	
	public int getFired() {
		return fired;
	}

	public int getHit() {
		return hit;
	}

	public int getMissed() {
		return missed;
	}

	public int getMemes() {
		return memes;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getKilled() {
		return killed;
	}

	public int getPowerup() {
		return powerup;
	}

	public int getTravel() {
		return travel;
	}

	public int getTime() {
		return time;
	}

	
}
