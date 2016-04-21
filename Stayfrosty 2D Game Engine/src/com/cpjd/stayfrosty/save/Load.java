package com.cpjd.stayfrosty.save;

/*
 * The load class will reload all data when it's initialized.
 * Use the getter methods to get the data. To reload the data, either
 * reintialize the class, or call the load.reload(); method. Note that
 * the load file will only load integers into the class.
 * 
 */
public class Load {

	// Launcher vars
	private int resolution;
	private boolean quality;
	private boolean sound;
	
	public Load() {
		reload();
	}
	
	public void reload() {
		// Load launcher stuff
		resolution = Head.load(Head.launcher, 1);
		if (Head.load(Head.launcher, 3) == 1) quality = true;
		if (Head.load(Head.launcher, 5) == 1) sound = true;
	}
	
	public int getResolution() {
		return resolution;
	}
	public boolean getQuality() {
		return quality;
	}
	public boolean getSound() {
		return sound;
	}
	
}
