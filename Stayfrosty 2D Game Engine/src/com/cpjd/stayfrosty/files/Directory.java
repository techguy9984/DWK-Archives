package com.cpjd.stayfrosty.files;

import java.io.File;

import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.util.Error;
/* Description
 *  Simply sets the location of the save file, based of the OS in future versions
 * 
 */
public class Directory {

	// File nomenclature
	public static final String foldernm = GamePanel.GAME_TITLE; // The name of the folder all the data will be written to
	public static final String savenm = "save.save";
	
	private static String appdata;
	public static File directory; // Location of the file
	public static File save; // The save file

	public static boolean delete;
	
	public static void setPath() {
		
		setOS(System.getProperty("os.name"));
		directory = new File(appdata + "/" + foldernm);
		save = new File(appdata + "/"+ foldernm + "/"+savenm);
		
		checkExists();
	}
	
	private static void checkExists() {
		if(!directory.exists() || delete) {
			directory.mkdir();
		}
		if(!save.exists() || delete) {
			try {
				save.createNewFile();
				setDefaults();
			} catch (Exception e) {
				Error.error(e, Error.FILE_WRITE_ERROR);
			}
		}
	}

	public static void delete() {
		delete = true;
	}
	
	private static void setDefaults() {
		// update
		/* 0 Update code
		 * 1 Current level
		 * 2 Dank memes
		 * 3 pistol clip
		 * 4 pistol ammo
		 * 5 uzi clip
		 * 6 uzi ammo
		 * 7 shotgun clip
		 * 8 shotgun ammo
		 * 9 ak-47 clip
		 * 10 ak-47 ammo
		 * 11 m4a1 clip
		 * 12 m4a1 ammo
		 * 13 sniper clip
		 * 14 sniper ammo
		 * 15 pistol purchase
		 * 16 uzi purchase
		 * 17 shotgun purchase
		 * 18 ak purchase
		 * 19 m4 purchase
		 * 20 sniper purchase
		 * 21 amount of pistol upgrades (0-5)
		 * 22 amount of uzi upgrades
		 * 23 amount of shotgun upgrades
		 * 24 amount of ak47 upgrades
		 * 25 amount of m4a1 upgrades
		 * 26 amount of sniper upgrades
		 * 27 endurance upgrades
		 * 28 health upgrades
		 * 29 powerup upgrades
		 * 30 armor upgrades
		 * 31 mixtape upgrades
		 * 32 health packs
		 * 33 instant powerups
		 * 34 shields
		 * 35 level skips
		 * LAUNCHER
		 * 36 resolution
		 * 37 high quality enabled
		 * 38 joe mode enabled
		 * 39 hide cursor enabled
		 * 
		 */
		int[] defaults = {
				0,GameStateManager.L_TUTORIAL,0,6,30,32,150,3,20,30,90,36,100,3,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,1,0,1,0
		};
		Write.writeAll(defaults);
	}
	
	private static void setOS(String os) {		
		String[] base = os.split("\\s+");
		if(base[0].equalsIgnoreCase("Windows")) appdata = System.getenv("APPDATA");
		else {
			Error.error(null,Error.OS_UNRECOGNIZED);
		}
	}
}
