package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
//import java.awt.image.BufferedImage;
import java.util.Random;

public class HardEnemy extends GameObject {
	
	private Handler handler;
	private Random r = new Random();
	//private BufferedImage enemy_image;

	public HardEnemy(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		velX = 5;
		velY = 5;
		
		//SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		//enemy_image = ss.grabImage(1, 4, 16, 16);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(y <= 0 || y >= Game.HEIGHT - 32) {
			if(velY < 0) {
				velY = (float)r.nextInt(6) + 2;
			}else {
				velY = (float)(r.nextInt(6) + 2) * -1;
			}
		}
		if(x <= 0 || x >= Game.WIDTH - 16)
			if(velX < 0) {
				velX = (float)r.nextInt(6) + 2;
			}else {
				velX = (float)(r.nextInt(6) + 2) * -1;
			}
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.yellow, 15, 15, 0.02f, handler));
		
	}

	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int)x, (int)y, 16, 16);
		//ddg.drawImage(enemy_image, (int)x, (int)y, null);
		
	}

}
