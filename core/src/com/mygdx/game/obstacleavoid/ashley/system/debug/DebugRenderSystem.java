package com.mygdx.game.obstacleavoid.ashley.system.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.obstacleavoid.ashley.common.Mappers;
import com.mygdx.game.obstacleavoid.ashley.component.BoundsComponent;

public class DebugRenderSystem extends IteratingSystem { //Itrerating system for systems that iterate over family of items

    private static final Family FAMILY = Family.all(BoundsComponent.class).get();


    private final Viewport viewport;
    private final ShapeRenderer renderer;

    public DebugRenderSystem(Viewport viewport, ShapeRenderer renderer) {
        super(FAMILY);
        this.viewport = viewport;
        this.renderer = renderer;
    }

    //called every frame
    @Override
    public void update(float deltaTime) {
        Color oldColor = renderer.getColor().cpy();
        viewport.apply();
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);

        super.update(deltaTime); //calls processEntity(). begin/end here to draw all objects within one begin/end section.
        // Calling begin/end for every element would make rendering very slow

        renderer.end();
        renderer.setColor(oldColor);
    }


    //called for every entity in the family in every frame
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent bc = Mappers.BOUNDS.get(entity);
        renderer.circle(bc.bounds.x, bc.bounds.y, bc.bounds.radius, 30);

    }
}
