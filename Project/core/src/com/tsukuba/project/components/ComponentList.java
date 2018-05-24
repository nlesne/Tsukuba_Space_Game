package com.tsukuba.project.components;

import com.badlogic.ashley.core.ComponentMapper;

public class ComponentList {

    public static final ComponentMapper<TransformComponent> TRANSFORM = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<MovementComponent> MOVEMENT = ComponentMapper.getFor(MovementComponent.class);
    public static final ComponentMapper<DrawableComponent> DRAWABLE = ComponentMapper.getFor(DrawableComponent.class);
    public static final ComponentMapper<HealthComponent> HEALTH = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<AIComponent> AI = ComponentMapper.getFor(AIComponent.class);
    public static final ComponentMapper<CollidingComponent> COLLIDING = ComponentMapper.getFor(CollidingComponent.class);
}
