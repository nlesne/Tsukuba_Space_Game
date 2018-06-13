package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.tsukuba.project.components.*;

public class HitboxUpdateSystem extends IteratingSystem {

	ShapeRenderer renderer;
	SpriteBatch batch;

	public HitboxUpdateSystem(SpriteBatch batch) {
		super(Family.all(HitboxComponent.class,TransformComponent.class).get());
		renderer = new ShapeRenderer();
		this.batch = batch;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		HitboxComponent hitbox = ComponentList.HITBOX.get(entity);
		TransformComponent transform = ComponentList.TRANSFORM.get(entity);
		renderer.setProjectionMatrix(batch.getProjectionMatrix());

		if (ComponentList.COOLDOWN.has(entity)) {
			CooldownComponent cooldownComponent = ComponentList.COOLDOWN.get(entity);
			cooldownComponent.value -= deltaTime;
			if (cooldownComponent.value <= 0) {
				entity.remove(CooldownComponent.class);
			}
		}
		hitbox.shape.setPosition(transform.position.x-hitbox.width/2,transform.position.y-hitbox.height/2);
		hitbox.shape.setRotation(MathUtils.radDeg * transform.rotation);
	}
}
