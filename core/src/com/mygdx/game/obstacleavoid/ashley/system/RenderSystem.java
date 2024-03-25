package com.mygdx.game.obstacleavoid.ashley.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.obstacleavoid.ashley.common.Mappers;
import com.mygdx.game.obstacleavoid.ashley.component.DimensionComponent;
import com.mygdx.game.obstacleavoid.ashley.component.PositionComponent;
import com.mygdx.game.obstacleavoid.ashley.component.TextureComponent;

public class RenderSystem extends EntitySystem {

    private static final Family FAMILY = Family.all(
            TextureComponent.class,
            PositionComponent.class,
            DimensionComponent.class
    ).get();

    private final Viewport viewport;
    private final SpriteBatch batch;

    private Array<Entity> renderQueue = new Array<Entity>();

    public RenderSystem(Viewport viewport, SpriteBatch batch) {
        this.viewport = viewport;
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(FAMILY);
//        Line commented out because causes: Exception in thread "main" java.lang.ClassCastException: class [Ljava.lang.Object; cannot be cast to class [Lcom.badlogic.ashley.core.Entity; ([Ljava.lang.Object; is in module java.base of loader 'bootstrap'; [Lcom.badlogic.ashley.core.Entity; is in unnamed module of loader 'app')
//        renderQueue.addAll(entities.toArray());
        entities.forEach(entity -> renderQueue.add(entity));

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        draw();

        batch.end();

        renderQueue.clear();
    }

    private void draw() {
        for (Entity entity : renderQueue) {
            PositionComponent position = Mappers.POSITION.get(entity);
            DimensionComponent dimension = Mappers.DIMENSION.get(entity);
            TextureComponent texture = Mappers.TEXTURE.get(entity);

            batch.draw(texture.region,
                    position.x, position.y,
                    dimension.width, dimension.height);
        }
    }
}
