package com.tsukuba.project.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.*;

public class BulletFactory {
    public static final float BASE_BULLET_SPREAD = 10; // degrees
    public static final float BASE_BULLET_SPEED = 30; // meters/second
    public static Entity shoot(final PooledEngine engine, Entity shooter) {
        TransformComponent shooterTransform = ComponentList.TRANSFORM.get(shooter);
        float bulletSpread = MathUtils.random(BASE_BULLET_SPREAD) - BASE_BULLET_SPREAD / 2;
        float bulletSpeed = BASE_BULLET_SPEED;

        final Entity bullet = engine.createEntity();
        DrawableComponent drawable = engine.createComponent(DrawableComponent.class);
        drawable.sprite = Assets.bulletLvl1;

        MovementComponent movement = engine.createComponent(MovementComponent.class);
        
        ProjectileComponent projectile = engine.createComponent(ProjectileComponent.class);
        projectile.damage = 1;
        projectile.lifespan = 2;
        projectile.shooter = shooter;
        if (ComponentList.UPGRADE.has(shooter)) {
            UpgradeComponent shooterUpgrades = ComponentList.UPGRADE.get(shooter);
            projectile.damage = shooterUpgrades.weaponLevel;
            projectile.lifespan = shooterUpgrades.weaponLevel * 2;
            bulletSpread /= shooterUpgrades.weaponLevel;
            bulletSpeed *= shooterUpgrades.weaponLevel;
            if (shooterUpgrades.weaponLevel == 2)
                drawable.sprite = Assets.bulletLvl2;
            else if (shooterUpgrades.weaponLevel >= 3)
                drawable.sprite = Assets.bulletLvl3;
        }

        TransformComponent transform = engine.createComponent(TransformComponent.class);
        float adjustedRotation = shooterTransform.rotation + MathUtils.PI / 2 + (bulletSpread*MathUtils.degRad);
        transform.position.set(shooterTransform.position.x,shooterTransform.position.y,2);
        float bulletPosOffsetX = (float) (Math.cos(adjustedRotation));
        float bulletPosOffsetY = (float) (Math.sin(adjustedRotation));
        transform.position.add(bulletPosOffsetX,bulletPosOffsetY,0);
        transform.rotation = shooterTransform.rotation;
        transform.width = 0.25f;
        transform.height = 0.5f;

        
        movement.velocity.set(bulletPosOffsetX,bulletPosOffsetY).nor().scl(bulletSpeed, bulletSpeed);

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
