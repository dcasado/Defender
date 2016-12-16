package com.dcasado.defender.model.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.dcasado.defender.model.components.BoundsComponent;
import com.dcasado.defender.model.components.DamageComponent;
import com.dcasado.defender.model.components.EnemyComponent;
import com.dcasado.defender.model.components.LifeComponent;
import com.dcasado.defender.model.components.Mappers;
import com.dcasado.defender.model.components.PlayerComponent;
import com.dcasado.defender.model.components.ShotComponent;
import com.dcasado.defender.model.components.VelocityComponent;

/**
 * Created by davidcasado on 3/2/16.
 */
public class CollisionSystem extends EntitySystem {

    private Engine pooledEngine;

    private Entity player;
    private ImmutableArray<Entity> enemies;
    private ImmutableArray<Entity> shots;

    @Override
    public void addedToEngine(Engine engine) {
        pooledEngine = engine;

        player = engine.getEntitiesFor(Family.one(PlayerComponent.class).get()).first();
        enemies = engine.getEntitiesFor(Family.all(EnemyComponent.class).get());
        shots = engine.getEntitiesFor(Family.all(ShotComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity enemy : enemies) {
            BoundsComponent enemyBounds = Mappers.boundsMapper.get(enemy);
            BoundsComponent playerBounds = Mappers.boundsMapper.get(player);
            for (Entity shot : shots) {
                BoundsComponent shotBounds = Mappers.boundsMapper.get(shot);

                if (enemyBounds.bounds.overlaps(shotBounds.bounds)) {
                    LifeComponent enemyLife = Mappers.lifeMapper.get(enemy);
                    DamageComponent shotDamage = Mappers.damageMapper.get(shot);
                    enemyLife.life -= shotDamage.damage;
                    pooledEngine.removeEntity(shot);
                }
            }
            if(enemyBounds.bounds.overlaps(playerBounds.bounds)){
                enemy.remove(VelocityComponent.class);
                LifeComponent playerLife = Mappers.lifeMapper.get(player);
                DamageComponent enemyDamage = Mappers.damageMapper.get(enemy);
                playerLife.life -= enemyDamage.damage*deltaTime;
            }
        }
    }
}
