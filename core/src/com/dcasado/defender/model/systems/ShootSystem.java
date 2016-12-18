package com.dcasado.defender.model.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dcasado.defender.Assets;
import com.dcasado.defender.World;
import com.dcasado.defender.model.components.BoundsComponent;
import com.dcasado.defender.model.components.DamageComponent;
import com.dcasado.defender.model.components.Mappers;
import com.dcasado.defender.model.components.PositionComponent;
import com.dcasado.defender.model.components.RenderComponent;
import com.dcasado.defender.model.components.ShooterComponent;
import com.dcasado.defender.model.components.ShotComponent;
import com.dcasado.defender.model.components.SpriteComponent;
import com.dcasado.defender.model.components.VelocityComponent;

/**
 * Created by David on 23/01/2016.
 */
public class ShootSystem extends IteratingSystem {
    private Vector2 touch;
    private World world;
    private PooledEngine engine;

    public ShootSystem(PooledEngine engine, World world, Vector2 touch) {
        super(Family.all(ShooterComponent.class).get());
        this.engine = engine;
        this.world = world;
        this.touch = touch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent positionComponent = Mappers.positionMapper.get(entity);
        ShooterComponent shooterComponent = Mappers.shooterMapper.get(entity);
        BoundsComponent boundsComponent = Mappers.boundsMapper.get(entity);

        float delay = shooterComponent.temp;
        if (delay >= 0)
            delay -= deltaTime;

        if (delay < 0 && world.touched) {
            createShot(positionComponent.position, boundsComponent.bounds);
            shooterComponent.temp = shooterComponent.interval;
        } else {
            shooterComponent.temp = delay;
        }
    }

    private void createShot(Vector2 position, Rectangle entityBounds) {

        Entity entity = engine.createEntity();

        ShotComponent shotComponent = engine.createComponent(ShotComponent.class);
        RenderComponent renderComponent = engine.createComponent(RenderComponent.class);
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        SpriteComponent spriteComponent = engine.createComponent(SpriteComponent.class);
        PositionComponent positionComponent = engine.createComponent(PositionComponent.class);
        VelocityComponent velocityComponent = engine.createComponent(VelocityComponent.class);
        DamageComponent damageComponent = engine.createComponent(DamageComponent.class);

        boundsComponent.bounds.setWidth(ShotComponent.WIDTH);
        boundsComponent.bounds.setHeight(ShotComponent.HEIGHT);

        spriteComponent.sprite.set(Assets.shotSprite);
        positionComponent.position.set(position.x +
                entityBounds.getWidth() * 0.5f +
                boundsComponent.bounds.getWidth() * 0.5f, position.y);

        velocityComponent.velocity
                .set(touch.x - position.x, -(touch.y - position.y))
                .nor()
                .scl(150);

        damageComponent.damage = 2;

        entity.add(shotComponent)
                .add(boundsComponent)
                .add(renderComponent)
                .add(spriteComponent)
                .add(positionComponent)
                .add(velocityComponent)
                .add(damageComponent);

        engine.addEntity(entity);
    }
}
