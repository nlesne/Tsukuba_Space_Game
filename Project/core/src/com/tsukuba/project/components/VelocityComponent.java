package com.tsukuba.project.components;

import com.badlogic.gdx.math.Vector2;

public class VelocityComponent {
    public Vector2 velocity;

    public VelocityComponent(float dx, float dy) {
        velocity = new Vector2(dx,dy);
    }

    public VelocityComponent() {
        velocity = new Vector2();
    }
}
