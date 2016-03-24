package com.cpjd.stayfrosty.files;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.main.GamePanel;

public class SaveAnim {
	
	// Plays animations pertaining to saving
	BufferedImage image;
	
	// Math
	private int degree;
	public static boolean finished;
	private double speed;
	
	private int x;
	private int y;
	
	private int numRotations;
	
	public SaveAnim() {
		
		finished = false;
		
		degree = 0;
		speed = 5;
		numRotations = 0;
		
		x = GamePanel.WIDTH - 64;
		y = GamePanel.HEIGHT - 64;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Interface/save.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if(!finished) degree += speed;
		
		if(degree > 360 && !finished) {
			numRotations++;
			degree = 0;
		}

		if(numRotations > 2 && !finished) {
			finished = true;
			numRotations = 0;
			degree = 0;
		}
	}
	
	public void draw(Graphics2D g) {
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(degree), image.getWidth() / 2, image.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);

		if(!finished) {
			g.drawImage(op.filter(image, null), x, y, null);
			g.setFont(new Font("Arial",Font.PLAIN,15));
			g.setColor(Color.BLACK);
			g.drawString("Saving...", GamePanel.WIDTH - 55, GamePanel.HEIGHT - 28);
		}
	}
	
}
