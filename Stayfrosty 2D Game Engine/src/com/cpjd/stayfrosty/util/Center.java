package com.cpjd.stayfrosty.util;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cpjd.stayfrosty.main.GamePanel;

/* Description 
 *  Computer generated placement for objects
 */
public class Center {
	/* Gets width of a string */
	public static int getSWidth(Graphics2D g, String s) {
		FontMetrics metrics = g.getFontMetrics();
		return metrics.stringWidth(s);
	}
	
	/* Gets height of a string */
	public static int getSHeight(Graphics2D g) {
		FontMetrics metrics = g.getFontMetrics();
		return metrics.getHeight();
	}
	
	/* Centers the object horizontally */
	public static int center(Graphics2D g, String s) {
		FontMetrics metrics = g.getFontMetrics();
		return (GamePanel.WIDTH / 2) - (metrics.stringWidth(s) / 2);
	}
	/* Center an image horizontally */
	public static int centeri(int width) {
		return (GamePanel.WIDTH / 2) - (width / 2);
	}
	public static int centerh(int height) {
		return (GamePanel.HEIGHT / 2) - (height / 2);
	}
	/* Centers the object vertically */
	public static int centerv(Graphics2D g) {	
		FontMetrics metrics = g.getFontMetrics();
		return (GamePanel.HEIGHT / 2) - (metrics.getHeight() / 2);
	}
	/* Align xs to a certain percent of the screen 
	 * 0% is point 0, 100% is point GamePanel.WIDTH
	 */
	public static int align(double percent) {
		percent = percent * 0.01;
		return (int)(GamePanel.WIDTH * percent);
	}
	/* Align ys to a certain percent of the screen
	 * 0% is point 0, 100% is point GamePanel.HEIGHT
	 */
	public static int aligny(double percent) {
		percent = percent * 0.01;
		return (int)(GamePanel.HEIGHT * percent);
	}
	/* Calculating hit boxes of object for mouse interaction,
	 * This method for strings drawn with Graphics2D g */
	public static Rectangle getStrBox(Graphics2D g, String s, int x, int y) {
		FontMetrics metrics = g.getFontMetrics();
		return new Rectangle(x, y, metrics.stringWidth(s), metrics.getHeight());
	}
	
	/* Calculate the horizontal center between two x points
	 * For text
	 */
	public static int getPointsCenter(int x1, int x2, String str, Graphics2D g) {
		int center = x1 + ((x2 - x1) / 2);
		FontMetrics metrics = g.getFontMetrics();
		return center - (metrics.stringWidth(str) / 2);
	}
	
	/* Calculate the horizontal center between two x points
	 * For images
	 */
	public static int getPointsCenteri(int x1, int x2, int width) {
		int center = x1 + ((x2 - x1) / 2);
		return center - (width / 2);
	}
	
	
	
	
}
