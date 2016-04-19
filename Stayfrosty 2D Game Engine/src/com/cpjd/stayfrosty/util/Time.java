package com.cpjd.stayfrosty.util;

// Keeps track of how long we've played
public class Time {
	
	static long startTime;
	long elapsed;
	
	public static double rawSeconds;
	public static double time;
	
	public static int unit; // 1 second, 2 minute, 3 hour, 4 day
	
	public Time() {
		startTime = System.currentTimeMillis();
		
		unit = 1;
	}
		
	public static void restart() {
		startTime = System.currentTimeMillis();
	}
	
	public void update() {
		elapsed = System.currentTimeMillis() - startTime;
	
		rawSeconds = (elapsed / 1000);
		
		unit = 1;
		time = rawSeconds;
		
		if(time >= 60 * 60 * 24) {
			unit = 4;
			time = rawSeconds / 60 / 60 / 24;
		}
		
		if(time >= 60 * 60) {
			unit = 3;
			time = rawSeconds / 60 / 60;
		}
		if(time >= 60) {
			unit = 2;
			time = rawSeconds / 60;	
		}
		
	
		
	}
	
}
