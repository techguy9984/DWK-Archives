package com.cpjd.stayfrosty.main;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.lwjgl.openal.AL;

import com.cpjd.tools.Layout;
import com.cpjd.tools.Log;

@SuppressWarnings("serial")
public class Game extends JFrame implements WindowListener {

	// Call this to start the game, specify game start parameters
	public Game(String title, String version, int versionCode, Dimension screen, boolean fullscreen, boolean quality) {
		super(title);
		
		// Configure the logging
		Log.log("Game initialized with attributes: "
				+ "(Resolution) "+screen.getWidth()+" x "+screen.getHeight()+
				" (Fullscreen) "+fullscreen
				+" (High Quality) "+quality
				+" (Version) "+version
				+" (Title) "+title
				+" (Version Code) "+versionCode, 2);
		
		// Configure the layout class
		// Set screen size
		GamePanel.WIDTH = (int)screen.getWidth() / GamePanel.SCALE;
		GamePanel.HEIGHT = (int)screen.getHeight() / GamePanel.SCALE;
		Layout.HEIGHT = GamePanel.HEIGHT;
		Layout.WIDTH = GamePanel.WIDTH;
		
		// Other attributes
		GamePanel.QUALITY = quality;
		GamePanel.GAME_TITLE = title;
		GamePanel.VERSION = version;
		GamePanel.VERSION_CODE = versionCode;
		
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
