package com.mygdx.game.obstacleavoid.ashley.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.obstacleavoid.ashley.common.Mappers;
import com.mygdx.game.obstacleavoid.ashley.component.DimensionComponent;
import com.mygdx.game.obstacleavoid.ashley.component.PositionComponent;
import com.mygdx.game.obstacleavoid.ashley.component.WorldWrapComponent;

public class WorldWrapSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            WorldWrapComponent.class,
            PositionComponent.class,
            DimensionComponent.class
    ).get();

    private final Viewport viewport;

    public WorldWrapSystem(Viewport viewport) {
        super(FAMILY);
        this.viewport = viewport;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);

        position.x = MathUtils.clamp(position.x, 0, viewport.getWorldWidth() - dimension.width);
        position.y = MathUtils.clamp(position.y, 0, viewport.getWorldHeight() - dimension.height);
    }
}
