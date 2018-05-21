package com.tsukuba.project.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
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
import com.tsukuba.project.systems.MovementSystem;
import com.tsukuba.project.systems.RenderingSystem;

public class GameScreen extends ScreenAdapter {

    private SpaceGame game;
    private Engine engine;

    public GameScreen(SpaceGame game) {
        this.game = game;
        engine = new Engine();
        Entity movingEntity = new Entity();
        Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        movingEntity.add(new TransformComponent(new Vector3(5.0f,1.0f,1), new Vector2(1.0f,1.0f), 0));
        movingEntity.add(new MovementComponent(new Vector2(0.0f, 0.5f), new Vector2(0.0f,0.0f)));
        movingEntity.add(new DrawableComponent(new TextureRegion(texture,0,0,64,64)));
        engine.addEntity(movingEntity);
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderingSystem(game.batch));

    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        OrthographicCamera camera = new OrthographicCamera(480,320);
        game.batch.setProjectionMatrix(camera.combined);
    }
}
