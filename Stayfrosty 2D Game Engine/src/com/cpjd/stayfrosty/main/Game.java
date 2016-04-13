package com.cpjd.stayfrosty.main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.lwjgl.openal.AL;

public class Game extends JFrame implements WindowListener {

	private static final long serialVersionUID = -1139087955463380188L;

	// Call this to start the game, specify game start parameters
	public Game(int width, int height, boolean fullscreen, boolean highQuality, boolean joe, boolean hideCursor, boolean qxx1, String title, String version, int versionCode) {
		super(title);
		
		// Set screen size
		GamePanel.WIDTH = width / GamePanel.SCALE;
		GamePanel.HEIGHT = height / GamePanel.SCALE;
		
		// Other attributes
		GamePanel.highQuality = highQuality;
		GamePanel.GAME_TITLE = title;
		GamePanel.version = version;
		GamePanel.versionCode = versionCode;
		
		GamePanel.joe = joe;
		
		// FPS
		if(joe) GamePanel.FPS = 10;
		if(!joe) GamePanel.FPS = 150;
		
		// Cursor
		if(hideCursor) GamePanel.hideCursor = true;
		
		// Fullscreen
		if(fullscreen) setUndecorated(true);
		
		// Icon image
		ImageIcon icon = new ImageIcon(getClass().getResource("/CPJD/small-illuminati.png"));
		setIconImage(icon.getImage());
		
		addWindowListener(this);
		
		setLayout(null);
		setContentPane(new GamePanel());
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		AL.destroy();
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}

}
