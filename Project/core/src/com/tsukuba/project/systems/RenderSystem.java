package com.tsukuba.project.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tsukuba.project.components.DrawableComponent;
import com.tsukuba.project.components.PositionComponent;

import java.util.Collections;

public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<DrawableComponent> dm = ComponentMapper.getFor(DrawableComponent.class);

    public RenderSystem(OrthographicCamera camera) {
        batch = new SpriteBatch();
        this.camera = camera;
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class,DrawableComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        PositionComponent position;
        DrawableComponent drawable;

        super.update(deltaTime);

        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            position = pm.get(e);
            drawable = dm.get(e);

            batch.draw(drawable.sprite, position.x, position.y);
        }

        batch.end();
    }
}
