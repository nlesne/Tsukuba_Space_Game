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
import com.tsukuba.project.components.TypeComponent;

public class MovementSystem extends IteratingSystem {

    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get(),0);

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent position = ComponentList.TRANSFORM.get(entity);
        MovementComponent movement = ComponentList.MOVEMENT.get(entity);
        TypeComponent type = ComponentList.TYPE.get(entity);
        Vector2 tmp = new Vector2();

        position.rotation = (float) (position.rotation%Math.toRadians(360));

        tmp.set(movement.acceleration).scl(deltaTime);
        movement.velocity.add(tmp);
        movement.velocity.scl(movement.friction);

        tmp.set(movement.velocity).scl(deltaTime);
        position.position.add(tmp.x,tmp.y,0.0f);
    }
}
