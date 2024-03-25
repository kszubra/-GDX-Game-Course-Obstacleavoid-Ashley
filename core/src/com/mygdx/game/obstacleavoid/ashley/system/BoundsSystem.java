package com.mygdx.game.obstacleavoid.ashley.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.obstacleavoid.ashley.common.Mappers;
import com.mygdx.game.obstacleavoid.ashley.component.BoundsComponent;
import com.mygdx.game.obstacleavoid.ashley.component.DimensionComponent;
import com.mygdx.game.obstacleavoid.ashley.component.PositionComponent;

public class BoundsSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            BoundsComponent.class,
            PositionComponent.class,
            DimensionComponent.class
    ).get();

    public BoundsSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent bounds = Mappers.BOUNDS.get(entity);
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);

        bounds.bounds.x = position.x + dimension.width / 2f;
        bounds.bounds.y = position.y + dimension.height / 2f;
    }
}
