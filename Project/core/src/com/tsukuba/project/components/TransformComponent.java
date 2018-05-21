package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TransformComponent implements Component {
    public Vector3 position;
    public Vector2 scale;
    public float rotation;

    public TransformComponent(Vector3 position, Vector2 scale, float rotation) {
        this.position = new Vector3(position);
        this.scale = new Vector2(scale);
        this.rotation = rotation;
    }

    public TransformComponent() {
        this.position = new Vector3();
        this.scale = new Vector2(1.0f,1.0f);
        this.rotation = 0.0f;
    }

}
