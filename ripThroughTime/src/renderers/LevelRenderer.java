package renderers;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rip.RipGame;
import com.rip.levels.Level;
import com.rip.objects.Enemy;
import com.rip.objects.MovableEntity;
import com.rip.objects.Player;
import com.rip.objects.Raptor;

public class LevelRenderer {
	//////////UNIVERSAL VARIABLES//////////
	Level level;
	SpriteBatch batch;
	Music leveltheme;
	RipGame game;
	ShapeRenderer sr;
	Player player;
	int width, height;
	public final static int Y_LIMIT = 180;
	public static float levelTime = 0;
	public static int levelScore = 0;
	public static OrthographicCamera cam;
	public static int camPos = 0;
	public static float delta;
	public static ArrayList<Enemy> enemy_list;
	public static ArrayList<MovableEntity> drawables;
	public Random r = new Random();
	float stateTime = 0f;
	public static boolean move = true;
	
	//////////UNIVERSAL TEXTURES//////////
	Texture playerTexture;
	
	public LevelRenderer() {
		width = 960;
		height = 480;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		
		batch = new SpriteBatch();
		
		sr = new ShapeRenderer();
		
		drawables = new ArrayList<MovableEntity>();
	}
	
	public LevelRenderer(Level level) {
		this.level = level;
		level.setRenderer(this);
		
		width = 960;
		height = 480;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		
		batch = new SpriteBatch();
		
		sr = new ShapeRenderer();
		
		drawables = new ArrayList<MovableEntity>();
		
		level.generateBackground();
	}
	
	public void render() {
//		Gdx.app.log(RipGame.LOG, "rendering...");
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		delta = Gdx.graphics.getDeltaTime();
		stateTime += delta;
		
		player = level.getPlayer();
		enemy_list = level.getEnemies();
		drawables.add(player);
		drawables.addAll(enemy_list);
		
		cam.update();
		
		batch.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
		
		batch.begin();
		
			level.drawBackground(batch);
		
			drawDrawables();
		
			player.handleTime(this, level, game);
		
			player.handleMovement(this, level, game);
			
			level.drawHud(batch, level.levelHudColor, this);
			
			level.handleCheckPoints(this);
		
			drawables.clear();
		
		batch.end();
		
	}
	
	public void drawDrawables() {
		for (int i = 0; i < drawables.size(); i++) {
			//Gdx.app.log(RipGame.LOG, "Drawables");
			MovableEntity me = drawables.get(i);
			if ((me instanceof Player) && player.getTimeFreeze() == false){
				batch.draw(me.getCurrentFrame(), me.getX(), me.getY());
			} else if (me instanceof Raptor){
				batch.draw(me.getCurrentFrame(), me.getX(), me.getY());
				((Raptor) me).setCurrentFrame(delta);
			} else {
				batch.draw(me.getTexture(), me.getX(), me.getY());
			}
			//sr.rect(me.hitableBox.x, me.hitableBox.y, me.hitableBox.width, me.hitableBox.height);
			//sr.rect(me.getX(), me.getY(), me.hitableBox.width, me.hitableBox.height);
		}
	}
	
	public void setLevel(Level level) {
		this.level = level;
		level.generateBackground();
	}
}
