package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {
    public float baseShootCooldown = 0;
    public int money = 0;
    public Entity respawnPlanet = null;

    @Override
    public void reset() {
        baseShootCooldown = 0;
        money = 0;
        respawnPlanet = null;
    }
}
