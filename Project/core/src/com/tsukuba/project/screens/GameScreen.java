package com.tsukuba.project.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tsukuba.project.SpaceGame;
import com.tsukuba.project.components.DrawableComponent;
import com.tsukuba.project.components.MovementComponent;
import com.tsukuba.project.components.TransformComponent;
import com.tsukuba.project.systems.MovementSystem;
import com.tsukuba.project.systems.RenderingSystem;

public class GameScreen extends ScreenAdapter {

    private SpaceGame game;
    private PooledEngine engine;

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

        movingEntity.add(transform);
        movingEntity.add(movement);
        movingEntity.add(drawable);
        engine.addEntity(movingEntity);
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderingSystem(game.batch));

    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        OrthographicCamera camera = engine.getSystem(RenderingSystem.class).getCamera();
        game.batch.setProjectionMatrix(camera.combined);


    }
}
