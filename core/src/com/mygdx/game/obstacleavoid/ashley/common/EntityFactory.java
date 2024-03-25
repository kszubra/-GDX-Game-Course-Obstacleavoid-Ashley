package com.mygdx.game.obstacleavoid.ashley.common;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.obstacleavoid.ashley.assets.AssetDescriptors;
import com.mygdx.game.obstacleavoid.ashley.assets.RegionNames;
import com.mygdx.game.obstacleavoid.ashley.component.*;
import com.mygdx.game.obstacleavoid.ashley.config.GameConfig;

public class EntityFactory {

    private final PooledEngine engine;
    private final AssetManager assetManager;
    private final TextureAtlas gamePlayAtlas;

    public EntityFactory(PooledEngine engine, AssetManager assetManager) {
        this.engine = engine;
        this.assetManager = assetManager;
        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
    }

    public void addPlayer() {
        float x = (GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE) / 2f;
        float y = 1 - GameConfig.PLAYER_SIZE / 2f;

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = x;
        position.y = y;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.bounds.set(x, y, GameConfig.PLAYER_BOUNDS_RADIUS);

        MovementComponent movement = engine.createComponent(MovementComponent.class);

        PlayerComponent player = engine.createComponent(PlayerComponent.class);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.PLAYER);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.PLAYER_SIZE;
        dimension.height = GameConfig.PLAYER_SIZE;


        Entity entity = engine.createEntity();
        entity.add(bounds);
        entity.add(movement);
        entity.add(player);
        entity.add(position);
        entity.add(worldWrap);
        entity.add(texture);
        entity.add(dimension);

        engine.addEntity(entity);
    }

    public void addObstacle(float x, float y) {
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.bounds.set(x, y, GameConfig.OBSTACLE_BOUNDS_RADIUS);

        MovementComponent movement = engine.createComponent(MovementComponent.class);
        movement.ySpeed = -GameManager.INSTANCE.getDifficultyLevel().getObstacleSpeed();

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = x;
        position.y = y;

        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        ObstacleComponent obstacle = engine.createComponent(ObstacleComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.OBSTACLE);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.OBSTACLE_SIZE;
        dimension.height = GameConfig.OBSTACLE_SIZE;

        Entity entity = engine.createEntity();
        entity.add(bounds);
        entity.add(movement);
        entity.add(position);
        entity.add(cleanUp);
        entity.add(obstacle);
        entity.add(texture);
        entity.add(dimension);

        engine.addEntity(entity);
    }

    public void addBackground() {
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = 0;
        position.y = 0;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.WORLD_WIDTH;
        dimension.height = GameConfig.WORLD_HEIGHT;

        Entity entity = engine.createEntity();
        entity.add(texture);
        entity.add(position);
        entity.add(dimension);

        engine.addEntity(entity);
    }
}
