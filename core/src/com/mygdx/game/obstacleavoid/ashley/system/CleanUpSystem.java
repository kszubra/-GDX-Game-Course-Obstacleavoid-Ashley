package com.mygdx.game.obstacleavoid.ashley.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.obstacleavoid.ashley.common.Mappers;
import com.mygdx.game.obstacleavoid.ashley.component.CleanUpComponent;
import com.mygdx.game.obstacleavoid.ashley.component.PositionComponent;
import com.mygdx.game.obstacleavoid.ashley.config.GameConfig;


public class CleanUpSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            CleanUpComponent.class,
            PositionComponent.class
    ).get();

    public CleanUpSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);

        if (position.y < -GameConfig.OBSTACLE_SIZE) {
            getEngine().removeEntity(entity);
        }
    }
}
