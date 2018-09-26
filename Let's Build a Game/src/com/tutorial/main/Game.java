package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 7580815534084638412L;
	// 3 : 4 aspect ratio
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	/* This is going to be a single threaded game, its going to 
	 * run on that thread below */
	private Thread thread;
	private boolean running = false;
	
	private Random r;
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	private Shop shop;
	
	public int diff; // 0 = normal, 1 = hard
	public static BufferedImage sprite_sheet;
	
	public static boolean paused = false;
	
	public enum STATE {
		Menu,
		Help,
		Game,
		Select,
		Shop,
		End
	};
	
	public static STATE gameState = STATE.Menu;
	
	// *****CONSTRUCTOR*****
	public Game() {
		
		handler = new Handler();
		hud = new HUD();
		shop = new Shop(handler, hud);
		menu = new Menu(this, handler, hud);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);
		this.addMouseListener(shop);
		
		AudioPlayer.load();
		AudioPlayer.getMusic("music").loop();
		
		new Window(WIDTH, HEIGHT, "Let's Build a Game!", this);
		
		BufferedImageLoader loader = new BufferedImageLoader();
		sprite_sheet = loader.loadImage("/sprite_sheet.png");
		System.out.println("SpriteSheet loaded");
		
		spawner = new Spawn(handler, hud, this);
		r = new Random();
		
		if(gameState == STATE.Game) {
			handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler));
			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.BasicEnemy, handler));
		}else {
			for(int i = 0; i < 15; i++) {
				handler.addObject(new MenuParticle(r.nextInt(WIDTH - 38), r.nextInt(HEIGHT - 62), ID.MenuParticle, handler));
			}
		}
	}

	public synchronized void start() {
		// making an instance of the thread and starting it
		thread = new Thread(this);
		thread.start();	
		running = true;
	}
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	// this is a commonly used game loop pattern in Java
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running) {
				render();
			}
			frames++;
			// fps counter
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		if(gameState == STATE.Game) {
			if(!paused) {
				hud.tick();
				spawner.tick();
				handler.tick();
				
				if(HUD.HEALTH <= 0) {
					gameState = STATE.End;
					handler.clearEnemies();
					HUD.HEALTH = 100;
					for(int i = 0; i < 15; i++) {
						handler.addObject(new MenuParticle(r.nextInt(WIDTH - 38), r.nextInt(HEIGHT - 62), ID.MenuParticle, handler));
					}
				}
			}
		}else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select){
			menu.tick();
			handler.tick();
		}
	}
	
	private void render() {
		// Creates 3 buffers
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		// Renders actual graphics
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		
		if(paused) {
			g.setColor(Color.white);
			g.drawString("PAUSED", 300, 200);
			handler.render(g);
		}
		
		if(gameState == STATE.Game) {
			hud.render(g);
			handler.render(g);
		}else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select){
			menu.render(g);
			handler.render(g);
		}else if(gameState == STATE.Shop) {
			shop.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max) {
		if(var > max) return var = max;
		else if(var < min) return var = min;
		else return var;
	}
	
	public static void main(String args[]) {
		new Game();
	}
}
