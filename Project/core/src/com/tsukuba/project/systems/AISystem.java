package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.tsukuba.project.components.*;
import com.tsukuba.project.entities.BulletFactory;
import com.tsukuba.project.entities.EnemyFactory;

import static com.badlogic.gdx.math.MathUtils.PI;
import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.sin;

public class AISystem extends IteratingSystem {

    PooledEngine engine;

    public AISystem(PooledEngine engine) {
        super(Family.all(EnemyComponent.class, AIComponent.class, TransformComponent.class).get(),1);
        this.engine = engine;
    }

    @Override
    protected void processEntity(final Entity entity, float deltaTime) {
        final TransformComponent transform = ComponentList.TRANSFORM.get(entity);
        final AIComponent ai = ComponentList.AI.get(entity);
        EnemyComponent.EnemyType enemyType = ComponentList.ENEMY.get(entity).type;
        MovementComponent movement = ComponentList.MOVEMENT.get(entity);

        Entity player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
        TransformComponent playerTransform = ComponentList.TRANSFORM.get(player);
        float distToPlayer = transform.position.dst(playerTransform.position);

        switch (ai.state) {
            case IDLE:
                if (distToPlayer <= ai.detectionRadius) {
                    ai.state = AIComponent.AIState.ATTACK;
                    ai.target = player;

                    switch (enemyType) {
                        case SHOOTER:
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    if (ai.state == AIComponent.AIState.ATTACK)
                                        BulletFactory.shoot(engine, entity);
                                    else
                                        Timer.instance().clear();
                                }
                            }, 1, 0.25f);
                            break;
                        case BOSS:
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    if (ai.state == AIComponent.AIState.ATTACK) {
                                        Entity spawned = EnemyFactory.spawn(engine, EnemyComponent.EnemyType.SHOOTER);
                                        TransformComponent spawnedTransform = ComponentList.TRANSFORM.get(spawned);
                                        spawnedTransform.position.set(transform.position).add(MathUtils.random(-10, 10), MathUtils.random(-15, 15), 0);
                                    }
                                    else
                                        Timer.instance().clear();
                                }
                            }, 1, 10);
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    if (ai.state == AIComponent.AIState.ATTACK) {
                                        Entity spawned = EnemyFactory.spawn(engine, EnemyComponent.EnemyType.MINE);
                                        TransformComponent spawnedTransform = ComponentList.TRANSFORM.get(spawned);
                                        spawnedTransform.position.set(transform.position);
                                    }
                                    else
                                        Timer.instance().clear();
                                }
                            }, 5, 5);
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    if (ai.state != AIComponent.AIState.IDLE)
                                        BulletFactory.shoot(engine, entity);
                                    else
                                        Timer.instance().clear();
                                }
                            }, 1, 0.2f);
                            break;
                    }
                }
                break;
            case ATTACK: {
                TransformComponent targetTransform = ComponentList.TRANSFORM.get(ai.target);
                float targetX = targetTransform.position.x;
                float targetY = targetTransform.position.y;
                float targetAngle = MathUtils.atan2(targetY - transform.position.y, targetX - transform.position.x);
                float lerpedAngle = MathUtils.lerpAngle(transform.rotation, targetAngle, 1f);

                switch (enemyType) {
                    case MINE:
                        movement.friction = 1;
                        movement.velocity.set(9 * cos(lerpedAngle), 9 * sin(lerpedAngle));
                        transform.rotation = lerpedAngle - PI / 2;
                        break;
                    case SHOOTER:
                        transform.rotation = targetAngle - PI / 2;
                        if (distToPlayer > 10) {
                            movement.acceleration.set(8 * cos(lerpedAngle), 8 * sin(lerpedAngle));
                        } else {
                            movement.acceleration.setZero();
                            movement.velocity.scl(0.9f);
                        }
                        break;
                    case BOSS:
                        HealthComponent health = ComponentList.HEALTH.get(entity);
                        if (health.currentHealth <= health.maxHealth / 2) {
                            ai.state = AIComponent.AIState.FLEE;
                            movement.friction = 0.985f;

                            for (int i = 0; i < 8; i++) {
                                EnemyComponent.EnemyType type;
                                Vector3 position = new Vector3();

                                if (i%2 == 0)
                                    type = EnemyComponent.EnemyType.MINE;
                                else
                                    type = EnemyComponent.EnemyType.SHOOTER;

                                position.set(transform.position).add(MathUtils.random(-10,10),MathUtils.random(-15,15),0);

                                Entity spawnedEntity = EnemyFactory.spawn(engine,type);
                                TransformComponent spawnTransform = ComponentList.TRANSFORM.get(spawnedEntity);
                                spawnTransform.position = position;
                            }
                        }
                        transform.rotation = targetAngle - PI / 2;
                        break;
                }
            }
                break;

            case FLEE:
                TransformComponent targetTransform = ComponentList.TRANSFORM.get(ai.target);
                float targetX = targetTransform.position.x;
                float targetY = targetTransform.position.y;
                float targetAngle = MathUtils.atan2(targetY - transform.position.y, targetX - transform.position.x);

                MovementComponent targetMovement = ComponentList.MOVEMENT.get(ai.target);

                transform.rotation = targetAngle - PI / 2;
                if (distToPlayer < 16)
                    movement.acceleration.set(targetMovement.velocity);
                else
                    movement.friction = 0.9f;
                break;

        }
    }
}
