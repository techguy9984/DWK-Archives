package com.cpjd.stayfrosty.save;

/*
 * SaveDefaults stores all the default files that will be written at a file creation.
 * 0 is used for false and 1 is used for true
 * note that other saves should match the format of the default save so that loading is
 * consistent
 */
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
