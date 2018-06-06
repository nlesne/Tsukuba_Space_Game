package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.tsukuba.project.components.ComponentList;
import com.tsukuba.project.components.HitboxComponent;
import com.tsukuba.project.components.PlayerComponent;
import com.tsukuba.project.components.TransformComponent;

public class DebugSystem extends IteratingSystem{

	private ShapeRenderer shape;
	private SpriteBatch batch;
	private SpriteBatch hud;
	private BitmapFont font;
	
	private OrthographicCamera textCamera;

	public DebugSystem(SpriteBatch batch) {
		super(Family.all(HitboxComponent.class,TransformComponent.class).get());
		this.batch = batch;

		shape = new ShapeRenderer();
		font = new BitmapFont();
		hud = new SpriteBatch();
	    
        float aspectRatio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
        float viewPortWidth = 20;
        float viewPortHeight = viewPortWidth * aspectRatio;

        float cameraViewPortWidth = 1024; // Set the size of the viewport for the text to something big
        float cameraViewPortHeight = cameraViewPortWidth * aspectRatio;
		
		textCamera = new OrthographicCamera(cameraViewPortWidth, cameraViewPortHeight);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		HitboxComponent hitbox = ComponentList.HITBOX.get(entity);
		TransformComponent transform = ComponentList.TRANSFORM.get(entity);
		
		Entity playerEntity = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
		TransformComponent posPlayer = ComponentList.TRANSFORM.get(playerEntity);

		hud.setProjectionMatrix(textCamera.combined);
		shape.setProjectionMatrix(batch.getProjectionMatrix());


		shape.begin(ShapeRenderer.ShapeType.Line);
		shape.polygon(hitbox.shape.getTransformedVertices());
		shape.end();
		
		hud.begin();
		font.getData().setScale(1.7f);
		font.draw(hud, "X : " + Math.floor(posPlayer.position.x) + "  |  Y : " + Math.floor(posPlayer.position.y),-90 , 80);
		hud.end();

	}
}