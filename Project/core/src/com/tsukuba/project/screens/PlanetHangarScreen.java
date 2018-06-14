package com.tsukuba.project.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.tsukuba.project.Assets;
import com.tsukuba.project.SpaceGame;
import com.tsukuba.project.components.*;
import com.tsukuba.project.entities.EnemyFactory;

import java.util.Random;

public class PlanetHangarScreen extends ScreenAdapter{
	private PooledEngine engine;
	private ScreenAdapter parent;
	private SpaceGame game;
	private OrthographicCamera camera;
	private Entity player;
	private Entity planet;
	
	private TextureRegion backgroundRegion;
	private TextureRegion pilotRegion;
	private TextureRegion engineerRegion;
	
	private Image background;
	private Image engineer;
	private Image pilot;
	
	protected Stage stage;
	protected Skin skin;

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width,height);
		background.setHeight(height);
		background.setWidth(width);

	}

	public PlanetHangarScreen(SpaceGame game, ScreenAdapter parent, PooledEngine engine, Entity planet) {
		this.game = game;
		this.parent = parent;
		this.engine = engine;
		this.planet = planet;
		player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		backgroundRegion = new TextureRegion(Assets.hangarBackground);
		background = new Image(backgroundRegion);
		engineerRegion = new TextureRegion(Assets.engineer);
		engineer = new Image(engineerRegion);
		pilotRegion = new TextureRegion(Assets.pilot);
		pilot = new Image(pilotRegion);
		
		stage = new Stage();
		stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		background.setWidth(Gdx.graphics.getWidth());
		background.setHeight(Gdx.graphics.getWidth());
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		PlayerComponent playerComponent = ComponentList.PLAYER.get(player);
		playerComponent.respawnPlanet = planet;

		for (Entity enemy : engine.getEntitiesFor(Family.all(AIComponent.class,EnemyComponent.class).get())) {
			EnemyComponent enemyComponent = ComponentList.ENEMY.get(enemy);
			if (enemyComponent.type != EnemyComponent.EnemyType.BOSS)
				engine.removeEntity(enemy);
			else {
				TransformComponent bossTransform = ComponentList.TRANSFORM.get(enemy);
				TransformComponent planetTransform = ComponentList.TRANSFORM.get(planet);
				float x = planetTransform.position.x + MathUtils.random(-256,256);
				float y = planetTransform.position.y + MathUtils.random(-256,256);
				bossTransform.position.set(x,y,2);

				AIComponent bossAI = ComponentList.AI.get(enemy);
				bossAI.state = AIComponent.AIState.IDLE;
			}
		}

		for (Entity projectile : engine.getEntitiesFor(Family.all(ProjectileComponent.class).get())) {
			engine.removeEntity(projectile);
		}

		MovementComponent playerMovement = ComponentList.MOVEMENT.get(player);
		playerMovement.velocity.setZero();
		playerMovement.acceleration.setZero();

		engine.clearPools();

	}	 

	@Override
	public void render(float delta) {
		//Camera
		camera.update();
		//game.batch.setProjectionMatrix(camera.combined);

		//Batch
		stage.act(delta);
		stage.draw();

		//Inputs
		handleInput();
	}

	@Override
	public void show() {
		//Buttons
		TextButton questBtn = new TextButton("Quest", skin);
		questBtn.setWidth(100f);
		questBtn.setHeight(30f);
		questBtn.setPosition(Gdx.graphics.getWidth() /2 - 200f, 75f);
		TextButton exit = new TextButton("Exit", skin);
		exit.setWidth(100f);
		exit.setHeight(40f);
		exit.setPosition(Gdx.graphics.getWidth() /2 - 50f, Gdx.graphics.getHeight() - 75f);
		TextButton upgrade = new TextButton("Upgrade", skin);
		upgrade.setWidth(100f);
		upgrade.setHeight(30f);
		upgrade.setPosition(Gdx.graphics.getWidth() /2 + 100f, 75f);

		//Listeners
		questBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PlayerComponent playerComponent = ComponentList.PLAYER.get(player);
				if (!ComponentList.QUEST.has(player)) {
					final QuestComponent quest = engine.createComponent(QuestComponent.class);
					final Random random = new Random();
					quest.progress = 0;
					if (random.nextFloat() > 0.5) {
						quest.type = QuestComponent.QuestType.KILL;
						quest.objective = 3 + random.nextInt(4);
						quest.reward = quest.objective * 10;
						for (int i = 0; i < quest.objective; i++) {
							if (i%2 == 0)
								EnemyFactory.spawn(engine, EnemyComponent.EnemyType.MINE);
							else
								EnemyFactory.spawn(engine,EnemyComponent.EnemyType.SHOOTER);
						}
					}
					else {
						quest.type = QuestComponent.QuestType.SURVIVE;
						quest.objective = 60;
						quest.reward = 50;
						Timer.schedule(new Timer.Task() {
							@Override
							public void run() {
								quest.progress++;
							}
						},1,1,quest.objective);

						Timer.schedule(new Timer.Task() {
							@Override
							public void run() {
								if (random.nextFloat() > 0.4)
									EnemyFactory.spawn(engine,EnemyComponent.EnemyType.MINE);
								else
									EnemyFactory.spawn(engine,EnemyComponent.EnemyType.SHOOTER);
							}
						},0,quest.objective/10,quest.objective/10-1);
					}
					player.add(quest);
				}
				else {
					QuestComponent quest = ComponentList.QUEST.get(player);
					if (quest.progress >= quest.objective) {
						player.remove(QuestComponent.class);
						playerComponent.money += quest.reward;
					}
				}
			}
		});
		upgrade.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// Go to the Upgrade Menu
				changeToUpgradeScreen();
			}
		});
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onExit();
			}
		});
		
		//Images properties
		background.toBack();
		pilot.setScale(0.3f);
		pilot.setOrigin(Gdx.graphics.getWidth() /2 - 180f, 165f);
		engineer.setScale(0.25f);
		engineer.setOrigin(Gdx.graphics.getWidth() /2 + 220f, 160f);
		//Images
		stage.addActor(background);
		stage.addActor(pilot);
		stage.addActor(engineer);
		
		//Buttons
		stage.addActor(questBtn);
		stage.addActor(upgrade);
		stage.addActor(exit);

		Gdx.input.setInputProcessor(stage);
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			onExit();
		}
	}
	
	private void changeToUpgradeScreen() {
		game.setScreen(new UpgradeScreen(game, this, engine));
	}

	private void onExit() {
		TransformComponent playerTransform = ComponentList.TRANSFORM.get(player);
		TransformComponent planetTransform = ComponentList.TRANSFORM.get(planet);
		playerTransform.position.set(planetTransform.position);
		HealthComponent playerHealth = ComponentList.HEALTH.get(player);
		playerHealth.currentHealth = playerHealth.maxHealth;
		game.setScreen(parent);
		dispose();
	}
}
