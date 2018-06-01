package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component {
    public enum EntityType {PLAYER,ENEMY,PLANET,PROJECTILE}

    public EntityType type;
}
