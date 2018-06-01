package com.tsukuba.project.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.*;

import java.util.Random;

public class EnemyFactory {

    public static Entity spawn(PooledEngine engine, EnemyComponent.EnemyType type) {

        Random random = new Random();

        DrawableComponent drawable = engine.createComponent(DrawableComponent.class);
        drawable.sprite = new TextureRegion(Assets.enemy);
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        transform.position.x = random.nextFloat() * 32;
        transform.position.y = random.nextFloat() * 32;
        transform.width = 2f;
        transform.height = 3f;

        MovementComponent movement = engine.createComponent(MovementComponent.class);
        movement.friction = 0.99f;

        AIComponent ai = engine.createComponent(AIComponent.class);
        ai.detectionRadius = 16;

        HealthComponent health = engine.createComponent(HealthComponent.class);
        health.maxHealth = 3;
        health.currentHealth = health.maxHealth;
        EnemyComponent enemyComponent = engine.createComponent(EnemyComponent.class);
        enemyComponent.type = type;
        enemyComponent.contactDamage = 1;
        TypeComponent entityType = engine.createComponent(TypeComponent.class);
        entityType.type = TypeComponent.EntityType.ENEMY;

        HitboxComponent hitbox = engine.createComponent(HitboxComponent.class);
        hitbox.width = transform.width;
        hitbox.height = transform.height;
        hitbox.shape = new Polygon(new float[]
                {
                        0,0,
                        0,hitbox.height,
                        hitbox.width,hitbox.height,
                        hitbox.width,0
                });
        hitbox.shape.setPosition(transform.position.x-hitbox.width/2,transform.position.y-hitbox.height/2);
        hitbox.shape.setOrigin(hitbox.width/2,hitbox.height/2);
        hitbox.shape.setRotation(MathUtils.radDeg * transform.rotation);

        Entity enemy = engine.createEntity();
        enemy.add(enemyComponent);
        enemy.add(drawable);
        enemy.add(transform);
        enemy.add(movement);
        enemy.add(ai);
        enemy.add(health);
        enemy.add(entityType);
        enemy.add(hitbox);

        engine.addEntity(enemy);

        return enemy;

    }
}
