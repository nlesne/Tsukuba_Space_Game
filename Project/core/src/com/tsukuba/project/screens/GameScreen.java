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
import com.tsukuba.project.components.DrawableComponent;
import com.tsukuba.project.components.MovementComponent;
import com.tsukuba.project.components.PlayerControlledComponent;
import com.tsukuba.project.components.TransformComponent;
import com.tsukuba.project.systems.MovementSystem;
import com.tsukuba.project.systems.RenderingSystem;

public class GameScreen extends ScreenAdapter {

    private SpaceGame game;
    private PooledEngine engine;
    private ComponentMapper<MovementComponent> vm = ComponentMapper.getFor(MovementComponent.class);

    public GameScreen(SpaceGame game) {
        this.game = game;
        engine = new PooledEngine();
        Entity movingEntity = new Entity();
        Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));

        TransformComponent transform = engine.createComponent(TransformComponent.class);
        transform.position.set(16,16,0);

        MovementComponent movement = engine.createComponent(MovementComponent.class);
        movement.velocity.set(0.5f,0f);

        DrawableComponent drawable = engine.createComponent(DrawableComponent.class);
        drawable.sprite.setRegion(new TextureRegion(texture,0,0,64,64));

        PlayerControlledComponent playerControlled = engine.createComponent(PlayerControlledComponent.class);

        movingEntity.add(transform);
        movingEntity.add(movement);
        movingEntity.add(drawable);
        movingEntity.add(playerControlled);
        engine.addEntity(movingEntity);
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderingSystem(game.batch));

    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        OrthographicCamera camera = engine.getSystem(RenderingSystem.class).getCamera();
        game.batch.setProjectionMatrix(camera.combined);

        Family player = Family.all(PlayerControlledComponent.class).get();
        ImmutableArray<Entity> entity = engine.getEntitiesFor(player);
        Entity playerEntity = entity.first();

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

        MovementComponent movement = vm.get(playerEntity);
        movement.velocity.add(new Vector2(accelX,accelY));
    }
}
