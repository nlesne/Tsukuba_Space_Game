package com.tsukuba.project.components;

import com.badlogic.ashley.core.ComponentMapper;

public class ComponentList {

    public static final ComponentMapper<TransformComponent> TRANSFORM = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<MovementComponent> MOVEMENT = ComponentMapper.getFor(MovementComponent.class);
    public static final ComponentMapper<DrawableComponent> DRAWABLE = ComponentMapper.getFor(DrawableComponent.class);
    public static final ComponentMapper<HealthComponent> HEALTH = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<AIComponent> AI = ComponentMapper.getFor(AIComponent.class);
    public static final ComponentMapper<CollidingComponent> COLLIDING = ComponentMapper.getFor(CollidingComponent.class);
    public static final ComponentMapper<EnemyComponent> ENEMY = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<PlayerComponent> PLAYER = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<ProjectileComponent> PROJECTILE = ComponentMapper.getFor(ProjectileComponent.class);
    public static final ComponentMapper<UpgradeComponent> UPGRADE = ComponentMapper.getFor(UpgradeComponent.class);
    public static final ComponentMapper<TypeComponent> TYPE = ComponentMapper.getFor(TypeComponent.class);
    public static final ComponentMapper<HitboxComponent> HITBOX = ComponentMapper.getFor(HitboxComponent.class);
    public static final ComponentMapper<CooldownComponent> COOLDOWN = ComponentMapper.getFor(CooldownComponent.class);
    public static final ComponentMapper<QuestComponent> QUEST = ComponentMapper.getFor(QuestComponent.class);

}
