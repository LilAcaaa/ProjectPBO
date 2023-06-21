package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public abstract class Game extends ApplicationAdapter {
	private Texture texture;
	private Rectangle bounds;
	private int highScore = 0;
	private SpriteBatch batch;

	//Textures
	private Texture PlayerTexture;
	private Texture EnemyTexture;
	private Texture PlayerSkillTexture;
	private Texture ProjectileTexture;

	private Texture HeartsTexture;

	//hitbox
	private Rectangle projectile;
	private Rectangle player;
	private Rectangle skill;
	private Array<Rectangle> aliens;
	private Array<Rectangle> projectiles;

	//sound effect
	private Sound explodingSound;
	private Sound shootSound;
	private Sound crashSound;

	//spawn
	private long lastSpawnTime = 0;
	private OrthographicCamera camera;

	//score
	private int score = 0;
	private BitmapFont font;
	private int hearts = 3;

	// Projectile state
	private boolean isProjectileActive = false;

	private boolean gameStarted = false;
	private boolean gameOver = false;

	public Game() {

	}

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

		batch = new SpriteBatch();
		HeartsTexture = new Texture("heart.png");
		PlayerTexture = new Texture("player.png");
		EnemyTexture = new Texture("alien_type1.png");
		ProjectileTexture = new Texture("projectile.png");
		PlayerSkillTexture = new Texture("SkillUp.png");
		font = new BitmapFont();
		font.getData().setScale(2);

		explodingSound = Gdx.audio.newSound(Gdx.files.internal("explodingSound.wav"));
		shootSound = Gdx.audio.newSound(Gdx.files.internal("shootSound.mp3"));

		player = new com.badlogic.gdx.math.Rectangle();
		player.x = 800 / 2 - 64 / 2;
		player.y = 20;
		player.width = 64;
		player.height = 64;

		aliens = new Array<Rectangle>();
		projectiles = new Array<Rectangle>();
	}

	private void spawnEnemy() {
		Rectangle alien = new Rectangle();
		alien.y = 600;
		alien.x = MathUtils.random(0, 800 - 64);
		alien.setHeight(64);
		alien.setWidth(64);
		this.aliens.add(alien);
		lastSpawnTime = TimeUtils.nanoTime();
	}

	private void spawnProjectile() {
		if (!isProjectileActive) {
			Rectangle projectile = new Rectangle();
			projectile.y = player.y;
			projectile.x = player.x + 15;
			projectile.setHeight(32);
			projectile.setWidth(32);
			projectiles.add(projectile);
			isProjectileActive = true;
			shootSound.play();
		}
	}

	private void restartGame() {
		score = 0;
		hearts = 3;
		aliens.clear();
		projectiles.clear();
		gameStarted = false;
		gameOver = false;
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0.2f, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		if (score > highScore) {
			highScore = score;
		}
		font.draw(batch, "High Score: " + highScore, 600, 50);

		if (!gameStarted) {
			font.draw(batch, "Press ENTER to Start", 250, 325);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				gameStarted = true;
			}
		} else if (!gameOver) {
			font.draw(batch, hearts + "", 770, 580);
			batch.draw(PlayerTexture, player.x, player.y);

			for (Rectangle alien : aliens) {
				batch.draw(EnemyTexture, alien.x, alien.y);
			}
			for (Rectangle projectile : projectiles) {
				batch.draw(ProjectileTexture, projectile.x, projectile.y);
			}
			font.draw(batch, score + "", 700, 550);

			if (hearts != 0) {
				//cek kiri kanan
				if (Gdx.input.isKeyPressed(Input.Keys.A)) {
					player.x = player.x - 200 * Gdx.graphics.getDeltaTime() - 1;
				}
				if (Gdx.input.isKeyPressed(Input.Keys.D)) {
					player.x = player.x + 200 * Gdx.graphics.getDeltaTime() + 1;
				}
				if (Gdx.input.isKeyPressed(Input.Keys.S)) {
					player.y = player.y - 200 * Gdx.graphics.getDeltaTime() - 1;
				}
				if (Gdx.input.isKeyPressed(Input.Keys.W)) {
					player.y = player.y + 200 * Gdx.graphics.getDeltaTime() + 1;
				}

				//batas agar tidak out of bound
				if (player.x < 0) {
					player.x = 0;
				}
				if (player.x > 800 - 64) {
					player.x = 800 - 64;
				}
				if (player.y < 0) {
					player.y = 0;
				}
				if (player.y > 600 - 64) {
					player.y = 600 - 64;
				}

				//spawn aliens
				if (TimeUtils.nanoTime() - lastSpawnTime > 1000000000) {
					spawnEnemy();
				}
				//spawn projectiles
				if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
					spawnProjectile();
				}

				for (Iterator<Rectangle> alIter = aliens.iterator(); alIter.hasNext(); ) {
					Rectangle alien = alIter.next();
					alien.y -= 100 * Gdx.graphics.getDeltaTime();

					// Remove aliens that go off-screen
					if (alien.y + alien.height < 0) {
						alIter.remove();
					}

					// Check for collisions between aliens and the player
					if (alien.overlaps(player)) {
						explodingSound.play();
						hearts--;
						alIter.remove();
					}

					//update speed alien (up difficulty)
					if (score > 100) {
						alien.y -= 150 * Gdx.graphics.getDeltaTime();
					}

					// Check for collisions between aliens and projectiles
					for (Iterator<Rectangle> projIter = projectiles.iterator(); projIter.hasNext(); ) {
						Rectangle projectile = projIter.next();
						projectile.y += 200 * Gdx.graphics.getDeltaTime();

						// Remove projectiles that go off-screen
						if (projectile.y > 600) {
							projIter.remove();
							isProjectileActive = false;
						}

						// Check for collisions between aliens and projectiles
						if (alien.overlaps(projectile)) {
							explodingSound.play();
							score += 10;
							alIter.remove();
							projIter.remove();
							isProjectileActive = false;
						}
					}
				}
			} else {
				gameOver = true;
			}
		} else {
			font.draw(batch, "GAME OVER", 320, 300);
			font.draw(batch, "Press R to Restart", 300, 250);
			if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
				restartGame();
				if (score > highScore) {
					highScore = score;
				}
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		PlayerTexture.dispose();
		EnemyTexture.dispose();
		HeartsTexture.dispose();
		ProjectileTexture.dispose();
		HeartsTexture.dispose();
		explodingSound.dispose();
		font.dispose();
	}

	public abstract void update(float deltaTime);
}
