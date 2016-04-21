package com.cpjd.stayfrosty.save;

// Loads all the data into a friendly, accessable class
public class Load {

	// Launcher vars
	private int resolution;
	private boolean quality;
	private boolean sound;
	
	public Load() {
		
		// Load launcher stuff
		resolution = Head.load(Head.launcher, 1);
		if(Head.load(Head.launcher, 3) == 1) quality = true;
		if(Head.load(Head.launcher, 5) == 1) sound = true;
		
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
