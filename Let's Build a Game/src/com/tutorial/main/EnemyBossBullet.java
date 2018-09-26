package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class EnemyBossBullet extends GameObject {
	
	private Handler handler;
	private Random r = new Random();
	Color offRed = new Color(250, 20, 35);

	public EnemyBossBullet(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		velX = (r.nextInt(5 - -5) + -5); // generates randInt(-5, 5)
		velY = 5;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		//if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		//if(x <- 0 || x >= Game.WIDTH - 16) velX *= -1;
		
		if(y >= Game.HEIGHT) handler.removeObject(this);
		
		handler.addObject(new Trail(x, y, ID.Trail, offRed, 15, 15, 0.05f, handler));
		
	}

	public void render(Graphics g) {
		g.setColor(offRed);
		g.fillRect((int)x, (int)y, 16, 16);
		
	}

}
