package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.tsukuba.project.components.ComponentList;
import com.tsukuba.project.components.MovementComponent;
import com.tsukuba.project.components.PlayerComponent;

import java.util.Random;

public class StarfieldParallaxSystem extends EntitySystem {
	private int MAX_STARS = 75;

	private ShapeRenderer shape;
	
	private Array<Vector3> starfield;
	
	public StarfieldParallaxSystem(ShapeRenderer shape) {
		priority = 5;
		this.shape = shape;
		this.starfield = parallaxStarfieldCreate();
	}

	@Override
	public void update(float deltaTime) {
		parallaxStarfield();
	}

	private Array<Vector3> parallaxStarfieldCreate() {
    	Random rand = new Random();
    	
    	//z = Speed
    	Array<Vector3> starArray = new Array<Vector3>();
    	
    	for(int i=0; i<MAX_STARS/3; i++)
    		starArray.add(new Vector3(rand.nextInt(Gdx.graphics.getWidth()+1),rand.nextInt(Gdx.graphics.getHeight()+1),0.37f));
    	
    	for(int i=MAX_STARS/3; i<2*MAX_STARS/3; i++)
    		starArray.add(new Vector3(rand.nextInt(Gdx.graphics.getWidth()+1),rand.nextInt(Gdx.graphics.getHeight()+1),0.175f));
    		
    	for(int i=2*MAX_STARS/3; i<MAX_STARS; i++)
    		starArray.add(new Vector3(rand.nextInt(Gdx.graphics.getWidth()+1),rand.nextInt(Gdx.graphics.getHeight()+1),0.125f));
    		
    	return starArray;
    }

	private void parallaxStarfield() {
		Entity playerEntity = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
		MovementComponent movement = ComponentList.MOVEMENT.get(playerEntity);
				
    	shape.begin(ShapeType.Line);
    	for(int i=0; i<starfield.size;i++) {
    		shape.setColor(Color.LIGHT_GRAY);
    		shape.circle(starfield.get(i).x,starfield.get(i).y, 1);
    		if(starfield.get(i).y<0)
    			starfield.get(i).y = Gdx.graphics.getHeight();
    		if(starfield.get(i).x<0)
    			starfield.get(i).x = Gdx.graphics.getWidth();
    		if(starfield.get(i).y>Gdx.graphics.getHeight())
    			starfield.get(i).y = 0;
    		if(starfield.get(i).x>Gdx.graphics.getWidth())
    			starfield.get(i).x = 0;
    		
    		starfield.set(i, new Vector3(starfield.get(i).x-movement.velocity.x*starfield.get(i).z,starfield.get(i).y-movement.velocity.y*starfield.get(i).z,starfield.get(i).z));
    	}
    	shape.end();
    }
	
}
