package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;

public class CooldownComponent implements Component {
    public float value = 0;
    public Component storedComponent;
}
