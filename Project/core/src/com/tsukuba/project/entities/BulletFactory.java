package com.tsukuba.project.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
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
        float bulletPosOffsetX = (float) (2f*Math.cos(adjustedRotation));
        float bulletPosOffsetY = (float) (2f*Math.sin(adjustedRotation));
        transform.position.add(bulletPosOffsetX,bulletPosOffsetY,0);

        MovementComponent movement = engine.createComponent(MovementComponent.class);
        movement.velocity.set(bulletPosOffsetX*10f,bulletPosOffsetY*10f);

        ProjectileComponent projectile = engine.createComponent(ProjectileComponent.class);
        projectile.contactDamage = 1;

        bullet.add(drawable);
        bullet.add(transform);
        bullet.add(movement);
        bullet.add(projectile);
        engine.addEntity(bullet);

        return bullet;

    }
}
