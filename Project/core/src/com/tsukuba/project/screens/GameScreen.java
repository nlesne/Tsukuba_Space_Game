package com.tsukuba.project.screens;

import java.util.Random;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.tsukuba.project.Assets;
import com.tsukuba.project.SpaceGame;
import com.tsukuba.project.components.*;
import com.tsukuba.project.entities.BulletFactory;
import com.tsukuba.project.entities.EnemyFactory;
import com.tsukuba.project.entities.PlanetFactory;
import com.tsukuba.project.entities.PlayerShipFactory;
import com.tsukuba.project.systems.AISystem;
import com.tsukuba.project.systems.HUDSystem;
import com.tsukuba.project.systems.IndicatorSystem;
import com.tsukuba.project.systems.MovementSystem;
import com.tsukuba.project.systems.RenderingSystem;
import com.tsukuba.project.systems.StarfieldParallaxSystem;

public class GameScreen extends ScreenAdapter {

	private int MAX_PLANET = 200;
	
//	private OrthographicCamera cameraMiniMap;
//	private SpriteBatch batchMiniMap;
	
    private SpaceGame game;
    private PooledEngine engine;
    private OrthographicCamera camera;
	private ShapeRenderer shape;
	private float accumulator = 0f;
	
	private OrthographicCamera hudCamera;
	private SpriteBatch hud;
	
	private OrthographicCamera starfieldCamera;
	private SpriteBatch starfield;
	private Array<Vector3> starArray;
	
    private boolean camera_lock = true;
    
    public GameScreen(SpaceGame game) {
        this.game = game;
        engine = new PooledEngine();
        shape = new ShapeRenderer();
        
        starfield = new SpriteBatch(); 
        hud = new SpriteBatch();
        
        generatePlanets();
        
        //Player
        PlayerShipFactory.create(engine,16,16);

        //Enemy
        EnemyFactory.spawn(engine,EnemyTypeComponent.EnemyType.MINE);
        EnemyFactory.spawn(engine,EnemyTypeComponent.EnemyType.MINE);
        
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderingSystem(game.batch));
        engine.addSystem(new AISystem());
        camera = engine.getSystem(RenderingSystem.class).getCamera();
        engine.addSystem(new IndicatorSystem(camera,game.batch));
        engine.addSystem(new StarfieldParallaxSystem(shape));
        engine.addSystem(new HUDSystem(hud));
        game.batch.setProjectionMatrix(camera.combined);
        
        starfieldCamera = engine.getSystem(RenderingSystem.class).getCamera();
        starfield.setProjectionMatrix(starfieldCamera.combined);
        
        hudCamera = engine.getSystem(RenderingSystem.class).getCamera();
        hud.setProjectionMatrix(hudCamera.combined);
        
//        cameraMiniMap = new OrthographicCamera(800,480);
//        cameraMiniMap.zoom = 4;
//        batchMiniMap = new SpriteBatch();
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
        	PlanetFactory.create(engine,rand.nextInt(1001)-500,rand.nextInt(1001)-500,size,i);
        }
    }
   
    private void handleCamera(OrthographicCamera camera, Entity playerEntity, float delta) {
    	PlayerComponent playerComponent = ComponentList.PLAYER.get(playerEntity);
    	TransformComponent transform = ComponentList.TRANSFORM.get(playerEntity);
    	//DrawableComponent drawable = ComponentList.DRAWABLE.get(playerEntity);
    	
    	if(camera_lock) {
      		camera.position.set(transform.position.x,transform.position.y,0);
    		//camera.position.add(camera.position.cpy().scl(-1).add(transform.position.x, transform.position.y, 0).scl(0.04f));
    		//System.out.println("X : " +  transform.position.x + " / Y : " + transform.position.y);
    	}

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			//camera.zoom += 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			//camera.zoom -= 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
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
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			camera.translate(0, 1, 0);
			//If the UP Key is pressed, translate the camera 3 units in the Y-Axis
			camera_lock = false;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			camera_lock = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
    	    if (accumulator >= playerComponent.shootCooldown) {
                BulletFactory.shoot(engine,playerEntity);
                accumulator = 0f;
            }
        }
		
//	   	batchMiniMap.begin();
//        batchMiniMap.draw(drawable.sprite, transform.position.x, transform.position.y);
//        batchMiniMap.end();
		
    }
    
    private void handleInput(Entity playerEntity, float delta) {

    	MovementComponent movement = ComponentList.MOVEMENT.get(playerEntity);
        TransformComponent transform = ComponentList.TRANSFORM.get(playerEntity);

        float accelX = 0.0f;
        float accelY = 0.0f;
        float rotation = transform.rotation;

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
        	rotation += 0.05f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
        	rotation -= 0.05f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            accelX = (float) (0.3*Math.cos(rotation+Math.PI/2));
            accelY = (float) (0.3*Math.sin(rotation+Math.PI/2));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
        	accelX = -(float) (0.3*Math.cos(rotation+Math.PI/2));
        	accelY = -(float) (0.3*Math.sin(rotation+Math.PI/2));
        }

        // Enter a planet
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
        	int id = 3;
        	enterAPlanet(id);
        }
        
        movement.velocity.add(accelX,accelY);
        transform.rotation=rotation;
    }
    
    private void enterAPlanet(int id) {
    	//Not every hangar are the same in function of their ID
    	switch(id) {
    	case 3 :
    		game.setScreen(new PlanetHangarScreen(game, this, engine)); 
    		break;
    	case 4 :
    		//Change the screen
    		break;
    	}
    }
}

