package com.cpjd.stayfrosty.consume;

import com.cpjd.stayfrosty.entity.Player;

public class Health {
	
	public void addHealth() {
		int temp = Player.maxHealth / 2;
		Player.health += temp;
	}
	
}
