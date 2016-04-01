package com.cpjd.stayfrosty.input;

import java.awt.event.MouseEvent;

public class Mouse2 {
	
	public static final int NUM_VARS = 3;
	
	public static boolean mouseState[] = new boolean[NUM_VARS];
	public static boolean prevMouseState[] = new boolean[NUM_VARS];
	
	public static int LEFT = 0;
	public static int RIGHT = 1;
	public static int SCROLL = 2;
	
	public static int x;
	public static int y;
	
	public static void mouseSet(int i, boolean b) {
		if(i == MouseEvent.BUTTON1) mouseState[LEFT] = b;
		if(i == MouseEvent.BUTTON2) mouseState[RIGHT] = b;
		if(i == MouseEvent.BUTTON3) mouseState[SCROLL] = b;
	}
	
	public static void setMousePos(int x, int y) {
		Mouse2.x = x;
		Mouse2.y = y;
	}
	
	public static void update() {
		
		
		for(int i = 0; i < NUM_VARS; i++) {
			prevMouseState[i] = mouseState[i];
		}
	}
	
	public static boolean isPressed(int i) {
		return mouseState[i];
	}
}
