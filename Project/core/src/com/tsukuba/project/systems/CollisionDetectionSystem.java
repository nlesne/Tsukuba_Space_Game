package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.tsukuba.project.components.CollidingComponent;
import com.tsukuba.project.components.ComponentList;
import com.tsukuba.project.components.HitboxComponent;
import com.tsukuba.project.components.TransformComponent;

public class CollisionDetectionSystem extends IteratingSystem {
    PooledEngine engine;

    public CollisionDetectionSystem(PooledEngine engine) {
        super(Family.all(HitboxComponent.class,TransformComponent.class).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HitboxComponent hitbox = ComponentList.HITBOX.get(entity);
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(getFamily());

        for (Entity e : entities) {
            if (!e.equals(entity)) {
                HitboxComponent otherHitbox = ComponentList.HITBOX.get(e);
                if (Intersector.overlapConvexPolygons(hitbox.shape,otherHitbox.shape)) {
                    CollidingComponent collidingComponent = engine.createComponent(CollidingComponent.class);
                    collidingComponent.collidingEntity = e;
                    entity.add(collidingComponent);
                }
            }
        }
    }
}
