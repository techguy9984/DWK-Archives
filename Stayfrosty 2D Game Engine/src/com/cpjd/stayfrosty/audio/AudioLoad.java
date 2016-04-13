package com.cpjd.stayfrosty.audio;

/* This class will load required audio at the beginning of the game
 * 
 */


public class AudioLoad {
	
	public static double p = 0;
	
	public static boolean finished = false;
	
	public static void Start() {
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
		AudioPlayer.addSound(SKeys.Shield_Activated, "/Audio/SFX/shield_activated.ogg");
		AudioPlayer.addSound(SKeys.Shield_Running, "/Audio/SFX/shield_running.ogg");
		AudioPlayer.addSound(SKeys.Enemy_Laser, "/Audio/SFX/Enemies/enemy_laser.ogg");
		AudioPlayer.addSound(SKeys.Ak_Reload, "/Audio/SFX/Weapons/Reload/ak47r.ogg");
		AudioPlayer.addSound(SKeys.M4_Reload, "/Audio/SFX/Weapons/Reload/m4a1r.ogg");
		AudioPlayer.addSound(SKeys.Pistol_Reload, "/Audio/SFX/Weapons/Reload/pistolr.ogg");
		AudioPlayer.addSound(SKeys.Shotgun_Reload, "/Audio/SFX/Weapons/Reload/shotgunr.ogg");
		AudioPlayer.addSound(SKeys.Sniper_Reload, "/Audio/SFX/Weapons/Reload/sniperr.ogg");
		AudioPlayer.addSound(SKeys.Uzi_Reload, "/Audio/SFX/Weapons/Reload/uzir.ogg");
		AudioPlayer.addSound(SKeys.Ak_Fire, "/Audio/SFX/Weapons/Shot/ak47.ogg");
		AudioPlayer.addSound(SKeys.Laser_Fire, "/Audio/SFX/Weapons/Shot/laser.ogg");
		AudioPlayer.addSound(SKeys.M4_Fire, "/Audio/SFX/Weapons/Shot/m4a1.ogg");
		AudioPlayer.addSound(SKeys.Pistol_Fire, "/Audio/SFX/Weapons/Shot/pistol.ogg");
		AudioPlayer.addSound(SKeys.Shotgun_Fire, "/Audio/SFX/Weapons/Shot/shotgun.ogg");
		AudioPlayer.addSound(SKeys.Sniper_Fire, "/Audio/SFX/Weapons/Shot/sniper.ogg");
		AudioPlayer.addSound(SKeys.Uzi_Fire, "/Audio/SFX/Weapons/Shot/uzi.ogg");
		AudioPlayer.addSound(SKeys.Creepy, "/Audio/SFX/creepy.ogg");

		
		AudioPlayer.addMusic(SKeys.Main, "/Audio/Music/main.ogg");
		AudioPlayer.addMusic(SKeys.MLG_Epic, "/Audio/Music/mlg_epic.ogg");
		AudioPlayer.addMusic(SKeys.Boss, "/Audio/Music/Boss/boss.ogg");
		AudioPlayer.addMusic(SKeys.Credits, "/Audio/Music/Menu/credits.ogg");
		AudioPlayer.addMusic(SKeys.Menu_Music, "/Audio/Music/Menu/menu.ogg");
		AudioPlayer.addMusic(SKeys.Baby, "/Audio/Music/Boss/baby.ogg");
		AudioPlayer.addMusic(SKeys.John_Cena, "/Audio/Music/Boss/cena.ogg");
		AudioPlayer.addMusic(SKeys.Set_2, "/Audio/Music/set2.ogg");
		AudioPlayer.addMusic(SKeys.Set_3, "/Audio/Music/set3.ogg");
		AudioPlayer.addMusic(SKeys.Theme, "/Audio/Music/theme.ogg");
		
		long elapsed = (System.nanoTime() - start) / 1000000;
		System.out.println("It took: "+elapsed+" ms to load all the sound");
		finished = true;
	}
	
	public static void stopAll() {

		AudioPlayer.stopMusic(SKeys.Main);
		AudioPlayer.stopMusic(SKeys.MLG_Epic);
		AudioPlayer.stopMusic(SKeys.Boss);
		AudioPlayer.stopMusic(SKeys.Credits);
		AudioPlayer.stopMusic(SKeys.Menu_Music);
		AudioPlayer.stopMusic(SKeys.Baby);
		AudioPlayer.stopMusic(SKeys.John_Cena);
		AudioPlayer.stopMusic(SKeys.Set_2);
		AudioPlayer.stopMusic(SKeys.Set_3);
		AudioPlayer.stopMusic(SKeys.Theme);
		
	}
	
}
