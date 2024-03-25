package com.mygdx.game.obstacleavoid.ashley.entity;

import com.mygdx.game.obstacleavoid.ashley.config.GameConfig;

public class Player extends GameObjectBase {

    public Player() {
        super(GameConfig.PLAYER_BOUNDS_RADIUS);
        setSize(GameConfig.PLAYER_SIZE, GameConfig.PLAYER_SIZE);
    }
    
}
