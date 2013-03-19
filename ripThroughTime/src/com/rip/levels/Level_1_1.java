package com.rip.levels;

import java.util.ArrayList;
import java.util.Random;

import renderers.LevelRender;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Timer;
import com.rip.RipGame;
import com.rip.objects.Ape;
import com.rip.objects.Enemy;
import com.rip.objects.Player;
import com.rip.objects.Raptor;


public class Level_1_1 {
	
	public RipGame game;
	Player player;
	LevelRender lr;
	ArrayList<Enemy> enemies;
	private InputHandler in;
	
	Random r = new Random();
	int rightside;
	int leftside;
	
	Timer t = new Timer();
		
	Music leveltheme;
	
	public Level_1_1(RipGame game) {
			this.game = game;
			enemies = new ArrayList<Enemy>();
			this.player = new Player(250, 158);
			setIn(new InputHandler(this));
			Gdx.input.setInputProcessor(getIn());
			/*
			Raptor raptor_one = new Raptor(800, 50);
			Raptor raptor_two = new Raptor(500, 150);
			enemies.add(raptor_one);
			enemies.add(raptor_two);
			*/
			leveltheme = Gdx.audio.newMusic(Gdx.files.internal("data/Prehistoric Main.mp3"));
			leveltheme.setLooping(true);
	}
	
//	public void checkPoint(int numOfEnemiesRap, int numOfEnemiesApe) {
//		
//		while (numOfEnemiesRap + numOfEnemiesApe > 0) {
//			int rightside = LevelRender.camPos + RipGame.WIDTH;
//			int leftside = LevelRender.camPos;
//			
//			Timer.Task generator = new Timer.Task() {
//				
//				@Override
//				public void run() {
//					if (r.nextBoolean() && numOfEnemiesRap > 0) {
//						
//					}
//					
//				}
//			};
//		}
//	}
	
	
//	public void checkPoint(int numOfEnemiesRap, int numOfEnemiesApe) {
//			int rapI = 0;
//			int apeI = 0;
//		
//			Timer.Task rapGenerator = new Timer.Task() {
//				@Override
//				public void run() {
//					Gdx.app.log(RipGame.LOG, "generated");
//					boolean lr = r.nextBoolean();
//					if (lr) {
//						rightside = LevelRender.camPos + RipGame.WIDTH;
//						enemies.add(new Raptor(r.nextInt((rightside + RipGame.WIDTH) - (rightside + 50)) + (rightside + 50), r.nextInt(LevelRender.Y_LIMIT)));
//					} else {
//						leftside = LevelRender.camPos;
//						enemies.add(new Raptor(r.nextInt((leftside - 50) - (leftside - RipGame.WIDTH)) + (leftside - RipGame.WIDTH), r.nextInt(LevelRender.Y_LIMIT)));
//					}
//
//				}
//			};
//			while (!rapGenerator.isScheduled() && numOfEnemiesRap > 0) {
//				t.scheduleTask(rapGenerator, 2);
//				numOfEnemiesRap -= 1;
//			}
//			
//			
//			Timer.Task apeGenerator = new Timer.Task() {
//				
//				@Override
//				public void run() {
//					Gdx.app.log(RipGame.LOG, "generated");
//					boolean lr = r.nextBoolean();
//					if (lr) {
//						rightside = LevelRender.camPos + RipGame.WIDTH;
//						enemies.add(new Ape(r.nextInt((rightside + RipGame.WIDTH) - (rightside + 50)) + (rightside + 50), r.nextInt(LevelRender.Y_LIMIT)));
//					} else {
//						leftside = LevelRender.camPos;
//						enemies.add(new Ape(r.nextInt((leftside - 50) - (leftside - RipGame.WIDTH)) + (leftside - RipGame.WIDTH), r.nextInt(LevelRender.Y_LIMIT)));
//					}
//					
//				}
//			};
//			while (!apeGenerator.isScheduled() && numOfEnemiesApe > 0) {
//				t.scheduleTask(apeGenerator, 2);
//				numOfEnemiesApe -= 1 ;
//			}
//	}
	
	public void checkPoint(int numOfEnemiesRap, int numOfEnemiesApe) {
		int buffer = 200;
		Random r = new Random();
		int rightside;
		int leftside;
		boolean lr;

		for (int i = 0; i < numOfEnemiesRap; i++) {
			lr = r.nextBoolean();
			if (lr) {
				rightside = LevelRender.camPos + RipGame.WIDTH;
				Raptor raptor = new Raptor(LevelRender.camPos + RipGame.WIDTH + buffer, r.nextInt(LevelRender.Y_LIMIT));
				raptor.spawnPoint = true;
				enemies.add(raptor);
				buffer += 200;
				
			} else {
				leftside = LevelRender.camPos;
				enemies.add(new Raptor(LevelRender.camPos - buffer , r.nextInt(LevelRender.Y_LIMIT)));
				buffer += 200;
			}

		}

		for (int i = 0; i < numOfEnemiesApe; i++) {
			lr = r.nextBoolean();
			if (lr) {
				rightside = LevelRender.camPos + RipGame.WIDTH;
				Ape ape = new Ape(LevelRender.camPos + RipGame.WIDTH + buffer, r.nextInt(LevelRender.Y_LIMIT));
				ape.spawnPoint = true;
				enemies.add(ape);
				buffer += 200;
			} else {
				leftside = LevelRender.camPos;
				enemies.add(new Ape(LevelRender.camPos - buffer, r.nextInt(LevelRender.Y_LIMIT)));
				buffer += 200;
			}

		}
	}
	
		
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}


	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}


	public Player getPlayer(){
		return player;
	}
	
	public void update() {
		player.update();
	}

	public void setRenderer(LevelRender lr) {
		this.lr = lr;
	}
	
	public LevelRender getRenderer() {
		return lr;
	}
	
	public void dispose() {
		
	}


	public Music getLeveltheme() {
		return leveltheme;
	}

	public InputHandler getIn() {
		return in;
	}

	public void setIn(InputHandler in) {
		this.in = in;
	}

	
	
}

