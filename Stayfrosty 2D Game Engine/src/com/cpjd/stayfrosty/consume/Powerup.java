package com.cpjd.stayfrosty.consume;

import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.shop.Consumables;

public class Powerup {
	
	public void addPowerup() {
		if(GameStateManager.globalState == GameStateManager.L1_BOSS || GameStateManager.globalState == GameStateManager.L2_BOSS ||
				GameStateManager.globalState == GameStateManager.L3_BOSS) {
			Consumables.powerups.add(new Powerup());
			return;
		}
		
		Player.currentPowerup = Player.powerupRequired;
	}
	
}
