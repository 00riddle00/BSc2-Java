package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class MenuParticle extends GameObject {
	
	private Handler handler;
	private Random r = new Random();
	
	private Color col;

	public MenuParticle(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		velX = movement();
		velY = movement();
		
		col = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}
	
	private float movement() {
		float move;
		
		move = r.nextInt(7) + 1;
		if(r.nextInt(2) == 0) move *= -1;
		
		return move;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 12, 12);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		if(x <- 0 || x >= Game.WIDTH - 16) velX *= -1;
		
		handler.addObject(new Trail(x, y, ID.Trail, col, 13, 13, 0.04f, handler));
		
	}

	public void render(Graphics g) {
		g.setColor(col);
		g.fillRect((int)x, (int)y, 14, 14);
		
	}

}
