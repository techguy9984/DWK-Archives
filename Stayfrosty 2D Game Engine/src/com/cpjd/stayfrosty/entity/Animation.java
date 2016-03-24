package com.cpjd.stayfrosty.entity;

import java.awt.image.BufferedImage;

/* Animation for map objects, handles sprite animation */
public class Animation {

	private BufferedImage[] frames; // All the individual images of a sprite
	private int currentFrame; 
	
	private long startTime; // Between frames
	private long delay; // How long to wait between each frame
	
	private boolean playedOnce; // Whether or not the animation has already played, if it has looped
	
	public Animation() {
		playedOnce = false;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames; // Pass in the array
		currentFrame = 0;
		startTime = System.nanoTime(); // The time the animation started
		playedOnce = false;
	}
	
	public void setDelay(long d) {
		delay = d;
	}
	
	public void setFrame(int i) {
		currentFrame = i;
	}
	public void setPlayed(boolean played) {
		playedOnce = played;
	}
	public void update() { // Handles logic for determing whether or not to move to the next frame
		if(delay == -1) return;

		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length) { // Loop the animation back to 0
			currentFrame = 0;
			playedOnce = true;
		}
		
	}
	
	public int getFrame() {
		return currentFrame;
	}
	public BufferedImage getImage() {
		return frames[currentFrame];
	}
	public boolean hasPlayedOnce() {
		return playedOnce;
	}
	
}
