package com.tsukuba.project.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.AIComponent;
import com.tsukuba.project.components.DrawableComponent;
import com.tsukuba.project.components.HealthComponent;
import com.tsukuba.project.components.MovementComponent;
import com.tsukuba.project.components.TransformComponent;

public class PlanetFactory {
	public static Entity create(PooledEngine engine, float x, float y, float size) {
		
		DrawableComponent drawable = engine.createComponent(DrawableComponent.class);			
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        drawable.sprite = new TextureRegion(Assets.planet);
        
        transform.scale.set(size,size);
        
        Entity planet = engine.createEntity();
        planet.add(drawable);
        planet.add(transform);

        engine.addEntity(planet);
		
		return planet;
	}
}
