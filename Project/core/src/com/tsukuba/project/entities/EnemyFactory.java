package com.tsukuba.project.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.*;

import java.util.Random;

public class EnemyFactory {

    public static Entity spawn(PooledEngine engine, EnemyTypeComponent.EnemyType type) {

        Random random = new Random();

        DrawableComponent drawable = engine.createComponent(DrawableComponent.class);
        drawable.sprite = new TextureRegion(Assets.enemy);
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        transform.position.x = random.nextFloat() * 32;
        transform.position.y = random.nextFloat() * 32;
        transform.scale.set(0.25f,0.25f);
        MovementComponent movement = engine.createComponent(MovementComponent.class);
        AIComponent ai = engine.createComponent(AIComponent.class);
        ai.detectionRadius = 16;
        HealthComponent health = engine.createComponent(HealthComponent.class);
        EnemyTypeComponent enemyType = engine.createComponent(EnemyTypeComponent.class);
        enemyType.type = type;

        Entity enemy = engine.createEntity();
        enemy.add(enemyType);
        enemy.add(drawable);
        enemy.add(transform);
        enemy.add(movement);
        enemy.add(ai);
        enemy.add(health);

        engine.addEntity(enemy);

        return enemy;

    }
}
