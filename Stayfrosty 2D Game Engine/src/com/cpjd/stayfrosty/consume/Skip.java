package com.cpjd.stayfrosty.consume;

import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.shop.Consumables;

public class Skip {
	
	// Technical
	GameStateManager gsm;
	
	public void addSkip(GameStateManager gsm) {
		this.gsm = gsm;
		if(gsm.getState() == GameStateManager.L1_BOSS || gsm.getState() == GameStateManager.L2_BOSS ||
				gsm.getState() == GameStateManager.L3_BOSS) {
			Consumables.skips.add(new Skip());
			return;
		}
		
		if(gsm.getState() - 1 % 10 != 0) {
			Player.completedLevel = true;
		}
	}
	
}
