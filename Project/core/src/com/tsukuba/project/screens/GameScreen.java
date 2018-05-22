package com.tsukuba.project.screens;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tsukuba.project.SpaceGame;
import com.tsukuba.project.components.DrawableComponent;
import com.tsukuba.project.components.TransformComponent;
import com.tsukuba.project.components.MovementComponent;
import com.tsukuba.project.components.PlayerControlledComponent;
import com.tsukuba.project.systems.MovementSystem;
import com.tsukuba.project.systems.RenderingSystem;

public class GameScreen extends ScreenAdapter {

    private SpaceGame game;
    private Engine engine;
    private ComponentMapper<MovementComponent> vm = ComponentMapper.getFor(MovementComponent.class);
    
    public GameScreen(SpaceGame game) {
        this.game = game;
        engine = new Engine();
        Entity movingEntity = new Entity();
        Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        movingEntity.add(new TransformComponent(new Vector3(5.0f,1.0f,1), new Vector2(1.0f,1.0f), 0));
        movingEntity.add(new MovementComponent(new Vector2(0.0f, 0.5f), new Vector2(0.0f,0.0f)));
        movingEntity.add(new DrawableComponent(new TextureRegion(texture,0,0,64,64)));
        movingEntity.add(new PlayerControlledComponent());
        engine.addEntity(movingEntity);
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderingSystem(game.batch));
        
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        OrthographicCamera camera = new OrthographicCamera(480,320);
        game.batch.setProjectionMatrix(camera.combined);
        
        Family player = Family.all(PlayerControlledComponent.class).get();
        ImmutableArray<Entity> entity = engine.getEntitiesFor(player);
        Entity playerEntity = entity.first();
        
        float accelX = 0.0f;
        float accelY = 0.0f;
        
        if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
        	accelX = -0.2f;
        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
        	accelX = 0.2f;
        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
        	accelY = 0.2f;
        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
        	accelY = -0.2f;
        }
        
        MovementComponent movement = vm.get(playerEntity);
        movement.velocity.add(new Vector2(accelX,accelY));
    }
}
