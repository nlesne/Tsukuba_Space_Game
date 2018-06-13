package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.tsukuba.project.components.ComponentList;
import com.tsukuba.project.components.ProjectileComponent;

public class ProjectileSystem extends IteratingSystem {

    PooledEngine engine;

    public ProjectileSystem(PooledEngine engine) {
        super(Family.all(ProjectileComponent.class).get(),1);
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ProjectileComponent projectile = ComponentList.PROJECTILE.get(entity);
        projectile.lifespan -= deltaTime;

        if (projectile.lifespan <= 0 || projectile.shooter.isScheduledForRemoval())
            engine.removeEntity(entity);
    }
}
