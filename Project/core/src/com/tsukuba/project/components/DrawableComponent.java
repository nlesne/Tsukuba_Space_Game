package com.tsukuba.project.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawableComponent {
    public TextureRegion sprite;
    public int drawPriority;

    public DrawableComponent(TextureRegion sprite, int drawPriority) {
        this.sprite = sprite;
        this.drawPriority = drawPriority;
    }

    public DrawableComponent(TextureRegion sprite) {
        this.sprite = sprite;
        this.drawPriority = 0;
    }
}
