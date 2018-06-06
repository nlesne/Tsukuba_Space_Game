package com.tsukuba.project.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.*;

public class PlayerShipFactory {

    public static Entity create(PooledEngine engine, float x, float y) {
        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);
        playerComponent.shootCooldown = 0.25f;
        DrawableComponent drawableComponent = engine.createComponent(DrawableComponent.class);
        drawableComponent.sprite = new TextureRegion(Assets.spaceship);

        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.position.set(x,y,0);
        //transformComponent.scale.set(0.1f,0.1f);
        transformComponent.height = 3;
        transformComponent.width = 2;
        HealthComponent healthComponent = engine.createComponent(HealthComponent.class);
        healthComponent.maxHealth = 5;
        healthComponent.currentHealth = healthComponent.maxHealth;

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.friction = 0.99f;
        
        UpgradeComponent upgradeComponent = engine.createComponent(UpgradeComponent.class);
        

        TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
        typeComponent.type = TypeComponent.EntityType.PLAYER;

        HitboxComponent hitboxcomponent = engine.createComponent(HitboxComponent.class);
        hitboxcomponent.width = transformComponent.width;
        hitboxcomponent.height = transformComponent.height;
        hitboxcomponent.shape = new Polygon(new float[]
                {
                        0,0,
                        0,hitboxcomponent.height,
                        hitboxcomponent.width,hitboxcomponent.height,
                        hitboxcomponent.width,0
                });
        hitboxcomponent.shape.setPosition(transformComponent.position.x-hitboxcomponent.width/2,transformComponent.position.y-hitboxcomponent.height/2);
        hitboxcomponent.shape.setOrigin(hitboxcomponent.width/2,hitboxcomponent.height/2);
        hitboxcomponent.shape.setRotation(MathUtils.radDeg * transformComponent.rotation);

        Entity player = engine.createEntity();

        player.add(upgradeComponent);
        player.add(transformComponent);
        player.add(playerComponent);
        player.add(drawableComponent);
        player.add(healthComponent);
        player.add(movementComponent);
        player.add(typeComponent);
        player.add(hitboxcomponent);

        engine.addEntity(player);

        return player;
    }
}
