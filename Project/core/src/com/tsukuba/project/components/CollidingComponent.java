package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class CollidingComponent implements Component, Pool.Poolable {
    public Entity collidingEntity;

    @Override
    public void reset() {
        collidingEntity = null;
    }
}
