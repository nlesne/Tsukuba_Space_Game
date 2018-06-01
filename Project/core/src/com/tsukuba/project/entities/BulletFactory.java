package com.tsukuba.project.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.*;

public class BulletFactory {
    public static Entity shoot(PooledEngine engine, Entity player) {
        TransformComponent playerTransform = ComponentList.TRANSFORM.get(player);

        Entity bullet = engine.createEntity();
        DrawableComponent drawable = engine.createComponent(DrawableComponent.class);
        drawable.sprite = Assets.playerBullet;

        TransformComponent transform = engine.createComponent(TransformComponent.class);
        float adjustedRotation = playerTransform.rotation + MathUtils.PI / 2;
        transform.position.set(playerTransform.position.x,playerTransform.position.y,2);
        float bulletPosOffsetX = (float) (Math.cos(adjustedRotation));
        float bulletPosOffsetY = (float) (Math.sin(adjustedRotation));
        transform.position.add(bulletPosOffsetX,bulletPosOffsetY,0);
        transform.rotation = playerTransform.rotation;
        transform.width = 0.25f;
        transform.height = 0.5f;

        MovementComponent movement = engine.createComponent(MovementComponent.class);
        movement.velocity.set(bulletPosOffsetX*10f,bulletPosOffsetY*10f);

        ProjectileComponent projectile = engine.createComponent(ProjectileComponent.class);
        projectile.damage = 1;
        projectile.shooter = player;

        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.EntityType.PROJECTILE;

        HitboxComponent hitbox = engine.createComponent(HitboxComponent.class);
        hitbox.shape = new Polygon(new float[] {
                0,0,
                0,transform.height,
                transform.width,transform.height,
                transform.width, 0
        });
        hitbox.shape.setPosition(transform.position.x-transform.width/2,transform.position.y-transform.height/2);
        hitbox.shape.setOrigin(hitbox.width/2,hitbox.height/2);
        hitbox.shape.setRotation(transform.rotation);
        hitbox.width = transform.width;
        hitbox.height = transform.height;

        bullet.add(drawable);
        bullet.add(transform);
        bullet.add(movement);
        bullet.add(projectile);
        bullet.add(type);
        bullet.add(hitbox);
        engine.addEntity(bullet);

        return bullet;

    }
}
