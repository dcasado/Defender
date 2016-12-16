package com.dcasado.defender.model.components;

import com.badlogic.ashley.core.ComponentMapper;

/**
 * Created by David on 23/01/2016.
 */
public class Mappers {
    public static final ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<EnemyComponent> enemyMapper = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    public static final ComponentMapper<SpriteComponent> spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
    public static final ComponentMapper<VelocityComponent> velocityMapper = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<ShooterComponent> shooterMapper = ComponentMapper.getFor(ShooterComponent.class);
    public static final ComponentMapper<LifeComponent> lifeMapper = ComponentMapper.getFor(LifeComponent.class);
    public static final ComponentMapper<DamageComponent> damageMapper = ComponentMapper.getFor(DamageComponent.class);
}
