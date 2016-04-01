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

import com.cpjd.stayfrosty.entity.Player;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.input.Mouse2;
import com.cpjd.stayfrosty.util.Error;
import com.cpjd.stayfrosty.util.Time;
import com.cpjd.tools.Mouse;
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	////////// GAME SYSTEM VARIABLES //////////
	public static int WIDTH; // 320 240
	public static int HEIGHT;
	public static int SCALE = 2; // Scale up the game
	public static String GAME_TITLE;
	public static String version;
	public static int versionCode;
	public static boolean highQuality;
	public static boolean hideCursor;
	public static boolean debug; // Turns on all on-screen debugging information
	public static boolean joe;
	////////// GAME SYSTEM VARIABLES //////////
	
	// Thread
	private Thread thread;
	private volatile boolean running;
	public static int FPS = 120;
	public static long targetTime = 1000 / FPS;
	
	// Image
	private BufferedImage image;
	private Graphics2D g;

	// Game State Manager
	private GameStateManager gsm;
	
	// Timing
	Time time;
	
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
		if(!joe) image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		if(joe) image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_BYTE_GRAY);
		
		g = (Graphics2D) image.getGraphics();
		if(GamePanel.highQuality) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		running = true;
		
		gsm = new GameStateManager();
		
		time = new Time();
		
		// Get rid of the mouse
		//hideCursor(hideCursor);
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
				Error.error(e, Error.THREAD_ERROR);
			}
		}
	}
	
	private void update() {
		gsm.update();
		
		time.update();
		

		
		Mouse2.update();

	}
	
	private void draw() { 
		gsm.draw(g);
		
		Toolkit.getDefaultToolkit().sync(); // Refreshes the display on some systems
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
			if(debug) {
				debug = false;
				Player.freecam = false;
				hideCursor(hideCursor);
				return;
			}
			if(!debug) {
				Player.freecam = true;
				hideCursor(false);
				debug = true;
				return;
			}
		}
	}
	public void keyReleased(KeyEvent key) { gsm.keyReleased(key.getKeyCode()); }
	public void keyTyped(KeyEvent key) {}

	public void mousePressed(MouseEvent mouse) {
		Mouse2.mouseSet(mouse.getButton(), true);
		Mouse.leftPressed = true;
	}

	public void mouseReleased(MouseEvent mouse) {
		Mouse2.mouseSet(mouse.getButton(), false);
		Mouse.leftPressed = false;
	}

	public void mouseMoved(MouseEvent mouse) {
		Mouse2.setMousePos(mouse.getX(), mouse.getY());
		Mouse.x = (int)(mouse.getX() * 0.5);
		Mouse.y = (int)(mouse.getY() * 0.5);
	}
	public void mouseDragged(MouseEvent mouse) {}
	public void mouseClicked(MouseEvent mouse) {}
	public void mouseEntered(MouseEvent mouse) {}
	public void mouseExited(MouseEvent mouse) {}	
}
