package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {
    public float dx;
    public float dy;

    public VelocityComponent(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public VelocityComponent() {
        this.dx = 0;
        this.dy = 0;
    }
}
