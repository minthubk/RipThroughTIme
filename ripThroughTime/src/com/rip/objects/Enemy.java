package com.rip.objects;

import java.util.Random;

import renderers.LevelRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.rip.RipGame;

public abstract class Enemy extends MovableEntity {

	protected float health;
	protected float damage;
	boolean collides_player;
	
	//collision objects.
	public Intersector in;
	//public Rectangle hitableBox;
	
	boolean flank;
	Random r = new Random();
	int trackX;
	int trackY;
	boolean flankPoint1;
	boolean flankPoint2;
	
	public boolean spawnPoint = false;
	
	public enum Directions { DIR_LEFT, DIR_RIGHT };
	
	Directions dir = Directions.DIR_RIGHT;
	
	public Enemy(int x, int y, float width, float height, Texture texture, int SPEED, float health) {
		super(x, y, width, height, SPEED, texture);
		Gdx.app.log(RipGame.LOG, "Enemy Created.");
		this.health = health;
		collides_player = false;
		make_flanks();
		//hitableBox = new Rectangle(x, y, width, height);
	}
	
	public Enemy(int x, int y, float width, float height, int SPEED, float health) {
		super(x, y, width, height, SPEED);
		this.health = health;
		collides_player = false;
		make_flanks();
		//hitableBox = new Rectangle(x, y, width, height);
	}
	
	public void make_flanks() {
		if (r.nextInt(4) < 3) {
			flank = false;
		} else {
			Gdx.app.log(RipGame.LOG, "flank true");
			flank = true;
			trackX = 0;
			trackY = 0;
			flankPoint1 = false;
			flankPoint2 = false;
		}
	}
	
	
	public void track(Player p) {
//		Gdx.app.log(RipGame.LOG, "Enemy -> X: " + getX() + ", Y: " + getY());
//		Gdx.app.log(RipGame.LOG, "Player -> X: " + p.getMiddleX() + ", Y: " + p.getMiddleY());
		
		int dx, dy;
		
		dx = p.getMiddleX() - this.getMiddleX();
		dy = p.getMiddleY() - this.getMiddleY();
		
//		Gdx.app.log(RipGame.LOG, "flank: " + flank);
		
		if (flank == false) {
			int pX;
			int pY;
			
			pX = p.getMiddleX();
			pY = p.getMiddleY();
			
			if (pY > 180) { pY = 180; }

			this.setX(this.getX() + (int)((dx - this.SPEED) * LevelRenderer.delta));
			this.setY(this.getY() + (int)((dy - this.SPEED) * LevelRenderer.delta));
			
			
		} else {
			if (trackX == 0 && trackY == 0) {
				Gdx.app.log(RipGame.LOG, "flank Initiated");
				
				trackX = p.getMiddleX();
				trackY = p.getMiddleY() - (r.nextInt(400-200) + 200);
			} else if (flankPoint1 == false) {
				dx = trackX - this.getMiddleX();
				dy = trackY - this.getMiddleY();
				this.setX(this.getX() + (int)((dx - this.SPEED + 2) * LevelRenderer.delta));
				this.setY(this.getY() + (int)((dy - this.SPEED + 2) * LevelRenderer.delta));
				if (this.getMiddleX() <= trackX + this.width + 10 && this.getMiddleX() >= trackX - this.width + 10 && this.getMiddleY() <= trackY + this.height + 10 && this.getMiddleY() >= trackY - this.height + 10 ) {
					flankPoint1 = true;
					Gdx.app.log(RipGame.LOG, "flankPoin1 = " + flankPoint1);
					if (spawnPoint) {
						trackX = p.getMiddleX() - (r.nextInt(500-300) + 300);
						trackY = p.getMiddleY();
					} else {
						trackX = p.getMiddleX() + (r.nextInt(500-300) + 300);
						trackY = p.getMiddleY();
					}
				}
			} else if (flankPoint2 == false) {
				dx = trackX - this.getMiddleX();
				dy = trackY - this.getMiddleY();
				setX(getX() + (int)((dx - SPEED + 2) * LevelRenderer.delta));
				setY(getY() + (int)((dy - SPEED + 2) * LevelRenderer.delta));
				if (this.getMiddleX() <= trackX + this.width + 10 && this.getMiddleX() >= trackX - this.width + 10 && this.getMiddleY() <= trackY + this.height + 10 && this.getMiddleY() >= trackY - this.height + 10 ) {
					flankPoint2 = true;
					trackY = p.getY();
					flank = false;
					}
			} 
		}

	}

	
	
	/*
	public void track(Player p) {
		if (this.collides_player) {
			return;
			
		} else {
		
			int pX = p.getX();
			int pY = p.getY();
	
			int dx = pX - x;
			int dy = pY - y;
			
			if (dx > 0) {
				dir = Directions.DIR_RIGHT;
			} else if (dx < 0) {
				dir = Directions.DIR_LEFT;
			}
	
			this.setX(this.getX() + (int)((dx - this.SPEED) * LevelRender.delta));
	
	
			this.setY(this.getY() + (int)((dy - this.SPEED) * LevelRender.delta));
		}
	}
	*/
	
	
	
	public int getDx(Player p) {
		return Math.abs(p.getMiddleX() - this.getMiddleX());
	}
	
	public int getDy(Player p) {
		return Math.abs(p.getMiddleY() - this.getMiddleY());
	}
	
	
	
	
	public float getDamage() {
		return damage;
	}


	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public Directions getDir() {
		return dir;
	}

	public void setDir(Directions dir) {
		this.dir = dir;
	}

	public boolean isCollides_player() {
		return collides_player;
	}

	public void setCollides_player(boolean collides_player) {
		this.collides_player = collides_player;
	}

	

}
