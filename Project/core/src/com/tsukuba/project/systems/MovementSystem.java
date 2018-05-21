package com.tsukuba.project.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.tsukuba.project.components.TransformComponent;
import com.tsukuba.project.components.VelocityComponent;

public class MovementSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public MovementSystem() {
        super(Family.all(TransformComponent.class, VelocityComponent.class).get());

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent position = pm.get(entity);
        VelocityComponent velocity = vm.get(entity);


    }
}
