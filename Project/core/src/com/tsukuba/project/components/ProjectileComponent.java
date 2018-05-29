package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ProjectileComponent implements Component, Pool.Poolable {
    public int contactDamage = 0;

    @Override
    public void reset() {
        contactDamage = 0;
    }
}
