package com.dcasado.defender;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.dcasado.defender.model.components.BoundsComponent;
import com.dcasado.defender.model.components.DamageComponent;
import com.dcasado.defender.model.components.EnemyComponent;
import com.dcasado.defender.model.components.LifeComponent;
import com.dcasado.defender.model.components.PlayerComponent;
import com.dcasado.defender.model.components.PositionComponent;
import com.dcasado.defender.model.components.RenderComponent;
import com.dcasado.defender.model.components.ShooterComponent;
import com.dcasado.defender.model.components.SpriteComponent;
import com.dcasado.defender.model.components.VelocityComponent;

/**
 * Created by David on 31/01/2016.
 */
public class World {
    public boolean touched;
    private PooledEngine pooledEngine;

    public World(PooledEngine pooledEngine) {
        this.pooledEngine = pooledEngine;
    }

    public void create(){
        createPlayer();
        createEnemy();
    }

    private Entity createPlayer() {
        Entity entity = pooledEngine.createEntity();

        BoundsComponent boundsComponent = pooledEngine.createComponent
                (BoundsComponent.class);
        PlayerComponent playerComponent =
                pooledEngine.createComponent(PlayerComponent.class);
        RenderComponent renderComponent =
                pooledEngine.createComponent(RenderComponent.class);
        SpriteComponent spriteComponent =
                pooledEngine.createComponent(SpriteComponent.class);
        PositionComponent positionComponent =
                pooledEngine.createComponent(PositionComponent.class);
        ShooterComponent shooterComponent =
                pooledEngine.createComponent(ShooterComponent.class);
        LifeComponent lifeComponent =
                pooledEngine.createComponent(LifeComponent.class);

        lifeComponent.life = 100;

        boundsComponent.bounds.setWidth(PlayerComponent.WIDTH);
        boundsComponent.bounds.setHeight(PlayerComponent.HEIGHT);

        spriteComponent.sprite.set(Assets.playerSprite);

        positionComponent.position.set(90, Gdx.graphics.getHeight() / 2);

        shooterComponent.interval = 1;

        entity.add(playerComponent)
                .add(boundsComponent)
                .add(renderComponent)
                .add(spriteComponent)
                .add(positionComponent)
                .add(shooterComponent)
                .add(lifeComponent);

        pooledEngine.addEntity(entity);

        return entity;
    }

    private void createEnemy() {
        Entity entity = pooledEngine.createEntity();

        EnemyComponent enemyComponent =
                pooledEngine.createComponent(EnemyComponent.class);
        LifeComponent lifeComponent =
                pooledEngine.createComponent(LifeComponent.class);
        lifeComponent.life = 20;
        BoundsComponent boundsComponent = pooledEngine.createComponent
                (BoundsComponent.class);
        boundsComponent.bounds.setWidth(EnemyComponent.WIDTH);
        boundsComponent.bounds.setHeight(EnemyComponent.HEIGHT);
        SpriteComponent spriteComponent = pooledEngine.createComponent
                (SpriteComponent.class);
        spriteComponent.sprite.set(Assets.enemySprite);
        RenderComponent renderComponent = pooledEngine.createComponent
                (RenderComponent.class);
        PositionComponent positionComponent = pooledEngine.createComponent
                (PositionComponent.class);
        positionComponent.position.set(1000, Gdx.graphics.getHeight() / 2);
        VelocityComponent velocityComponent = pooledEngine.createComponent
                (VelocityComponent.class);
        velocityComponent.velocity.x = -20;
        DamageComponent damageComponent = pooledEngine.createComponent(DamageComponent.class);
        damageComponent.damage = 5;

        entity.add(enemyComponent)
                .add(boundsComponent)
                .add(spriteComponent)
                .add(renderComponent)
                .add(positionComponent)
                .add(velocityComponent)
                .add(lifeComponent)
                .add(damageComponent);

        pooledEngine.addEntity(entity);
    }
}
