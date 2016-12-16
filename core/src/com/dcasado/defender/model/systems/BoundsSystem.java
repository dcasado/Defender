package com.dcasado.defender.model.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.dcasado.defender.model.components.BoundsComponent;
import com.dcasado.defender.model.components.Mappers;
import com.dcasado.defender.model.components.PositionComponent;

/**
 * Created by davidcasado on 3/2/16.
 */
public class BoundsSystem extends IteratingSystem{

    public BoundsSystem() {
        super(Family.all(BoundsComponent.class, PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent boundsComponent = Mappers.boundsMapper.get(entity);
        PositionComponent positionComponent = Mappers.positionMapper.get(entity);

        boundsComponent.bounds.setX(positionComponent.position.x -
                boundsComponent.bounds.getWidth() / 2);
        boundsComponent.bounds.setY(positionComponent.position.y -
                boundsComponent.bounds.getHeight() / 2);
    }
}
