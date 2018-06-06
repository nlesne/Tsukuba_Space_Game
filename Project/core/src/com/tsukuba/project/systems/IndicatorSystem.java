package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.*;

public class IndicatorSystem extends IteratingSystem {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite sprite;

    public IndicatorSystem(OrthographicCamera camera,SpriteBatch batch) {
        super(Family.all(EnemyComponent.class).get());
        
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
    	
   		if(!camera.frustum.pointInFrustum(position.position) && positionPlayer.position.dst(position.position)<50) {
   			double theta = Math.atan2(position.position.y-positionPlayer.position.y,position.position.x-positionPlayer.position.x);
   			//theta = theta*180/Math.PI;
   					
   			batch.begin();
   			float spritePosX = (float) (positionPlayer.position.x +(4*Math.cos(theta)));
   			float spritePosY = (float) (positionPlayer.position.y +(4*Math.sin(theta)));
   			sprite.setPosition(spritePosX,spritePosY);	
   			sprite.setRotation((float) (MathUtils.radiansToDegrees*theta));
   			sprite.setSize(1.5f, 1.5f);
   			sprite.setOriginCenter();
   			sprite.draw(batch);
   			batch.end();
   		}    
    }
}
