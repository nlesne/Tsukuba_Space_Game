package com.tsukuba.project.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.*;

import java.util.Random;

public class EnemyFactory {

    public static Entity spawn(PooledEngine engine, EnemyTypeComponent.EnemyType type) {

        Random random = new Random();

        DrawableComponent drawable = engine.createComponent(DrawableComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        transform.position.x = random.nextFloat() * 32;
        transform.position.y = random.nextFloat() * 32;
        MovementComponent movement = engine.createComponent(MovementComponent.class);
        AIComponent ai = engine.createComponent(AIComponent.class);
        HealthComponent health = engine.createComponent(HealthComponent.class);
        EnemyTypeComponent enemyType = engine.createComponent(EnemyTypeComponent.class);
        enemyType.type = type;

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
