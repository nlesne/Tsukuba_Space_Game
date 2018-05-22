package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HealthComponent implements Component, Pool.Poolable {
    public int maxHealth = 0;
    public int currentHealth = 0;

    @Override
    public void reset() {
        maxHealth = 0;
        currentHealth = 0;
    }
}
