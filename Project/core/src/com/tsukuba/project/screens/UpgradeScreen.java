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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tsukuba.project.Assets;
import com.tsukuba.project.SpaceGame;
import com.tsukuba.project.components.*;

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

	Entity playerEntity;

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
    }

    public UpgradeScreen(SpaceGame game, ScreenAdapter parent, PooledEngine engine) {
		this.game = game;
		this.parent = parent;
		this.engine = engine;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		backgroundRegion = new TextureRegion(Assets.hangarBackground);
		background = new Image(backgroundRegion);
		engineerRegion = new TextureRegion(Assets.engineer);
		engineer = new Image(engineerRegion);
		
		stage = new Stage();
		stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		playerEntity = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
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
		final UpgradeComponent upgrade = ComponentList.UPGRADE.get(playerEntity);
		final PlayerComponent playerComponent = ComponentList.PLAYER.get(playerEntity);

		int coreUpgradeCost = upgrade.coreLevel * 25;
		int weaponUpgradeCost = upgrade.weaponLevel * 50;
		int thrustersUpgradeCost = upgrade.thrustersLevel * 30;


		//Buttons
		final TextButton upgrade1 = new TextButton("Core\n+ HP\n- Hitbox", skin);
		upgrade1.setWidth(120f);
		upgrade1.setHeight(100f);
		upgrade1.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2);
		final TextButton upgrade2 = new TextButton("Weapon\n+ Bullet Damage\n+ Bullet Speed\n- Bullet Spread", skin);
		upgrade2.setWidth(120f);
		upgrade2.setHeight(100f);
		upgrade2.setPosition(Gdx.graphics.getWidth() /2 - 50f, Gdx.graphics.getHeight()/2);
		final TextButton upgrade3 = new TextButton("Thrusters\n+ Acceleration\n+ Turning Rate", skin);
		upgrade3.setWidth(120f);
		upgrade3.setHeight(100f);
		upgrade3.setPosition(Gdx.graphics.getWidth() /2 + 100f, Gdx.graphics.getHeight()/2);

		if (upgrade.coreLevel == 3) {
			upgrade1.setText("Fully upgraded");
			upgrade1.setDisabled(true);
			upgrade1.setTouchable(Touchable.disabled);
		}
		if (upgrade.weaponLevel == 3) {
			upgrade2.setText("Fully upgraded");
			upgrade2.setDisabled(true);
			upgrade2.setTouchable(Touchable.disabled);
		}
		if (upgrade.thrustersLevel == 3) {
			upgrade3.setText("Fully upgraded");
			upgrade3.setDisabled(true);
			upgrade3.setTouchable(Touchable.disabled);
		}
		

		TextButton exit = new TextButton("Exit", skin);
		exit.setWidth(100f);
		exit.setHeight(40f);
		exit.setPosition(Gdx.graphics.getWidth() /2 - 50f, Gdx.graphics.getHeight() - 75f);

		//Labels
		final Label coreUpgradeCostLabel = new Label("Cost: "+ coreUpgradeCost,skin);
		coreUpgradeCostLabel.setPosition(upgrade1.getX()+upgrade1.getWidth()/2-coreUpgradeCostLabel.getWidth()/2,upgrade1.getY()-30);


		final Label weaponUpgradeCostLabel = new Label("Cost: "+ weaponUpgradeCost,skin);
		weaponUpgradeCostLabel.setPosition(upgrade2.getX()+upgrade2.getWidth()/2-weaponUpgradeCostLabel.getWidth()/2,upgrade2.getY()-30);

		final Label thrustersUpgradeCostLabel = new Label("Cost: "+ thrustersUpgradeCost,skin);
		thrustersUpgradeCostLabel.setPosition(upgrade3.getX()+upgrade3.getWidth()/2-thrustersUpgradeCostLabel.getWidth()/2,upgrade3.getY()-30);

		final Label moneyLabel = new Label("Money: " + playerComponent.money,skin);
		moneyLabel.setPosition(Gdx.graphics.getWidth()/2-moneyLabel.getWidth()/2,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/4);


		//Listeners
		upgrade1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				int coreUpgradeCost = upgrade.coreLevel * 25;
				coreUpgradeCostLabel.setText("Cost: " + coreUpgradeCost);
				if(upgrade.coreLevel < 3 && playerComponent.money >= coreUpgradeCost) {
					HitboxComponent hitbox = ComponentList.HITBOX.get(playerEntity);
					upgrade.coreLevel++;
					hitbox.shape.scale(-1f/upgrade.coreLevel);
					playerComponent.money -= coreUpgradeCost;
                    HealthComponent playerHealth = ComponentList.HEALTH.get(playerEntity);
                    playerHealth.maxHealth += 2;
					moneyLabel.setText("Money: " + playerComponent.money);
					coreUpgradeCost = upgrade.coreLevel * 25;
					coreUpgradeCostLabel.setText("Cost: " + coreUpgradeCost);
				}
				if (upgrade.coreLevel > 2) {
					upgrade1.setTouchable(Touchable.disabled);
					upgrade1.setDisabled(true);
					upgrade1.setText("Fully upgraded");
				}
			}
		});
		upgrade2.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				int weaponUpgradeCost = upgrade.weaponLevel * 50;
				weaponUpgradeCostLabel.setText("Cost: " + weaponUpgradeCost);
				if(upgrade.weaponLevel < 3 && playerComponent.money >= weaponUpgradeCost) {
					upgrade.weaponLevel++;
					playerComponent.money -= weaponUpgradeCost;
                    moneyLabel.setText("Money: " + playerComponent.money);
					weaponUpgradeCost = upgrade.weaponLevel * 50;
					weaponUpgradeCostLabel.setText("Cost: " + weaponUpgradeCost);
				}
				if (upgrade.weaponLevel > 2) {
					upgrade2.setTouchable(Touchable.disabled);
					upgrade2.setDisabled(true);
					upgrade2.setText("Fully upgraded");
				}
			}
		});
		upgrade3.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				int thrustersUpgradeCost = upgrade.thrustersLevel * 30;
				thrustersUpgradeCostLabel.setText("Cost: " + thrustersUpgradeCost);
				if(upgrade.thrustersLevel < 3 && playerComponent.money >= thrustersUpgradeCost) {
					upgrade.thrustersLevel++;
					playerComponent.money -= thrustersUpgradeCost;
                    moneyLabel.setText("Money: " + playerComponent.money);
					thrustersUpgradeCost = upgrade.thrustersLevel * 30;
					thrustersUpgradeCostLabel.setText("Cost: " + thrustersUpgradeCost);
				}
				if (upgrade.thrustersLevel > 2) {
					upgrade3.setTouchable(Touchable.disabled);
					upgrade3.setText("Fully upgraded");
					upgrade3.setDisabled(true);
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
		engineer.setOrigin(Gdx.graphics.getWidth() /2, 0);
		//Images
		stage.addActor(background);
		stage.addActor(engineer);
		
		//Buttons
		stage.addActor(exit);
		stage.addActor(upgrade1);
		stage.addActor(upgrade2);
		stage.addActor(upgrade3);

		//Labels
		stage.addActor(coreUpgradeCostLabel);
		stage.addActor(weaponUpgradeCostLabel);
		stage.addActor(thrustersUpgradeCostLabel);
		stage.addActor(moneyLabel);

		Gdx.input.setInputProcessor(stage);
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			game.setScreen(parent); 
		}
	}
	
}
