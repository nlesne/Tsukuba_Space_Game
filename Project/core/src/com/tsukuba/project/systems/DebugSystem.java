package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tsukuba.project.components.ComponentList;
import com.tsukuba.project.components.HitboxComponent;
import com.tsukuba.project.components.TransformComponent;

public class DebugSystem extends IteratingSystem{

	private ShapeRenderer shape;
	private SpriteBatch batch;

	public DebugSystem(SpriteBatch batch) {
		super(Family.all(HitboxComponent.class,TransformComponent.class).get());
		this.batch = batch;
		
		shape = new ShapeRenderer();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		HitboxComponent hitbox = ComponentList.HITBOX.get(entity);
		TransformComponent transform = ComponentList.TRANSFORM.get(entity);
		
		shape.setProjectionMatrix(batch.getProjectionMatrix());
		
		shape.begin(ShapeRenderer.ShapeType.Line);
		shape.polygon(hitbox.shape.getTransformedVertices());
		shape.end();
		
		
	}
}