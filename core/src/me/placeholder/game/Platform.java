package me.placeholder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.placeholder.game.entity.Entity;
import me.placeholder.game.entity.impl.player.EntityCurrentPlayer;

import java.util.ArrayList;

/**
 * Created by Adrian on 27/04/2018.
 */
public class Platform {

    private SpriteBatch spriteBatch;

    private ArrayList<Entity> entities;

    private EntityCurrentPlayer player;

    private World world;
    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    private FPSLogger logger;

    private ScreenViewport viewport;

    public Platform(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        entities = new ArrayList();
        renderer = new Box2DDebugRenderer();
        logger = new FPSLogger();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ScreenViewport(camera);

        world = new World(new Vector2(0, 0), false);

        player = new EntityCurrentPlayer(world, camera);

        BodyDef ground = new BodyDef();
        ground.position.set(0, -10);
        Body groundBody = world.createBody(ground);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(1000, 3.0f);
        groundBody.createFixture(groundShape, 0f);
    }

    private Vector3 getMousePosCamera() {
        return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

    /**
     * TODO: use {@link Interpolation} to do bounceIn
     */
    public void cameraUpdate() {
        camera.position.x = camera.position.x + (player.getBody().getPosition().x - camera.position.x) * 0.03f + (Gdx.input.getX() - Gdx.graphics.getWidth() / 2) / 150;
        camera.position.y = camera.position.y + (player.getBody().getPosition().y - camera.position.y) * 0.05f + (Gdx.graphics.getHeight() / 2 - Gdx.input.getY()) / 150;
        camera.update();
    }

    public void update() {
        world.step(1f / 60f, 6, 2);
        logger.log();

        cameraUpdate();
        player.lookMouse(getMousePosCamera().x, getMousePosCamera().y);
        player.update();
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        player.render(spriteBatch);
        renderer.render(world, camera.combined);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}