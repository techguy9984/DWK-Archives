package com.cpjd.stayfrosty.audio;

import com.cpjd.tools.Log;

/* This class will load required audio at the beginning of the game
 * 
 */


public class AudioLoad implements Runnable {
	
	public static double p = 0; // The current amount of loaded items
	
	public static boolean finished = false;
	
	public static final int TOTAL_ITEMS = 16;

	private Thread thread;
	
	public static void stopAll() {

		AudioPlayer.stopMusic(SKeys.Main);
		AudioPlayer.stopMusic(SKeys.MLG_Epic);
		AudioPlayer.stopMusic(SKeys.Credits);
		AudioPlayer.stopMusic(SKeys.Menu_Music);
		AudioPlayer.stopMusic(SKeys.Set_3);
		AudioPlayer.stopMusic(SKeys.Theme);
		AudioPlayer.stopMusic(SKeys.Epic);
		
	}
	
	public AudioLoad() {
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		// Load SFX
		System.out.println("Loading audio, please wait....");
		long start = System.nanoTime();
		AudioPlayer.addSound(SKeys.Error, "/Audio/SFX/error.ogg");
		AudioPlayer.addSound(SKeys.Change, "/Audio/SFX/turn.ogg");
		AudioPlayer.addSound(SKeys.Collect, "/Audio/SFX/item_pickup.ogg");
		AudioPlayer.addSound(SKeys.Level_Complete, "/Audio/SFX/level_complete.ogg");
		AudioPlayer.addSound(SKeys.Damage, "/Audio/SFX/player_damaged.ogg");
		AudioPlayer.addSound(SKeys.Buy, "/Audio/SFX/purchase.ogg");
		AudioPlayer.addSound(SKeys.Select, "/Audio/SFX/selection.ogg");
		AudioPlayer.addSound(SKeys.Creepy, "/Audio/SFX/creepy.ogg");
		AudioPlayer.addSound(SKeys.Type, "/Audio/SFX/type.ogg");
		
		AudioPlayer.addMusic(SKeys.Main, "/Audio/Music/main.ogg");
		AudioPlayer.addMusic(SKeys.MLG_Epic, "/Audio/Music/mlg_epic.ogg");
		AudioPlayer.addMusic(SKeys.Credits, "/Audio/Music/Menu/credits.ogg");
		AudioPlayer.addMusic(SKeys.Menu_Music, "/Audio/Music/Menu/menu.ogg");
		AudioPlayer.addMusic(SKeys.Set_3, "/Audio/Music/set3.ogg");
		AudioPlayer.addMusic(SKeys.Theme, "/Audio/Music/theme.ogg");
		AudioPlayer.addMusic(SKeys.Epic, "/Audio/Music/epic.ogg");
		
		long elapsed = (System.nanoTime() - start) / 1000000;
		Log.log("It took: "+elapsed+" ms to load all the sound", 1);
		finished = true;
		
		try{
			thread.join();
		} catch(Exception e) {}
	}
	
}
