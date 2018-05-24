package com.tsukuba.project.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.*;

public class EnemyFactory {

    public static Entity create(PooledEngine engine) {

        DrawableComponent drawable = engine.createComponent(DrawableComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        MovementComponent movement = engine.createComponent(MovementComponent.class);
        AIComponent ai = engine.createComponent(AIComponent.class);
        HealthComponent health = engine.createComponent(HealthComponent.class);

        Entity enemy = engine.createEntity();
        enemy.add(drawable);
        enemy.add(transform);
        enemy.add(movement);
        enemy.add(ai);
        enemy.add(health);

        engine.addEntity(enemy);

        return enemy;

    }
}
