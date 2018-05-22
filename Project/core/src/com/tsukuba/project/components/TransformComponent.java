package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class TransformComponent implements Component, Pool.Poolable {
    public Vector3 position = new Vector3();
    public Vector2 scale = new Vector2(1.0f,1.0f);
    public float rotation = 0;

    @Override
    public void reset() {
        position = new Vector3();
        scale = new Vector2(1.0f,1.0f);
        rotation = 0;
    }
}
