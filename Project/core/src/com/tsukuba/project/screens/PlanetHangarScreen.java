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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.tsukuba.project.Assets;
import com.tsukuba.project.SpaceGame;
import com.tsukuba.project.components.ComponentList;
import com.tsukuba.project.components.EnemyComponent;
import com.tsukuba.project.components.PlayerComponent;
import com.tsukuba.project.components.QuestComponent;
import com.tsukuba.project.entities.EnemyFactory;

import java.util.Random;

public class PlanetHangarScreen extends ScreenAdapter{
	private PooledEngine engine;
	private ScreenAdapter parent;
	private SpaceGame game;
	private OrthographicCamera camera;
	
	private TextureRegion backgroundRegion;
	private TextureRegion pilotRegion;
	private TextureRegion engineerRegion;
	
	private Image background;
	private Image engineer;
	private Image pilot;
	
	protected Stage stage;
	protected Skin skin;

	public PlanetHangarScreen(SpaceGame game, ScreenAdapter parent, PooledEngine engine) {
		this.game = game;
		this.parent = parent;
		this.engine = engine;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		backgroundRegion = new TextureRegion(Assets.hangarBackground);
		background = new Image(backgroundRegion);
		engineerRegion = new TextureRegion(Assets.engineer);
		engineer = new Image(engineerRegion);
		pilotRegion = new TextureRegion(Assets.pilot);
		pilot = new Image(pilotRegion);
		
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
	}	 

	@Override
	public void render(float delta) {
		//Camera
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

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
				Entity player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
				if (!ComponentList.QUEST.has(player)) {
					final QuestComponent quest = engine.createComponent(QuestComponent.class);
					Random random = new Random();
					quest.progress = 0;
					if (random.nextInt() > 0.5) {
						quest.type = QuestComponent.QuestType.KILL;
						quest.objective = 2 + random.nextInt(3);
						quest.reward = quest.objective * 10;
						for (int i = 0; i < quest.objective; i++) {
							EnemyFactory.spawn(engine, EnemyComponent.EnemyType.MINE);
						}
					}
					else {
						quest.type = QuestComponent.QuestType.SURVIVE;
						quest.objective = 120;
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
								EnemyFactory.spawn(engine,EnemyComponent.EnemyType.MINE);
							}
						},0,10,quest.objective/10);
					}
					player.add(quest);
				}
				else {
					QuestComponent quest = ComponentList.QUEST.get(player);
					if (quest.progress >= quest.objective) {
						player.remove(QuestComponent.class);
						PlayerComponent playerComponent = ComponentList.PLAYER.get(player);
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
				game.setScreen(parent); 
				dispose();
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
			game.setScreen(parent); 
		}
	}
	
	private void changeToUpgradeScreen() {
		game.setScreen(new UpgradeScreen(game, this, engine));
	}
}
