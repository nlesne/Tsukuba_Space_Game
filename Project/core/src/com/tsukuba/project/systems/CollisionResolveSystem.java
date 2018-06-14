package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.tsukuba.project.SpaceGame;
import com.tsukuba.project.components.*;
import com.tsukuba.project.screens.GameScreen;
import com.tsukuba.project.screens.PlanetHangarScreen;

import static com.tsukuba.project.components.TypeComponent.EntityType;

public class CollisionResolveSystem extends IteratingSystem {
    PooledEngine engine;
    SpaceGame game;
    GameScreen screen;

    public CollisionResolveSystem(PooledEngine engine, SpaceGame game, GameScreen screen) {
        super(Family.all(CollidingComponent.class,TransformComponent.class).get(),3);
        this.engine = engine;
        this.game = game;
        this.screen = screen;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollidingComponent collidingComponent = ComponentList.COLLIDING.get(entity);
        Entity collidingEntity = collidingComponent.collidingEntity;

        if (entity.isScheduledForRemoval() || collidingEntity.isScheduledForRemoval() || entity.getComponents().size() == 0 || collidingEntity.getComponents().size() == 0)
            return;

        HealthComponent health = ComponentList.HEALTH.get(entity);
        EntityType e1Type = ComponentList.TYPE.get(entity).type;
        EntityType e2Type = ComponentList.TYPE.get(collidingEntity).type;

        switch (e1Type) {
            case PLAYER:
                switch (e2Type){
                    case PROJECTILE:
                        ProjectileComponent projectile = ComponentList.PROJECTILE.get(collidingEntity);
                        if (!projectile.shooter.equals(entity)) {
                            health.currentHealth -= projectile.damage;
                            engine.removeEntity(collidingEntity);
                        }
                        break;
                    case ENEMY:
                        if (!ComponentList.COOLDOWN.has(entity)) {
                            EnemyComponent enemy = ComponentList.ENEMY.get(collidingEntity);
                            health.currentHealth -= enemy.contactDamage;
                            MovementComponent playerMovement = ComponentList.MOVEMENT.get(entity);
                            MovementComponent enemyMovement = ComponentList.MOVEMENT.get(collidingEntity);
                            playerMovement.velocity.scl(-1f);
                            EnemyComponent enemyComponent = ComponentList.ENEMY.get(collidingEntity);
                            if (enemyComponent.type == EnemyComponent.EnemyType.MINE) {
                                engine.removeEntity(collidingEntity);
                            }
                            else
                                enemyMovement.velocity.scl(-1f);
                            CooldownComponent cooldown = engine.createComponent(CooldownComponent.class);
                            cooldown.value = 3;
                            entity.add(cooldown);
                        }
                        break;
                    case PLANET:
                        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
                            game.setScreen(new PlanetHangarScreen(game, screen, engine,collidingEntity));
                        }
                        break;

                }
                if (health.currentHealth <= 0) {
                    PlayerComponent playerComponent = ComponentList.PLAYER.get(entity);
                    TransformComponent playerTransform = ComponentList.TRANSFORM.get(entity);
                    if (playerComponent.respawnPlanet != null) {
                        if (ComponentList.QUEST.has(entity))
                            entity.remove(QuestComponent.class);
                        game.setScreen(new PlanetHangarScreen(game,screen,engine,playerComponent.respawnPlanet));
                    }
                    else {
                        Entity rndPlanet = engine.getEntitiesFor(Family.exclude(MovementComponent.class,EnemyComponent.class,ProjectileComponent.class,PlayerComponent.class).get()).random();
                        game.setScreen(new PlanetHangarScreen(game,screen,engine,rndPlanet));
                    }
                }

                break;
            case ENEMY:
                switch (e2Type) {
                    case PROJECTILE:
                        ProjectileComponent projectile = ComponentList.PROJECTILE.get(collidingEntity);
                        TypeComponent type = ComponentList.TYPE.get(entity);
                        TypeComponent shooterType = ComponentList.TYPE.get(projectile.shooter);
                        if (type.type == null || type.type != shooterType.type) {
                            health.currentHealth -= projectile.damage;
                            engine.removeEntity(collidingEntity);
                            if (health.currentHealth <= 0) {
                                engine.removeEntity(entity);
                                Entity player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
                                if (projectile.shooter == player && ComponentList.QUEST.has(player)) {
                                    QuestComponent quest = ComponentList.QUEST.get(player);
                                    if (quest.type == QuestComponent.QuestType.KILL && quest.progress <= quest.objective) {
                                        quest.progress++;
                                    }
                                }
                            }
                        }
                        break;
                }
                break;
        }

        entity.remove(CollidingComponent.class);

    }
}
