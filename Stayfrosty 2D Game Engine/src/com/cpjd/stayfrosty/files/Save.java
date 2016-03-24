package com.cpjd.stayfrosty.files;

import com.cpjd.stayfrosty.shop.Stats;
import com.cpjd.stayfrosty.util.Time;

public class Save {
	
	static int[] toSave = new int[41];
	
	Load load;
	
	public static boolean restart; // The game won't save on a level restart
	
	public Save(Load load) {
		this.load = load;
		
		restart = false;
		
		// Default all values to -1 since some values can be 0
		for(int i = 0; i < 41; i++) {
			toSave[i] = -1;
		}
	}
	// Call this command to save everything
	public void save() {
		if(restart) {
			restart = false;
			return;
		}
		
		// Saves stats
		int[] data = {Stats.fired,(int)Stats.hit,(int)Stats.missed,Stats.memesGathered,Stats.deaths,Stats.enemiesKilled,Stats.powerupTimes,Stats.distanceTraveled,(int)Time.rawSeconds};
		StatsSave.save(data);
		Time.restart();
		
		SaveAnim.finished = false;
		checkArray();
		Write.writeAll(toSave);
	}
	// Check to make sure the array is filled up
	private void checkArray() {
		for(int i = 0; i < 41; i++) {
			if(toSave[i] == -1) {
				int[] temp = Load.getAll();
				toSave[i] = temp[i];
			}
		}
	}
	
	public void setValue(int index, int value) {
		toSave[index] = value;
	}
	
}
