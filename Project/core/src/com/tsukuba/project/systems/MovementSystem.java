package com.tsukuba.project.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.tsukuba.project.components.TransformComponent;
import com.tsukuba.project.components.MovementComponent;

public class MovementSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<MovementComponent> vm = ComponentMapper.getFor(MovementComponent.class);

    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent position = pm.get(entity);
        MovementComponent movement = vm.get(entity);

        Vector2 tmp = new Vector2();

        tmp.set(movement.acceleration).scl(deltaTime);
        movement.velocity.add(tmp);

        tmp.set(movement.velocity).scl(deltaTime);
        position.position.add(tmp.x,tmp.y,0.0f);

    }
}
