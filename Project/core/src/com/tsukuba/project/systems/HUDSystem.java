package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.ComponentList;
import com.tsukuba.project.components.HealthComponent;
import com.tsukuba.project.components.PlayerComponent;
import com.tsukuba.project.components.TransformComponent;
import com.tsukuba.project.components.UpgradeComponent;

public class HUDSystem extends IteratingSystem{

	private SpriteBatch batch;
	private Sprite heart;
	private Sprite core;
	private Sprite rocket;
	private Sprite turret;
	private ShapeRenderer shape;

	public HUDSystem(SpriteBatch batch) {
		super(Family.all(PlayerComponent.class).get());

		shape = new ShapeRenderer();

		heart = new Sprite(Assets.hud_fullH);
		core = new Sprite(Assets.hud_core);
		rocket = new Sprite(Assets.hud_rocket);
		turret = new Sprite(Assets.hud_turret);

		this.batch = batch;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Entity playerEntity = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
		HealthComponent playerHealth = ComponentList.HEALTH.get(playerEntity);

		//		shape.begin(ShapeType.Line);
		//		shape.setColor(Color.RED);
		//		shape.circle(100, 100, 5);
		//		shape.end();

		//Hearts

		heart = new Sprite(Assets.hud_fullH);

		batch.begin();
		for(int i=0; i<playerHealth.maxHealth; i++) {
			if(i>playerHealth.currentHealth) {
				heart = new Sprite(Assets.hud_emptyH);
			}
			heart.setSize(1.5f, 2f);
			heart.setPosition(-4+i*1.5f,13f);	
			heart.setOriginCenter();
			heart.draw(batch);
		}

		//Spaceship state

		checkSpriteUpgrade(playerEntity);

		core.setSize(5f, 5f);
		core.setPosition(7f,-13f);	
		core.setOriginCenter();
		core.draw(batch);

		rocket.setSize(3f, 3f);
		rocket.setPosition(4f,-13f);	
		rocket.setOriginCenter();
		rocket.draw(batch);

		rocket.setSize(3f, 3f);
		rocket.setPosition(11.9f,-13f);	
		rocket.setOriginCenter();
		rocket.draw(batch);

		turret.setSize(3f, 3f);
		turret.setPosition(8f,-8f);	
		turret.setOriginCenter();
		turret.draw(batch);

		batch.end(); 			
	}

	private void checkSpriteUpgrade(Entity playerEntity) {
		UpgradeComponent playerUpgrade = ComponentList.UPGRADE.get(playerEntity);

		//Core Upgrade
		switch(playerUpgrade.coreLevel) {
		case 1 :
			break;
		case 2:
			break;
		case 3:
			break;
			//Add here others sprites upgrade
		}

		//Rocket Upgrade
		switch(playerUpgrade.rocketLevel) {
		case 1 :
			rocket = new Sprite(Assets.hud_rocket);
			break;
		case 2:
			rocket = new Sprite(Assets.hud_rocket2);
			break;
		case 3:
			break;
			//Add here others sprites upgrade
		}

		//Turret Upgrade
		switch(playerUpgrade.turretLevel) {
		case 1 :
			break;
		case 2:
			break;
		case 3:
			break;
			//Add here others sprites upgrade
		}
	}
}
