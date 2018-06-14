package com.tsukuba.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    public static Texture spaceship;
    public static Texture spaceship_firing;
    public static Texture arrow;
    public static Texture kamikaze;
    public static Texture shooter;
    public static Texture bullets;
    public static Texture planet;
    public static Texture planetHangar;
    public static Texture asteroid;
    public static Texture hangarBackground;
    public static Texture engineer;
    public static Texture pilot;
    public static Texture hud_fullH;
    public static Texture hud_halfH;
    public static Texture hud_emptyH;
    public static Texture hud_core1;
    public static Texture hud_core2;
    public static Texture hud_core3;
    public static Texture hud_weapon1;
    public static Texture hud_weapon2;
    public static Texture hud_weapon3;
    public static Texture hud_rocket1;
    public static Texture hud_rocket2;
    public static Texture hud_rocket3;
    public static Texture boss;
   
    
    public static TextureRegion bulletLvl1;
    public static TextureRegion bulletLvl2;
    public static TextureRegion bulletLvl3;

    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {
        boss = loadTexture("old_spaceship.png");
    	hud_core1 = loadTexture("spaceship_upgrade/spaceship_core.png");
    	hud_core2 = loadTexture("spaceship_upgrade/spaceship_core2.png");
    	hud_core3 = loadTexture("spaceship_upgrade/spaceship_core3.png");
        hud_rocket1 = loadTexture("spaceship_upgrade/spaceship_rocket.png");
    	hud_rocket2 = loadTexture("spaceship_upgrade/spaceship_rocket2.png");
        hud_rocket3 = loadTexture("spaceship_upgrade/spaceship_rocket3.png");
    	hud_weapon1 = loadTexture("spaceship_upgrade/spaceship_turret.png");
        hud_weapon2 = loadTexture("spaceship_upgrade/spaceship_turret2.png");
        hud_weapon3 = loadTexture("spaceship_upgrade/spaceship_turret3.png");
    	hud_fullH = loadTexture("fullHeart.png");
    	hud_halfH = loadTexture("halfHeart.png");
    	hud_emptyH = loadTexture("emptyHeart.png");
    	pilot = loadTexture("pilot.png");
    	engineer = loadTexture("engineer.png");
    	hangarBackground = loadTexture("hangarBackground.jpg");
    	asteroid = loadTexture("asteroid.png");
    	planet = loadTexture("planet1.png");
    	planetHangar = loadTexture("planet.png");
    	spaceship_firing = loadTexture("spaceship_firing.png");
    	spaceship = loadTexture("spaceship.png");
        kamikaze = loadTexture("kamikaze.png");
        shooter = loadTexture("enemy.png");
        arrow = loadTexture("arrow.png");
        bullets = loadTexture("bullets.png");
        bulletLvl1 = new TextureRegion(bullets,21,32,10,6);
        bulletLvl2 = new TextureRegion(bullets,21,50,10,6);
        bulletLvl3 = new TextureRegion(bullets, 21,14,10,6);
     }
}
