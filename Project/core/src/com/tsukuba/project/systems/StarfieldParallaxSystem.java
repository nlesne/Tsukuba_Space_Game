package com.tsukuba.project.systems;

import java.util.Random;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.tsukuba.project.components.ComponentList;
import com.tsukuba.project.components.EnemyTypeComponent;
import com.tsukuba.project.components.MovementComponent;
import com.tsukuba.project.components.PlayerComponent;

public class StarfieldParallaxSystem extends IteratingSystem {
	private int MAX_STARS = 75;

	private ShapeRenderer shape;
	
	private Array<Vector3> starfield;
	
	public StarfieldParallaxSystem(ShapeRenderer shape) {
		super(Family.all(EnemyTypeComponent.class).get());
		
		this.shape = shape;	
		
		this.starfield = parallaxStarfieldCreate();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Entity playerEntity = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
		
		parallaxStarfield(starfield, playerEntity);
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

	private void parallaxStarfield(Array<Vector3> starArray, Entity playerEntity) {
		MovementComponent movement = ComponentList.MOVEMENT.get(playerEntity);
				
    	shape.begin(ShapeType.Line);
    	for(int i=0; i<starArray.size;i++) {
    		shape.setColor(Color.LIGHT_GRAY);
    		shape.circle(starArray.get(i).x,starArray.get(i).y, 1);
    		if(starArray.get(i).y<0)
    			starArray.get(i).y = Gdx.graphics.getHeight();
    		if(starArray.get(i).x<0)
    			starArray.get(i).x = Gdx.graphics.getWidth();
    		if(starArray.get(i).y>Gdx.graphics.getHeight())
    			starArray.get(i).y = 0;
    		if(starArray.get(i).x>Gdx.graphics.getWidth())
    			starArray.get(i).x = 0;
    		
    		starArray.set(i, new Vector3(starArray.get(i).x-movement.velocity.x*starArray.get(i).z,starArray.get(i).y-movement.velocity.y*starArray.get(i).z,starArray.get(i).z));
    	}
    	shape.end();
    }
	
}
