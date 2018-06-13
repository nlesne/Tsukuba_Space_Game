package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TypeComponent implements Component, Pool.Poolable {

    public enum EntityType {PLAYER,ENEMY,PLANET,PROJECTILE}

    public EntityType type;

    @Override
    public void reset() {
        type = null;
    }
}
