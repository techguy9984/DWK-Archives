package com.cpjd.stayfrosty.shop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.cpjd.stayfrosty.files.StatsSave;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.util.Time;

public class Stats {
	
	// Statistics
	public static int fired;
	public static double hit;
	public static double missed;
	private double accuracy;
	public static int memesGathered;
	public static int deaths;
	public static int enemiesKilled;
	public static int powerupTimes;
	public static int distanceTraveled;

	// Variables
	private int yflux;
	private Font font;
	public static boolean enabled;
	private boolean finished;
	private int increment;
	private boolean bounced;
	
	// Rank stuff
	private String[] ranks = {
			"Terrible","99bacon","major dweeb","silver 4","console peasant","ginger","almost decent",
			"at least you tried","probably hacking","definately hacking","Bryan"
	};
	
	private int score;
	private int currentRank;
	
	private StatsSave st;
	
	public Stats() {
		
		font = new Font("Arial",Font.PLAIN,12);
		
		st = new StatsSave();
		
		currentRank = 0;
		
		fired = st.getFired();
		hit = st.getHit();
		missed = st.getMissed();
		memesGathered = st.getMemes();
		deaths = st.getDeaths();
		enemiesKilled = st.getKilled();
		powerupTimes = st.getPowerup();
		distanceTraveled = st.getTravel();
		
		reset();
		
	}
	
	public void reset() {
		yflux = GamePanel.HEIGHT;
		enabled = false;
		finished = false;
		bounced = false;
		increment = 12;
		
		
	}
	

	public void update() {
		// calculate accuracy as a percent
		accuracy = (hit / fired) * 100;
		
		calcScore();
		
		if(!finished) {
			yflux -= increment;
			if(yflux < GamePanel.HEIGHT - 230) {
				increment = -4;
				bounced = true;
			}
			if(bounced && yflux > GamePanel.HEIGHT - 190) {
				finished = true;
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		if(enabled) {
			g.setColor(Color.BLACK);
			g.fillRect(10, yflux, 200, 300);
			g.setColor(Color.WHITE);
			g.setFont(font);
			
			// Stats
			g.drawString("Stats:",12, yflux + 15);
			g.drawString("Shots fired: "+fired, 12, yflux + 30);
			g.drawString("Shots hit: "+(int)hit, 12, yflux + 45);
			g.drawString("Shots missed: "+(int)missed, 12, yflux + 60);
			g.drawString("Accuracy: "+(int)accuracy+"%", 12, yflux + 75);
			g.drawString("Memes gathered: "+memesGathered, 12, yflux + 90);
			g.drawString("Deaths: "+deaths, 12, yflux + 105);
			g.drawString("Enemies killed: "+enemiesKilled, 12, yflux + 120);
			g.drawString("Powerups: "+powerupTimes+" time(s)", 12, yflux + 135);
			g.drawString("Distance traveled: "+distanceTraveled+" pixels", 12, yflux + 150);
			String temp = "";
			if(Time.unit == 1) temp = "second(s)";
			if(Time.unit == 2) temp = "minute(s)";
			if(Time.unit == 3) temp = "hour(s)";
			if(Time.unit == 4) temp = "day(s)";
			g.drawString("Time played: "+(int)Time.time+" "+temp, 12, yflux + 165);
			g.setColor(Color.RED);
			g.drawString("RANK: "+ ranks[currentRank]+"("+score+ ")", 12, yflux + 180);
		}

	}
	
	// Calculation for the score
	public void calcScore() {
		double base = ((accuracy) + (memesGathered) - (deaths) - (enemiesKilled * 0.8) + (powerupTimes * 0.03)
				-(distanceTraveled * 0.0001) - (Time.time * 0.001)) / 2;
		score = (int)base;
		
		if(score < 0) score = 0;
		
		if(score < 100) currentRank = 0;
		if(score >= 100 && score < 200) currentRank = 1;
		if(score >= 200 && score < 300) currentRank = 2;
		if(score >= 300 && score < 400) currentRank = 3;
		if(score >= 400 && score < 500) currentRank = 4;
		if(score >= 500 && score < 600) currentRank = 5;
		if(score >= 600 && score < 700) currentRank = 6;
		if(score >= 700 && score < 800) currentRank = 7;
		if(score >= 800 && score < 900) currentRank = 8;
		if(score >= 900) currentRank = 9;
	}
	
}
