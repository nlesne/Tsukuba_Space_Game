package com.tsukuba.project.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.tsukuba.project.Assets;
import com.tsukuba.project.SpaceGame;
import com.tsukuba.project.components.ComponentList;
import com.tsukuba.project.components.MovementComponent;
import com.tsukuba.project.components.PlayerComponent;
import com.tsukuba.project.components.UpgradeComponent;

public class UpgradeScreen extends ScreenAdapter {
	private PooledEngine engine;
	private ScreenAdapter parent;
	private SpaceGame game;
	private OrthographicCamera camera;
	
	private TextureRegion backgroundRegion;
	private TextureRegion engineerRegion;
	
	private Image background;
	private Image engineer;
	
	protected Stage stage;
	protected Skin skin;
	
	public UpgradeScreen(SpaceGame game, ScreenAdapter parent, PooledEngine engine) {
		this.game = game;
		this.parent = parent;
		this.engine = engine;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		backgroundRegion = new TextureRegion(Assets.hangarBackground);
		background = new Image(backgroundRegion);
		engineerRegion = new TextureRegion(Assets.engineer);
		engineer = new Image(engineerRegion);
		
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
		Entity playerEntity = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
		final UpgradeComponent upgrade = ComponentList.UPGRADE.get(playerEntity);
		
		//Buttons
		final TextButton upgrade1 = new TextButton("Core Upgrade", skin);
		upgrade1.setWidth(100f);
		upgrade1.setHeight(85f);
		upgrade1.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2);
		final TextButton upgrade2 = new TextButton("Turret Upgrade", skin);
		upgrade2.setWidth(100f);
		upgrade2.setHeight(85f);
		upgrade2.setPosition(Gdx.graphics.getWidth() /2 - 50f, Gdx.graphics.getHeight()/2);
		final TextButton upgrade3 = new TextButton("Rocket Upgrade", skin);
		upgrade3.setWidth(100f);
		upgrade3.setHeight(85f);
		upgrade3.setPosition(Gdx.graphics.getWidth() /2 + 100f, Gdx.graphics.getHeight()/2);
		

		TextButton exit = new TextButton("Exit", skin);
		exit.setWidth(100f);
		exit.setHeight(40f);
		exit.setPosition(Gdx.graphics.getWidth() /2 - 50f, Gdx.graphics.getHeight() - 75f);
		
		//Listeners
		upgrade1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(upgrade.coreLevel<2) {
					upgrade.coreLevel++;
					upgrade1.setTouchable(Touchable.disabled);
				}
			}
		});
		upgrade2.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(upgrade.turretLevel<2) {
					upgrade.turretLevel++;
					upgrade2.setTouchable(Touchable.disabled);
				}
			}
		});
		upgrade3.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(upgrade.rocketLevel<2) {
					upgrade.rocketLevel++;
					upgrade3.setTouchable(Touchable.disabled);
				}
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
		engineer.setScale(0.35f);
		engineer.setOrigin(Gdx.graphics.getWidth() /2, 75f);
		//Images
		stage.addActor(background);
		stage.addActor(engineer);
		
		//Buttons
		stage.addActor(exit);
		stage.addActor(upgrade1);
		stage.addActor(upgrade2);
		stage.addActor(upgrade3);

		Gdx.input.setInputProcessor(stage);
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			game.setScreen(parent); 
		}
	}
	
}
