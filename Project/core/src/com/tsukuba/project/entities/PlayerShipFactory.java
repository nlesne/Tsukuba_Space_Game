package com.tsukuba.project.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.*;

public class PlayerShipFactory {

    public static Entity create(PooledEngine engine, float x, float y) {
        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);
        DrawableComponent drawableComponent = engine.createComponent(DrawableComponent.class);
        drawableComponent.sprite = Assets.playership;

        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.position.set(x,y,0);
        HealthComponent healthComponent = engine.createComponent(HealthComponent.class);
        healthComponent.maxHealth = 3;
        healthComponent.currentHealth = healthComponent.maxHealth;

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.velocity.set(0f,0f);
        movementComponent.acceleration.set(0,0);

        Entity player = engine.createEntity();

        player.add(transformComponent);
        player.add(playerComponent);
        player.add(drawableComponent);
        player.add(healthComponent);
        player.add(movementComponent);

        engine.addEntity(player);

        return player;
    }
}
