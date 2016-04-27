package com.cpjd.stayfrosty.menu;

import java.util.Random;

import com.cpjd.input.Keymap;

public class MOTD {
	
	private static final String[] motd = {
		"The Rubik's cube deals a deadly blow, but use it sparingly, it has a 30 second recharge rate.",
		"Hold "+Keymap.getKeyText(Keymap.jump)+" longer to jump higher.",
		"Change the HUD color in the options menu for a new look.",
		"Switch between the members of the DWK to complete various obstacles.",
	};
	
	public static String getMOTD() {
		Random r = new Random();
		return motd[r.nextInt(motd.length)];
	}
	
}
