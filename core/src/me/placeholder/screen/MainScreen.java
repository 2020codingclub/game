package me.placeholder.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.placeholder.PlaceholderGame;

import static me.placeholder.utils.TexturesManager.playButtonTexture;

/**
 * Created by Adrian on 27/04/2018.
 */
public class MainScreen implements Screen {

    private Stage stage;
    private ImageButton playButton;
    private OrthographicCamera camera;
    private PlaceholderGame game;

    private Table buttonTable;

    private int centerX, centerY;


    public MainScreen(final PlaceholderGame game) {
        this.game = game;
        centerX = Gdx.graphics.getWidth() / 2;
        centerY = Gdx.graphics.getHeight() / 2;

        camera = new OrthographicCamera();
        stage = new Stage(new ScreenViewport(camera));

        buttonTable = new Table();
        playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(playButtonTexture)));

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        buttonTable.add(playButton);
        buttonTable.setPosition(centerX - buttonTable.getWidth() / 2, centerY - buttonTable.getHeight() / 2);
        stage.addActor(buttonTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        /**
         * crosshair using textures
         * advantage - rotates
         * disadvantage - updates with fps
         */
//        game.batch.begin();
//        game.batch.draw(crosshairTexture, getMouse().x, getMouse().y);
//        game.batch.end();

        camera.position.set(centerX + Gdx.input.getX() / 100, centerY + Gdx.input.getY() / 100, 0);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        centerX = Gdx.graphics.getWidth() / 2;
        centerY = Gdx.graphics.getHeight() / 2;

        buttonTable.setPosition(centerX - buttonTable.getWidth() / 2, centerY - buttonTable.getHeight() / 2);
        camera.update();
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        playButtonTexture.dispose();
        stage.dispose();
    }
}
