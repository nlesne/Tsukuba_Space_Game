package com.tsukuba.project.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.tsukuba.project.SpaceGame;
import com.tsukuba.project.components.EnemyTypeComponent;
import com.tsukuba.project.entities.EnemyFactory;
import com.tsukuba.project.entities.PlayerShipFactory;
import com.tsukuba.project.systems.AISystem;
import com.tsukuba.project.systems.IndicatorSystem;
import com.tsukuba.project.systems.MovementSystem;
import com.tsukuba.project.systems.RenderingSystem;

public class PlanetHangarScreen extends ScreenAdapter{
	
	private ScreenAdapter parent;
	private SpaceGame game;
	
	private ShapeRenderer shape;
	
	 public PlanetHangarScreen(SpaceGame game, ScreenAdapter parent) {
	        this.game = game;
	        this.parent = parent;
	        shape = new ShapeRenderer();  
	        
	        shape.begin(ShapeType.Line);
			for(int i=0; i<200; i++) {
				shape.line(0, i*10, 1000, i*10);
				shape.line(i*10, 0, i*10, 1000);
			}
			shape.end();
	 }	 
	 
	 @Override
	  public void render(float delta) {
	    if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
	    	game.setScreen(parent); 
	    }
	  }
}
