package com.cpjd.stayfrosty.menu;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.util.Error;

/* Description
 *  Loading screen on application startup
 */
public class Intro extends GameState {
	
	// CPJD Logo
	private BufferedImage logo;
	private int logox;
	private int logoy;
	private int logowidth;
	private int logoheight;
	
	// Stayfrosty Engine Logo
	private BufferedImage engine;
	private int enginex;
	private int enginey;
	private int enginewidth;
	private int engineheight;
	
	private float alpha = 0;

	private int state; // Stage of the startup script
	
	public static final boolean SKIP_ALLOWED = true;
	
	public Intro(GameStateManager gsm) {
		super(gsm);
		
		state = 0;

		try {
			// Cats PJ logo
			logo = ImageIO.read(getClass().getResourceAsStream("/CPJD/cpjdlogo.png"));
			
			logowidth = logo.getWidth() / 2;
			logoheight = logo.getHeight() / 2;
			
			logox = (GamePanel.WIDTH / 2) - (logowidth / 2);
			logoy = (GamePanel.HEIGHT / 2) - (logoheight / 2);
			
			// Game engine logo
			engine = ImageIO.read(getClass().getResourceAsStream("/CPJD/engine.png"));
			
			enginewidth = engine.getWidth();
			
			engineheight = engine.getHeight();
			
			enginex = (GamePanel.WIDTH / 2) - (enginewidth / 2);
			enginey = (GamePanel.HEIGHT / 2) - (engineheight / 2);
			
		} catch(Exception e) {
			Error.error(e,Error.IO_IMAGE_ERROR);
		}
		
	}

	public void update() {

		// Fade images in and out
		if(state == 0 || state == 2) {
			alpha += 0.008f;
			if(alpha >= 1) {
				try {
					// Pause when the image is fully non-transparent
					Thread.sleep(700);
				} catch(Exception e) {
					Error.error(e, Error.THREAD_ERROR);
				}
				state++;
			}
		}
		if(state == 1 || state == 3) {
			alpha += -0.008f;
			if(alpha <= 0) {
				try {
					// Pause when the image is fully non-transparent
					Thread.sleep(700);
				} catch(Exception e) {
					Error.error(e, Error.THREAD_ERROR);
				}
				state++;
			}
		}

		
		
		// Check alpha range
		if(alpha <= 0) alpha = 0;
		if(alpha >= 1) alpha = 1;
	}

	
	public void draw(Graphics2D g) {
		Composite defaultComp = g.getComposite(); // Store our original settings
		
		// Clear screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		// Draw logos
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
		if(state == 0 || state == 1) g.drawImage(logo, logox, logoy, logowidth, logoheight, null);
		if(state == 2 || state == 3) {
			// If you want the "accomplished with" text
			/*g.setColor(Color.BLACK);
			g.setFont(new Font("Times New Roman",Font.BOLD,25));
			FontMetrics fm = g.getFontMetrics();
			int strWidth = fm.stringWidth("Accomplished with");
			g.drawString("Accomplished with", (GamePanel.WIDTH / 2) - (strWidth / 2), (GamePanel.HEIGHT / 2) - engineheight - 5);*/
			
			g.drawImage(engine, enginex, enginey, enginewidth, engineheight, null);
		}
		g.setComposite(defaultComp); // Reset the composite
	}
	
	public void keyPressed(int k) {
		if(SKIP_ALLOWED) gsm.setState(GameStateManager.MENU);
	}
	public void keyReleased(int k) {}
}
