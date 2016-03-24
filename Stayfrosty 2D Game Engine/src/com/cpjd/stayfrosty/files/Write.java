package com.cpjd.stayfrosty.files;

import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.cpjd.stayfrosty.util.Error;

/* Description
 *  Writes the specified data to the file
 * 
 */
public class Write {
	
	// Doesn't work
	public static void write(int line, int data) {
		try {
			//values[line] = data;
			
			FileOutputStream os = new FileOutputStream(Directory.save);
			PrintWriter out = new PrintWriter(os);
			
			// Write the data as the array is slowly filled up
			for(int i = 0; i < line; i++) {
				out.println();
			}
			
			out.close();
		} catch(Exception e) {
			Error.error(e,Error.FILE_WRITE_ERROR);
		}
	}
	
	public static void writeAll(int[] data) {
		try {
			FileOutputStream os = new FileOutputStream(Directory.save);
			PrintWriter out = new PrintWriter(os);
			
			// Write all the data
			for(int i = 0; i < data.length; i++) {
				out.println(data[i]);
			}
			
			out.close();
		} catch(Exception e) {
			Error.error(e,Error.FILE_WRITE_ERROR);
		}
	}
	
}
