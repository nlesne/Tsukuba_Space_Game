package com.tsukuba.project.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.*;

public class PlayerShipFactory {

    public static Entity create(PooledEngine engine, float x, float y) {
        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);
        DrawableComponent drawableComponent = engine.createComponent(DrawableComponent.class);
        drawableComponent.sprite = new TextureRegion(Assets.spaceship);

        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.position.set(x,y,0);
        transformComponent.scale.set(0.1f,0.1f);
        HealthComponent healthComponent = engine.createComponent(HealthComponent.class);
        healthComponent.maxHealth = 5;
        healthComponent.currentHealth = healthComponent.maxHealth;

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.friction = 0.99f;
        
        UpgradeComponent upgradeComponent = engine.createComponent(UpgradeComponent.class);
        

        Entity player = engine.createEntity();

        player.add(upgradeComponent);
        player.add(transformComponent);
        player.add(playerComponent);
        player.add(drawableComponent);
        player.add(healthComponent);
        player.add(movementComponent);

        engine.addEntity(player);

        return player;
    }
}
