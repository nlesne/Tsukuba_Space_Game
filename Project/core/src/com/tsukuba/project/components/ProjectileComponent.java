package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class ProjectileComponent implements Component, Pool.Poolable {
    public int damage = 0;
    public Entity shooter;
    public float lifespan = 0;

    @Override
    public void reset() {
        damage = 0;
        shooter = null;
        lifespan = 0;
    }
}
