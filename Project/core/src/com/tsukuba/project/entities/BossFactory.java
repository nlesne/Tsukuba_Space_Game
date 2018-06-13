package com.tsukuba.project.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.*;

import java.util.Random;

public class BossFactory {
    public static Entity spawn(PooledEngine engine) {
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        Random random = new Random();
        transform.position.set(random.nextInt(256)-128,random.nextInt(256)-128,2);
        transform.width = 5;
        transform.height = 5;

        DrawableComponent drawable = engine.createComponent(DrawableComponent.class);
        drawable.sprite = new TextureRegion(Assets.boss);

        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.EntityType.ENEMY;

        AIComponent ai = engine.createComponent(AIComponent.class);
        ai.detectionRadius = 30;
        ai.state = AIComponent.AIState.IDLE;

        MovementComponent movement = engine.createComponent(MovementComponent.class);

        EnemyComponent enemy = engine.createComponent(EnemyComponent.class);
        enemy.contactDamage = 2;
        enemy.type = EnemyComponent.EnemyType.BOSS;

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

        HealthComponent health = engine.createComponent(HealthComponent.class);
        health.maxHealth = 100;
        health.currentHealth = health.maxHealth;

        UpgradeComponent upgrade = engine.createComponent(UpgradeComponent.class);
        upgrade.weaponLevel = 3;

        Entity boss = engine.createEntity();
        boss.add(transform);
        boss.add(drawable);
        boss.add(type);
        boss.add(movement);
        boss.add(ai);
        boss.add(enemy);
        boss.add(hitbox);
        boss.add(health);
        boss.add(upgrade);

        engine.addEntity(boss);
        return boss;
    }
}
