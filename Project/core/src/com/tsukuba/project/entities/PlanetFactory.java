package com.tsukuba.project.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.*;

public class PlanetFactory {
	public static Entity create(PooledEngine engine, float x, float y, int size, int id) {
		
		DrawableComponent drawable = engine.createComponent(DrawableComponent.class);
		drawable.sprite = new TextureRegion(Assets.planet);
		
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.EntityType.PLANET;

        transform.position.set(x,y,0);
        transform.height = size*10;
        transform.width = size*10;

        switch(size) {
        case 0 :
        	drawable.sprite = new TextureRegion(Assets.asteroid);
        	transform.scale.set(0.2f,0.2f);
        	break;
        case 1 :
        	drawable.sprite = new TextureRegion(Assets.planet);
        	break;
        case 2 :
        	drawable.sprite = new TextureRegion(Assets.planet);
        	break;
        case 3 :
        	drawable.sprite = new TextureRegion(Assets.planetHangar);
        	break;
        case 4 :
        	drawable.sprite = new TextureRegion(Assets.planetHangar);
        	break;
        }

        HitboxComponent hitbox = engine.createComponent(HitboxComponent.class);
        hitbox.shape = new Polygon(new float[] {
                0,0,
                0,transform.height,
                transform.width,transform.height,
                transform.width, 0
        });
        
        hitbox.shape.setPosition(transform.position.x-transform.width/2,transform.position.y-transform.height/2);
        hitbox.shape.setOrigin(hitbox.width/2,hitbox.height/2);
        hitbox.shape.setRotation(transform.rotation);
        hitbox.width = transform.width;
        hitbox.height = transform.height;
        
        Entity planet = engine.createEntity();
        planet.add(hitbox);
        planet.add(drawable);
        planet.add(transform);
        planet.add(type);

        engine.addEntity(planet);
		
		return planet;
	}
}
