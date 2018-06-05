package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class UpgradeComponent implements Component, Pool.Poolable {
    public int turretLevel = 1;
    public int coreLevel = 1;
    public int rocketLevel = 1;

    @Override
    public void reset() {
    	turretLevel = 1;
        coreLevel = 1;
        rocketLevel = 1;
    }
}
