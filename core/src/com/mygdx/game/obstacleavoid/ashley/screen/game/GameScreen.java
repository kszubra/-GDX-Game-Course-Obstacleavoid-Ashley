package com.mygdx.game.obstacleavoid.ashley.screen.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.obstacleavoid.ashley.ObstacleAvoidGame;
import com.mygdx.game.obstacleavoid.ashley.assets.AssetDescriptors;
import com.mygdx.game.obstacleavoid.ashley.common.EntityFactory;
import com.mygdx.game.obstacleavoid.ashley.common.GameManager;
import com.mygdx.game.obstacleavoid.ashley.config.GameConfig;
import com.mygdx.game.obstacleavoid.ashley.screen.menu.MenuScreen;
import com.mygdx.game.obstacleavoid.ashley.system.*;
import com.mygdx.game.obstacleavoid.ashley.system.collision.CollisionListener;
import com.mygdx.game.obstacleavoid.ashley.system.collision.CollisionSystem;
import com.mygdx.game.obstacleavoid.ashley.system.debug.DebugCameraSystem;
import com.mygdx.game.obstacleavoid.ashley.system.debug.DebugRenderSystem;
import com.mygdx.game.obstacleavoid.ashley.system.debug.GridRenderSystem;
import com.mygdx.game.obstacleavoid.ashley.util.GdxUtils;

public class GameScreen implements Screen {

    private static final Logger log = new Logger(GameScreen.class.getName(), Logger.DEBUG);
    private static final boolean DEBUG = false;

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;
    private PooledEngine engine;
    private EntityFactory factory;
    private Sound hit;
    private boolean reset;

    public GameScreen(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        log.debug("show()");
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        renderer = new ShapeRenderer();
        engine = new PooledEngine();
        factory = new EntityFactory(engine, assetManager);

        BitmapFont font = assetManager.get(AssetDescriptors.FONT);
        hit = assetManager.get(AssetDescriptors.HIT_SOUND);

        CollisionListener listener = new CollisionListener() {
            @Override
            public void hitObstacle() {
                GameManager.INSTANCE.decrementLives();
                hit.play();

                if (GameManager.INSTANCE.isGameOver()) {
                    GameManager.INSTANCE.updateHighScore();
                } else {
                    engine.removeAllEntities();
                    reset = true;
                }
            }
        };

        engine.addSystem(new PlayerSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new WorldWrapSystem(viewport));
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new ObstacleSpawnSystem(factory));
        engine.addSystem(new CleanUpSystem());
        engine.addSystem(new CollisionSystem(listener));
        engine.addSystem(new ScoreSystem());
        engine.addSystem(new RenderSystem(viewport, game.getBatch()));
        engine.addSystem(new HudRenderSystem(hudViewport, game.getBatch(), font));

        if(DEBUG) {
            engine.addSystem(new GridRenderSystem(viewport, renderer));
            engine.addSystem(new DebugRenderSystem(viewport, renderer));
            engine.addSystem(new DebugCameraSystem(camera,
                    GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y));
        }

        addEntities();
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        engine.update(delta);
//        log.debug("entities size= " + engine.getEntities().size());

        if (GameManager.INSTANCE.isGameOver()) {
            GameManager.INSTANCE.reset();
            game.setScreen(new MenuScreen(game));
        }

        if (reset) {
            reset = false;
            addEntities();
        }
    }

    private void addEntities() {
        factory.addBackground();
        factory.addPlayer();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        log.debug("hide");
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
