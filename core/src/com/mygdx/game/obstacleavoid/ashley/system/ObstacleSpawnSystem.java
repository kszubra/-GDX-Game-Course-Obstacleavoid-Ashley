package com.mygdx.game.obstacleavoid.ashley.system;

import  com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.obstacleavoid.ashley.common.EntityFactory;
import com.mygdx.game.obstacleavoid.ashley.config.GameConfig;

public class ObstacleSpawnSystem extends IntervalSystem {

    private final EntityFactory factory;

    public ObstacleSpawnSystem(EntityFactory factory) {
        super(GameConfig.OBSTACLE_SPAWN_TIME);
        this.factory = factory;
    }

    @Override
    protected void updateInterval() {
        float min = 0;
        float max = GameConfig.WORLD_WIDTH - GameConfig.OBSTACLE_SIZE;

        float obstacleX = MathUtils.random(min, max);
        float obstacleY = GameConfig.WORLD_HEIGHT;

        factory.addObstacle(obstacleX, obstacleY);
    }
}
