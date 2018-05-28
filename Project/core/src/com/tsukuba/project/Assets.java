package com.tsukuba.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    public static Texture ships;
    public static Texture enemies;
    public static Texture objects;

    public static TextureRegion playership;
    public static TextureRegion mineEnemy;
    public static TextureRegion shooterEnemy;
    public static TextureRegion arrow;

    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {
        ships = loadTexture("ships.jpg");
        enemies = loadTexture("enemies.jpg");
        objects = loadTexture("arrow.png");

        playership = new TextureRegion(ships,0,0,32,32);

        mineEnemy = new TextureRegion(enemies,64,0,0,0);
        shooterEnemy = new TextureRegion(enemies, 128, 0, 32, 32);
        arrow = new TextureRegion(objects,0,0,objects.getWidth(),objects.getHeight());
        

    }
}
