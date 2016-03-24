package com.cpjd.stayfrosty.files;

import java.io.BufferedReader;
import java.io.FileReader;

import com.cpjd.stayfrosty.util.Error;

public class Read {
	
	public static final int numItems = 41;
	
	public static int load(int line) {
		
		try {
			FileReader fr = new FileReader(Directory.save);
			BufferedReader br = new BufferedReader(fr);
			
			// Skip to the correct line
			for(int i = 0; i < line; i++) {
				br.readLine();
			}
			int temp = Integer.parseInt(br.readLine());
			
			br.close();
			return temp;
			
		} catch (Exception e) {
			Error.error(e, Error.FILE_READ_ERROR);
		}
		return 0;
	}
	
	public static int[] loadAll() {
		try {
			int[] temp = new int[numItems];
			FileReader fr = new FileReader(Directory.save);
			BufferedReader br = new BufferedReader(fr);
			
			for(int i = 0; i < numItems; i++) {
				temp[i] = Integer.parseInt(br.readLine());
			}
			
			br.close();
			return temp;
		} catch (NumberFormatException e) {
			Error.error(e, Error.NUMBER_FORMAT_ERROR);
		} catch (Exception e) {
			Error.error(e, Error.FILE_READ_ERROR);
		}
		return null;
		
	}
	
	
}
