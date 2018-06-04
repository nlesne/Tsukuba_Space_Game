package com.tsukuba.project.screens;

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
import com.tsukuba.project.Assets;
import com.tsukuba.project.SpaceGame;

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
		TextButton quest = new TextButton("Quest", skin);
		quest.setWidth(100f);
		quest.setHeight(30f);
		quest.setPosition(Gdx.graphics.getWidth() /2 - 200f, 75f);
		TextButton exit = new TextButton("Exit", skin);
		exit.setWidth(100f);
		exit.setHeight(40f);
		exit.setPosition(Gdx.graphics.getWidth() /2 - 50f, Gdx.graphics.getHeight() - 75f);
		TextButton upgrade = new TextButton("Upgrade", skin);
		upgrade.setWidth(100f);
		upgrade.setHeight(30f);
		upgrade.setPosition(Gdx.graphics.getWidth() /2 + 100f, 75f);

		//Listeners
		quest.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// Activate a random quest
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
		stage.addActor(quest);
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
