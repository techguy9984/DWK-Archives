package com.cpjd.stayfrosty.main;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.tools.Log;
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	//////////////////// GAME SYSTEM VARIABLES////////////////////
	public static int WIDTH;
	public static int HEIGHT;
	public static int SCALE = 2;
	public static String GAME_TITLE;
	public static String VERSION;
	public static int VERSION_CODE;
	public static boolean QUALITY;
	public static boolean DEBUG;
	//////////////////// GAME SYSTEM VARIABLES////////////////////
	
	// Thread
	private Thread thread;
	private volatile boolean running;
	public static int FPS = 60;
	public static long targetTime = 1000 / FPS;
	
	// Image
	private BufferedImage image;
	private Graphics2D g;

	// Game State Manager
	private GameStateManager gsm;  
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			requestFocus();
			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	public void init() {
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		g = (Graphics2D) image.getGraphics();
		if(QUALITY) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		running = true;
		
		gsm = new GameStateManager();
		
		Log.log("Thread started successfully", 5);
	}
	
	private void hideCursor(boolean b) {
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0,0), "blank cursor");
		if(b) setCursor(blankCursor);
		if(!b) setCursor(Cursor.getDefaultCursor());
	}
	
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while(running) {
			start = System.nanoTime();
			{
				update();
				draw();
				drawToScreen();
			}
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 0;
			try {
				Thread.sleep(wait);
			} catch(Exception e) {
				Log.logError(e, Log.THREAD_ERROR);
			}
		}
	}
	
	private void update() {
		gsm.update();
	}
	
	private void draw() { 
		gsm.draw(g);
		
		if(QUALITY) Toolkit.getDefaultToolkit().sync(); // Refreshes the display on some systems
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}

	/* Input Handlers */
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
		
		// Debugging
		if(key.getKeyCode() == KeyEvent.VK_F6) {
			DEBUG = !DEBUG;
			hideCursor(!DEBUG);
		}
	}
	public void keyReleased(KeyEvent key) { gsm.keyReleased(key.getKeyCode()); }
	public void keyTyped(KeyEvent key) {}

	public void mousePressed(MouseEvent mouse) {

	}

	public void mouseReleased(MouseEvent mouse) {

	}

	public void mouseMoved(MouseEvent mouse) {

	}
	public void mouseDragged(MouseEvent mouse) {}
	public void mouseClicked(MouseEvent mouse) {}
	public void mouseEntered(MouseEvent mouse) {}
	public void mouseExited(MouseEvent mouse) {}	
}
