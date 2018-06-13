package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class UpgradeComponent implements Component, Pool.Poolable {
    public int weaponLevel = 1;
    public int coreLevel = 1;
    public int thrustersLevel = 1;

    @Override
    public void reset() {
    	weaponLevel = 1;
        coreLevel = 1;
        thrustersLevel = 1;
    }
}
