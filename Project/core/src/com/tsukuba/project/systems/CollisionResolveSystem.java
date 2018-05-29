package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.tsukuba.project.components.CollidingComponent;
import com.tsukuba.project.components.ComponentList;
import com.tsukuba.project.components.TransformComponent;

public class CollisionResolveSystem extends IteratingSystem {

    public CollisionResolveSystem() {
        super(Family.all(CollidingComponent.class,TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Entity collidingEntity = ComponentList.COLLIDING.get(entity).collidingEntity;

    }
}
