package com.dcasado.defender.model.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.dcasado.defender.model.components.Mappers;
import com.dcasado.defender.model.components.PositionComponent;
import com.dcasado.defender.model.components.VelocityComponent;

/**
 * Created by David on 23/01/2016.
 */
public class MovementSystem extends IteratingSystem {

    public MovementSystem() {
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent positionComponent = Mappers.positionMapper.get(entity);
        VelocityComponent velocityComponent = Mappers.velocityMapper.get(entity);

        positionComponent.position.mulAdd(velocityComponent.velocity, deltaTime);
    }
}
