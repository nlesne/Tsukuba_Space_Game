package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;

public class HitboxComponent implements Component {
    public Polygon shape;
    public int contactDamage = 1;
    public float width;
    public float height;
}
