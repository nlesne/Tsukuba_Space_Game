package com.tsukuba.project.components;

import com.badlogic.ashley.core.Entity;

public class CollisionComponent {
    public Entity collidingEntity;

    public CollisionComponent(Entity collidingEntity) {
        this.collidingEntity = collidingEntity;
    }
}
