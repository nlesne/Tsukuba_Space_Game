package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;

public class EnemyTypeComponent implements Component {
    public enum EnemyType {MINE,SHOOTER}

    public EnemyType type;
}
