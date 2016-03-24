package com.cpjd.stayfrosty.files;

/* Description
 *  When this class is inited, it will load data and store it in the class
 */
public class Load {


	// Data that is loaded
	private int storedUpdateCode; // 0
	private int currentLevel;
	private int memes; // 2
	
	// Ammo
	private int pistolClip; // 3
	private int pistolAmmo;
	private int uziClip;
	private int uziAmmo;
	private int shotgunClip;
	private int shotgunAmmo;
	private int akClip;
	private int akAmmo;
	private int m4Clip;
	private int m4Ammo;
	private int sniperClip;
	private int sniperAmmo; // 14
	
	// Weapons purchases
	private int[] purchases = new int[6]; // 15 - 20
	
	// Weapons upgrades
	private int pistolUpgrades; // 21
	private int uziUpgrades;
	private int shotgunUpgrades;
	private int akUpgrades;
	private int m4Upgrades;
	private int sniperUpgrades; // 26
	
	// Other upgrades
	private int enduranceUpgrades; // 27
	private int healthUpgrades;
	private int powerupUpgrades;
	private int armorUpgrades;
	private int mixtapeUpgrades; // 31
	
	// Consumables
	private int healthPacks; // 32
	private int powerupPacks;
	private int shieldPacks;
	private int skipPacks; // 35
	
	// Launcher
	private int resolution;
	private int quality;
	private int joe;
	private int cursor;

	public Load() {
		reload();
	}

	public void reload() {
		storedUpdateCode = Read.load(0);
		currentLevel = Read.load(1);
		memes = Read.load(2);
		
		pistolClip = Read.load(3);
		pistolAmmo = Read.load(4);
		uziClip = Read.load(5);
		uziAmmo = Read.load(6);
		shotgunClip = Read.load(7);
		shotgunAmmo = Read.load(8);
		akClip = Read.load(9);
		akAmmo = Read.load(10);
		m4Clip = Read.load(11);
		m4Ammo = Read.load(12);
		sniperClip = Read.load(13);
		sniperAmmo = Read.load(14);
		
		for(int i = 0; i < 6; i++) {
			purchases[i] = Read.load(15 + i);
		}
		
		pistolUpgrades = Read.load(21);
		uziUpgrades = Read.load(22);
		shotgunUpgrades = Read.load(23);
		akUpgrades = Read.load(24);
		m4Upgrades = Read.load(25);
		sniperUpgrades = Read.load(26);
		
		enduranceUpgrades = Read.load(27);
		healthUpgrades = Read.load(28);
		powerupUpgrades = Read.load(29);
		armorUpgrades = Read.load(30);
		mixtapeUpgrades = Read.load(31);
		
		healthPacks = Read.load(32);
		powerupPacks = Read.load(33);
		shieldPacks = Read.load(34);
		skipPacks = Read.load(35);
		
		// These aren't actually used
		resolution = Read.load(32);
		quality = Read.load(32);
		joe = Read.load(32);
		cursor = Read.load(32);
		
	}
	
	
	public static int[] getAll() {
		int[] temp = new int[16];
		for(int i = 0; i < 16; i++) {
			temp[i] = Read.load(i);
		}
		return temp;
	}
	public int[] getPurchases() {
		return purchases;
	}


	public int getStoredUpdateCode() {
		return storedUpdateCode;
	}
	public int getCurrentLevel() {
		return currentLevel;
	}
	public int getMemes() {
		return memes;
	}
	public int getPistolClip() {
		return pistolClip;
	}
	public int getPistolAmmo() {
		return pistolAmmo;
	}
	public int getUziClip() {
		return uziClip;
	}
	public int getUziAmmo() {
		return uziAmmo;
	}
	public int getShotgunClip() {
		return shotgunClip;
	}
	public int getShotgunAmmo() {
		return shotgunAmmo;
	}
	public int getAkClip() {
		return akClip;
	}
	public int getAkAmmo() {
		return akAmmo;
	}
	public int getM4Clip() {
		return m4Clip;
	}
	public int getM4Ammo() {
		return m4Ammo;
	}
	public int getSniperClip() {
		return sniperClip;
	}
	public int getSniperAmmo() {
		return sniperAmmo;
	}
	public int getPistolUpgrades() {
		return pistolUpgrades;
	}
	public int getUziUpgrades() {
		return uziUpgrades;
	}
	public int getShotgunUpgrades() {
		return shotgunUpgrades;
	}
	public int getAkUpgrades() {
		return akUpgrades;
	}
	public int getM4Upgrades() {
		return m4Upgrades;
	}
	public int getSniperUpgrades() {
		return sniperUpgrades;
	}
	public int getEnduranceUpgrades() {
		return enduranceUpgrades;
	}
	public int getHealthUpgrades() {
		return healthUpgrades;
	}
	public int getPowerupUpgrades() {
		return powerupUpgrades;
	}
	public int getArmorUpgrades() {
		return armorUpgrades;
	}
	public int getMixtapeUpgrades() {
		return mixtapeUpgrades;
	}
	public int getHealthPacks() {
		return healthPacks;
	}
	public int getPowerupPacks() {
		return powerupPacks;
	}
	public int getShieldPacks() {
		return shieldPacks;
	}
	public int getSkipPacks() {
		return skipPacks;
	}
	public int getResolution() {
		return resolution;
	}
	public int getQuality() {
		return quality;
	}
	public int getJoe() {
		return joe;
	}
	public int getCursor() {
		return cursor;
	}
	
}
