package com.tsukuba.project;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tsukuba.project.components.DrawableComponent;
import com.tsukuba.project.components.PositionComponent;
import com.tsukuba.project.components.VelocityComponent;
import com.tsukuba.project.systems.MovementSystem;
import com.tsukuba.project.systems.RenderSystem;

public class SpaceGame extends Game {
	SpriteBatch batch;
	Engine engine;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		engine = new Engine();
		Entity movingEntity = new Entity();
		Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
		movingEntity.add(new PositionComponent(64,64));
		movingEntity.add(new VelocityComponent(0,1));
		movingEntity.add(new DrawableComponent(new TextureRegion(texture,0,0,64,64)));
		engine.addEntity(movingEntity);
		engine.addSystem(new MovementSystem());
		engine.addSystem(new RenderSystem(new OrthographicCamera()));

	}

	public void update(float dt) {
	    engine.update(dt);
	}

	@Override
	public void render () {
	    float dt = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
		update(dt);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
