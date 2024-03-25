package com.mygdx.game.obstacleavoid.ashley.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.obstacleavoid.ashley.common.Mappers;
import com.mygdx.game.obstacleavoid.ashley.component.MovementComponent;
import com.mygdx.game.obstacleavoid.ashley.component.PlayerComponent;
import com.mygdx.game.obstacleavoid.ashley.config.GameConfig;

public class PlayerSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PlayerComponent.class,
            MovementComponent.class
    ).get();

    public PlayerSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movement = Mappers.MOVEMENT.get(entity);

        movement.xSpeed = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movement.xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movement.xSpeed = -GameConfig.MAX_PLAYER_X_SPEED;
        }

        // test code
//        movement.ySpeed = 0;
//
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//            movement.ySpeed = GameConfig.MAX_PLAYER_X_SPEED;
//        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//            movement.ySpeed = -GameConfig.MAX_PLAYER_X_SPEED;
//        }

        // end test code

    }
}
