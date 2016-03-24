package com.cpjd.stayfrosty.entity;

import java.awt.event.KeyEvent;

import com.cpjd.stayfrosty.tilemap.TileMap;

// The skeleton key allows the player to toggle the crates in the current map
public class SkeletonKey {

	public boolean hasKey;
	
	public boolean on;
	
	TileMap tileMap;
	
	public SkeletonKey(TileMap tm) {
		this.tileMap = tm;
		
		on = true;
	}
	
	public boolean hasKey() {
		return hasKey();
	}
	
	public void setKey(boolean b) {
		this.hasKey = b;
	}
	
	public void toggleOff() {
		if(!hasKey) return;
		for (int i = 0; i < tileMap.getNumCols(); i++) {
			for (int j = 0; j < tileMap.getNumRows(); j++) {
				int[][] tempMap = tileMap.getMap();
				if (tempMap[j][i] == 40) {
					tileMap.setMap(j, i, 1);
				}
			}
		}
		on = false;
	}
	
	public void toggleOn() {
		if(!hasKey) return;
		for (int i = 0; i < tileMap.getNumCols(); i++) {
			for (int j = 0; j < tileMap.getNumRows(); j++) {
				int[][] tempMap = tileMap.getMap();
				if (tempMap[j][i] == 1) {
					tileMap.setMap(j, i, 40);
					System.out.println("here");
				}
			}
		}
		on = true;
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_G) {
			if(on) toggleOff();
			else toggleOn();
		}
	}
	
}
