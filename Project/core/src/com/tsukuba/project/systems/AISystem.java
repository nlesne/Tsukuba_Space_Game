package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.tsukuba.project.components.*;

public class AISystem extends IteratingSystem {


    public AISystem() {
        super(Family.all(EnemyTypeComponent.class, AIComponent.class, MovementComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = ComponentList.TRANSFORM.get(entity);
        AIComponent ai = ComponentList.AI.get(entity);
        EnemyTypeComponent.EnemyType enemyType = ComponentList.ENEMY_TYPE.get(entity).type;
        MovementComponent movement = ComponentList.MOVEMENT.get(entity);

        Entity player = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
        TransformComponent playerTransform = ComponentList.TRANSFORM.get(player);
        float distToPlayer = transform.position.dst(playerTransform.position);

        switch (ai.state) {
            case IDLE:
                switch (enemyType) {
                    case MINE:
                        if (distToPlayer <= ai.detectionRadius) {
                            ai.state = AIComponent.AIState.CHARGE;
                            ai.target = player;
                        }
                        break;
                    case SHOOTER:
                        //TODO
                        break;
                }
                break;
            case CHARGE:
                switch (enemyType) {
                    case MINE:
                        TransformComponent targetTransform = ComponentList.TRANSFORM.get(ai.target);
                        float targetX = targetTransform.position.x;
                        float targetY = targetTransform.position.y;
                        float targetAngle = MathUtils.atan2(targetY - transform.position.y, targetX - transform.position.x);

                        double lerpedAngle = MathUtils.lerpAngle(transform.rotation, targetAngle, 1f);

                        float accelCoef = 10f;
                        movement.acceleration.set((float) (accelCoef*Math.cos(lerpedAngle)), (float) (accelCoef*Math.sin(lerpedAngle)));
                        transform.rotation = (float) (lerpedAngle - MathUtils.PI / 2);
                        break;
                    case SHOOTER:
                        //TODO
                        break;
                }
                break;
            case AIM:
                if (enemyType == EnemyTypeComponent.EnemyType.SHOOTER) {
                    //TODO
                }
                break;
            case FLEE:
                //TODO
                break;

        }
    }
}
