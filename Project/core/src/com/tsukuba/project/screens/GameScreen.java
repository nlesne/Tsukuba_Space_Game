package com.tsukuba.project.screens;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tsukuba.project.SpaceGame;
import com.tsukuba.project.components.*;
import com.tsukuba.project.entities.PlayerShipFactory;
import com.tsukuba.project.systems.MovementSystem;
import com.tsukuba.project.systems.RenderingSystem;

public class GameScreen extends ScreenAdapter {

    private SpaceGame game;
    private PooledEngine engine;

    private boolean camera_lock = true;
    
    public GameScreen(SpaceGame game) {
        this.game = game;
        engine = new PooledEngine();
        PlayerShipFactory.create(engine,16,16);

        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderingSystem(game.batch));

    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        
        OrthographicCamera camera = engine.getSystem(RenderingSystem.class).getCamera();
        game.batch.setProjectionMatrix(camera.combined);
        
        Family player = Family.all(PlayerComponent.class).get();
        ImmutableArray<Entity> entity = engine.getEntitiesFor(player);
        Entity playerEntity = entity.first();
        
        handleCamera(camera, playerEntity, delta);
        handleInput(playerEntity);
    }
    
    private void handleCamera(OrthographicCamera camera, Entity playerEntity, float delta) {
    	
    	if(camera_lock) {
    		TransformComponent transform = ComponentList.TRANSFORM.get(playerEntity);
    		camera.position.set(transform.position.x,transform.position.y,0);
    		//camera.position.add(camera.position.cpy().scl(-1).add(transform.position.x, transform.position.y, 0).scl(0.04f));
    	}
    	
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.zoom += 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			camera.zoom -= 0.02;
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
    }
    
    private void handleInput(Entity playerEntity) {        

        float accelX = 0.0f;
        float accelY = 0.0f;

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            accelX = -0.2f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            accelX = 0.2f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            accelY = 0.2f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            accelY = -0.2f;
        }

        MovementComponent movement = ComponentList.MOVEMENT.get(playerEntity);
        movement.velocity.add(new Vector2(accelX,accelY));
    }
}
