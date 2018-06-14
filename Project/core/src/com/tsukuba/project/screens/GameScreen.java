package com.tsukuba.project.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tsukuba.project.Assets;
import com.tsukuba.project.SpaceGame;
import com.tsukuba.project.components.*;
import com.tsukuba.project.entities.*;
import com.tsukuba.project.systems.*;

import java.util.Random;

public class GameScreen extends ScreenAdapter {

	private int MAX_PLANET = 5;

//	private OrthographicCamera cameraMiniMap;
//	private SpriteBatch batchMiniMap;

    private SpaceGame game;
    private PooledEngine engine;
    private ShapeRenderer shape;
    private OrthographicCamera camera;
	private float accumulator = 0f;

	private OrthographicCamera hudCamera;
	private SpriteBatch hud;

	private OrthographicCamera starfieldCamera;
	private SpriteBatch starfield;

	private boolean camera_lock = true;

	public GameScreen(SpaceGame game) {
		this.game = game;
		shape = new ShapeRenderer();

		starfield = new SpriteBatch();
		hud = new SpriteBatch();

        initEngine();
        generatePlanets();

        PlayerShipFactory.create(engine,16,16);
        BossFactory.spawn(engine);

        game.batch.setProjectionMatrix(camera.combined);

		starfieldCamera = engine.getSystem(RenderingSystem.class).getCamera();
		starfield.setProjectionMatrix(starfieldCamera.combined);

		hudCamera = engine.getSystem(RenderingSystem.class).getCamera();
		hud.setProjectionMatrix(hudCamera.combined);

		//        cameraMiniMap = new OrthographicCamera(800,480);
		//        cameraMiniMap.zoom = 4;
		//        batchMiniMap = new SpriteBatch();
	}

    private void initEngine() {
        engine = new PooledEngine();

        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderingSystem(game.batch));
        engine.addSystem(new AISystem(engine));
        engine.addSystem(new ProjectileSystem(engine));
        engine.addSystem(new HitboxUpdateSystem(game.batch));
        camera = engine.getSystem(RenderingSystem.class).getCamera();
        engine.addSystem(new IndicatorSystem(camera,game.batch));
        engine.addSystem(new CollisionDetectionSystem(engine));
        engine.addSystem(new CollisionResolveSystem(engine,game,this));
        engine.addSystem(new StarfieldParallaxSystem(shape));
        engine.addSystem(new HUDSystem(hud));
        engine.addSystem(new DebugSystem(game.batch));
        engine.getSystem(DebugSystem.class).setProcessing(false);
    }

    @Override
	public void render(float delta) {
		engine.update(delta);
		//cameraMiniMap.update();
		//batchMiniMap.setProjectionMatrix(cameraMiniMap.combined);

		Entity playerEntity = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first();

		accumulator += delta;
		handleCamera(camera, playerEntity, delta);
		handleInput(playerEntity,delta);
	}


   
    private void generatePlanets() {
    	Random rand = new Random();
        
        //Planet
        for(int i = 0; i < MAX_PLANET; i++) {  
        	// P(0) = 0.7, P(1) = 0.2, P(2) = 0.05, P(3) = 0.049, P(4) = 0.001.
        	int size = rand.nextInt(100)+1;
        	if(size<=70)
        		size = 0;
        	if(size>70 && size<=90)
        		size = 1;
        	if(size>90 && size<=95)
        		size = 2;
        	if(size>95 && size<=99)
        		size = 3;
        	if(size>99)
        		size = 4;
        	PlanetFactory.create(engine,rand.nextInt(64)-32,rand.nextInt(64)-32,size);
        }
    }


	private void handleCamera(OrthographicCamera camera, Entity playerEntity, float delta) {

		PlayerComponent playerComponent = ComponentList.PLAYER.get(playerEntity);
		TransformComponent transform = ComponentList.TRANSFORM.get(playerEntity);
		UpgradeComponent upgrade = ComponentList.UPGRADE.get(playerEntity);

		if(camera_lock) {
			camera.position.set(transform.position.x,transform.position.y,0);
			//camera.position.add(camera.position.cpy().scl(-1).add(transform.position.x, transform.position.y, 0).scl(0.04f));
			//System.out.println("X : " +  transform.position.x + " / Y : " + transform.position.y);
		}

        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
			camera.zoom += camera.zoom <= 4 ? 0.02 : 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.C)) {
			camera.zoom -= camera.zoom > 0.04 ? 0.02 : 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.translate(-1, 0, 0);
			//If the LEFT Key is pressed, translate the camera -3 units in the X-Axis
			camera_lock = false;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			camera.translate(1, 0, 0);
			//If the RIGHT Key is pressed, translate the camera 3 units in the X-Axis
			camera_lock = false;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			camera.translate(0, -1, 0);
			//If the DOWN Key is pressed, translate the camera -3 units in the Y-Axis
			camera_lock = false;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Z) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			camera.translate(0, 1, 0);
			//If the UP Key is pressed, translate the camera 3 units in the Y-Axis
			camera_lock = false;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			camera_lock = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F)) {
			if (accumulator >= playerComponent.baseShootCooldown - upgrade.weaponLevel/20f + 0.05) {
				BulletFactory.shoot(engine,playerEntity);
				accumulator = 0f;
			}
		}
	}

	private void handleInput(Entity playerEntity, float delta) {

		MovementComponent movement = ComponentList.MOVEMENT.get(playerEntity);
		TransformComponent transform = ComponentList.TRANSFORM.get(playerEntity);
		DrawableComponent drawable = ComponentList.DRAWABLE.get(playerEntity);
		UpgradeComponent upgrade = ComponentList.UPGRADE.get(playerEntity);

		drawable.sprite = new Sprite(Assets.spaceship);

        float accelX = 0.0f;
        float accelY = 0.0f;
        float turningRate = 0.03f + upgrade.thrustersLevel/100f;
        float accelPow = 0.2f + upgrade.thrustersLevel/10f;
        float rotation = transform.rotation;

		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
			rotation += turningRate;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
			rotation -= turningRate;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
			drawable.sprite = new Sprite(Assets.spaceship_firing);
			accelX = (float) (accelPow*Math.cos(rotation+Math.PI/2));
			accelY = (float) (accelPow*Math.sin(rotation+Math.PI/2));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
			accelX = -(float) (accelPow*Math.cos(rotation+Math.PI/2));
			accelY = -(float) (accelPow*Math.sin(rotation+Math.PI/2));
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
			EntitySystem debugSystem = engine.getSystem(DebugSystem.class);
			debugSystem.setProcessing(!debugSystem.checkProcessing());
		}
		movement.velocity.add(accelX,accelY);
		transform.rotation=rotation;
	}
}
