package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.ComponentList;
import com.tsukuba.project.components.EnemyTypeComponent;
import com.tsukuba.project.components.PlayerComponent;
import com.tsukuba.project.components.TransformComponent;

public class IndicatorSystem extends IteratingSystem {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;

    public IndicatorSystem(OrthographicCamera camera,SpriteBatch batch) {
        super(Family.all(EnemyTypeComponent.class).get());
        
        this.camera = camera;
        sprite = new Sprite(Assets.arrow);
        this.batch = batch;
        sprite.flip(true, false);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Entity playerEntity = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
    	
        TransformComponent positionPlayer = ComponentList.TRANSFORM.get(playerEntity);
    	TransformComponent position = ComponentList.TRANSFORM.get(entity);
        		
   		if(!camera.frustum.pointInFrustum(position.position)) {
   			double theta = Math.atan2(position.position.y-positionPlayer.position.y,position.position.x-positionPlayer.position.x);
   			//theta = theta*180/Math.PI;
   					
   			batch.begin();
   			float spritePosX = (float) (300+150*Math.cos(theta));
   			float spritePosY = (float) (220+150*Math.sin(theta));
   			sprite.setPosition(spritePosX,spritePosY);	
   			sprite.setRotation((float) (MathUtils.radiansToDegrees*theta));
   			sprite.setSize(20, 20);
   			sprite.setOriginCenter();
   			//sprite.rotate((float) theta);
   			sprite.draw(batch);
   			//batch.draw(texture, (float) (2*Math.cos(theta)), (float) (2*Math.sin(theta)), 15, 15);
   			batch.end();
   		}    
    }
}
