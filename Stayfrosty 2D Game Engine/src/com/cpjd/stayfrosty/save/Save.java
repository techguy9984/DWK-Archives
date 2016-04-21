package com.cpjd.stayfrosty.save;

import com.cpjd.tools.Log;

public class Save {
	
	public Save() {
		
	}
	
	public void saveLauncher(String[] tokens) {
		Log.log("Saving launcher settings...", 1);
		for(int i = 0; i < tokens.length; i++) {
			if(tokens[i].equalsIgnoreCase("true")) {
				tokens[i] = String.valueOf(1);
			}
			if(tokens[i].equalsIgnoreCase("false")) {
				tokens[i] = String.valueOf(0);
			}
		}
		Head.writeAll(Head.launcher, tokens);
	}
	
}
