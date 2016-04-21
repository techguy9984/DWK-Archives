package com.cpjd.stayfrosty.save;

import com.cpjd.tools.Log;

/*
 * The save class contains all the methods to completely save over a file. All the required data must be passed in, because
 * the save operation will rewrite the entire file. Any 'true' or 'false' tokens passed in will be converted into '1' and '0'
 * respectively
 */
public class Save {
	
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
