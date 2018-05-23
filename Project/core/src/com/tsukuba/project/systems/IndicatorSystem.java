package com.tsukuba.project.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.tsukuba.project.components.EnnemyComponent;
import com.tsukuba.project.components.MovementComponent;
import com.tsukuba.project.components.TransformComponent;

public class IndicatorSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);

    public IndicatorSystem() {
        super(Family.all(EnnemyComponent.class).get());

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent position = pm.get(entity);
        OrthographicCamera camera;
        		
   		if(!camera.frustum.pointInFrustum(position.position)) {
       		System.out.println("oui");
   		}    
    }
}
