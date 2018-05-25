package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.tsukuba.project.components.ComponentList;
import com.tsukuba.project.components.TransformComponent;
import com.tsukuba.project.components.MovementComponent;

public class MovementSystem extends IteratingSystem {
	
    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent position = ComponentList.TRANSFORM.get(entity);
        MovementComponent movement = ComponentList.MOVEMENT.get(entity);
        
        Vector2 tmp = new Vector2();

        //accelX = (float) -(0.1*Math.cos(Math.toDegrees(rotation)+Math.PI/2));
        //accelY = (float) -(0.1*Math.sin(Math.toDegrees(rotation)+Math.PI/2));
        
        tmp.set(movement.acceleration).scl(deltaTime);
        movement.velocity.add(tmp);
        
        tmp.set(movement.velocity).scl(deltaTime);
        position.position.add(tmp.x,tmp.y,0.0f);      
    }
}
