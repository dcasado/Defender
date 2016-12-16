package com.dcasado.defender.model.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.dcasado.defender.Defender;
import com.dcasado.defender.model.components.EnemyComponent;
import com.dcasado.defender.model.components.LifeComponent;
import com.dcasado.defender.model.components.Mappers;
import com.dcasado.defender.model.components.PlayerComponent;
import com.dcasado.defender.screens.MenuScreen;

/**
 * Created by david on 16/12/16.
 */

public class LifeSystem extends IteratingSystem {

    private Defender game;
    private Engine engine;

    public LifeSystem(Defender game, PooledEngine engine) {
        super(Family.all(LifeComponent.class).get());

        this.engine = engine;
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = Mappers.playerMapper.get(entity);
        EnemyComponent enemyComponent = Mappers.enemyMapper.get(entity);
        LifeComponent lifeComponent = Mappers.lifeMapper.get(entity);
        if (lifeComponent.life <= 0) {
            if (enemyComponent != null)
                engine.removeEntity(entity);
            if (playerComponent != null)
                game.setScreen(new MenuScreen(game));
        }
    }
}
