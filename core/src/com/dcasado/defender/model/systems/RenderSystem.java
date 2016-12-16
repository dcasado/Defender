package com.dcasado.defender.model.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dcasado.defender.model.components.Mappers;
import com.dcasado.defender.model.components.PositionComponent;
import com.dcasado.defender.model.components.RenderComponent;
import com.dcasado.defender.model.components.SpriteComponent;

/**
 * Created by David on 23/01/2016.
 */
public class RenderSystem extends IteratingSystem {
    private SpriteBatch batch;

    public RenderSystem(SpriteBatch batch) {
        super(Family.all(RenderComponent.class, SpriteComponent.class, PositionComponent.class).get());

        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SpriteComponent spriteComponent = Mappers.spriteMapper.get(entity);
        PositionComponent positionComponent = Mappers.positionMapper.get(entity);

        batch.begin();
        {
            batch.draw(spriteComponent.sprite, positionComponent.position.x - spriteComponent.sprite.getWidth() / 2,
                    positionComponent.position.y - spriteComponent.sprite.getHeight() /2);
        }
        batch.end();
    }
}
