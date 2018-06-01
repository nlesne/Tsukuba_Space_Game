package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.tsukuba.project.components.*;

import static com.tsukuba.project.components.TypeComponent.EntityType;

public class CollisionResolveSystem extends IteratingSystem {
    PooledEngine engine;

    public CollisionResolveSystem(PooledEngine engine) {
        super(Family.all(CollidingComponent.class,TransformComponent.class).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollidingComponent collidingComponent = ComponentList.COLLIDING.get(entity);
        Entity collidingEntity = collidingComponent.collidingEntity;
        HealthComponent health = ComponentList.HEALTH.get(entity);
        EntityType e1Type = ComponentList.TYPE.get(entity).type;
        EntityType e2Type = ComponentList.TYPE.get(collidingEntity).type;

        if (entity.isScheduledForRemoval() || collidingEntity.isScheduledForRemoval())
            return;

        switch (e1Type) {
            case PLAYER:
                switch (e2Type){
                    case PROJECTILE:
                        ProjectileComponent projectile = ComponentList.PROJECTILE.get(collidingEntity);
                        if (!projectile.shooter.equals(entity)) {
                            health.currentHealth -= projectile.damage;
                            getEngine().removeEntity(collidingEntity);
                        }
                        break;
                    case ENEMY:
                        if (!ComponentList.COOLDOWN.has(entity)) {
                            EnemyComponent enemy = ComponentList.ENEMY.get(collidingEntity);
                            health.currentHealth -= enemy.contactDamage;
                            MovementComponent playerMovement = ComponentList.MOVEMENT.get(entity);
                            MovementComponent enemyMovement = ComponentList.MOVEMENT.get(collidingEntity);
                            playerMovement.velocity.scl(-1f);
                            enemyMovement.velocity.scl(-1f);
                            CooldownComponent cooldown = engine.createComponent(CooldownComponent.class);
                            cooldown.value = 2;
                            entity.add(cooldown);
                        }
                        break;
                    case PLANET:
                        //TODO
                        break;

                }
                break;
            case ENEMY:
                switch (e2Type) {
                    case PROJECTILE:
                        HealthComponent enemyHealth = ComponentList.HEALTH.get(entity);
                        ProjectileComponent projectile = ComponentList.PROJECTILE.get(collidingEntity);
                        if (!projectile.shooter.equals(entity)) {
                            enemyHealth.currentHealth -= projectile.damage;
                            getEngine().removeEntity(collidingEntity);
                            if (enemyHealth.currentHealth <= 0) {
                                getEngine().removeEntity(entity);
                            }
                        }
                        break;
                }
                break;
        }

        entity.remove(CollidingComponent.class);

    }
}
