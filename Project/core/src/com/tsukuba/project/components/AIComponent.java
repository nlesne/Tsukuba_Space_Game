package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class AIComponent implements Component, Pool.Poolable {

    public enum AIState {WANDER,CHARGE,IDLE,FLEE,AIM}

    public AIState state = AIState.IDLE;
    public float detectionRadius;
    public Entity target;

    @Override
    public void reset() {
        state = AIState.IDLE;
        target = null;
        detectionRadius = 0f;
    }
}
