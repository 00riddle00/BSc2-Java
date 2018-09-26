package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class EnemyBoss extends GameObject {
	
	private Handler handler;
	private int timer = 70;
	private int timer2 = 50;
	private int spawn;
	Random r = new Random();
	

	public EnemyBoss(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		velX = 0;
		velY = 2;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 96, 96);
	}

	public void tick() {
		x += velX;
		y += velY;
		// movement of the boss
		if(timer <= 0) {
			velY = 0;
			if(timer2 <= 0) {
				if(velX == 0) velX = 2;
				// gradually speeding up
				if(velX > 0) velX += 0.005f;
				else if(velX < 0) velX -= 0.005f;
				velX = Game.clamp(velX, -10, 10);
				// shooting
				spawn = r.nextInt(10);
				if(spawn == 0) handler.addObject(new EnemyBossBullet((int)x + 48, (int)y + 90, ID.BasicEnemy, handler));
			}else timer2--;
		}else timer --;
		
		
		//if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		if(x <= 0 || x >= Game.WIDTH - 96) velX *= -1;
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.red, 96, 96, 0.07f, handler));
		
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, 96, 96);
		
	}

}
