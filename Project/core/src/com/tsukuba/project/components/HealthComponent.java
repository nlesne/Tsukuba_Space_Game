package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {
    public int maxHealth;
    public int currentHealth;

    public HealthComponent(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public HealthComponent(int maxHealth, int currentHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
    }

    public HealthComponent() {
        this.maxHealth = 0;
        this.currentHealth = 0;
    }
}
