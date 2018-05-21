package com.tsukuba.project;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tsukuba.project.components.DrawableComponent;
import com.tsukuba.project.components.TransformComponent;
import com.tsukuba.project.components.VelocityComponent;
import com.tsukuba.project.screens.GameScreen;
import com.tsukuba.project.systems.MovementSystem;
import com.tsukuba.project.systems.RenderingSystem;

public class SpaceGame extends Game {
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new GameScreen(this));
	}


	@Override
	public void render () {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
