package com.cpjd.stayfrosty.save;

public class SaveDefaults {
	
	// 0 false, 1 true
	
	public static void putLaunchDefaults() {
		Head.writeAppend(Head.launcher,"//Resolution");
		Head.writeAppend(Head.launcher,"3");
		Head.writeAppend(Head.launcher,"//Quality");
		Head.writeAppend(Head.launcher,"1");
		Head.writeAppend(Head.launcher,"//Sounds");
		Head.writeAppend(Head.launcher,"0");
	}
	
}
