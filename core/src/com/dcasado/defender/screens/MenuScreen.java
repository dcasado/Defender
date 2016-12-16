package com.dcasado.defender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dcasado.defender.Assets;
import com.dcasado.defender.Defender;

/**
 * Created by David on 17/01/2016.
 */
public class MenuScreen extends ScreenAdapter {
    private Defender game;
    private Stage stage;
    private Viewport viewport;
    private GlyphLayout layout;
    private Label titleLabel;
    private TextButton playButton;
    private TextButton exitButton;

    public MenuScreen(Defender game) {
        this.game = game;
        this.stage = new Stage();
        this.viewport = new ExtendViewport(1280, 720);
        stage.setViewport(viewport);
        Gdx.input.setInputProcessor(stage);
        layout = new GlyphLayout();

        createMenu();
    }

    private void createMenu() {
        titleLabel = new Label("Defender", Assets.skin.get("titleLabel", Label.LabelStyle.class));
        stage.addActor(titleLabel);

        createButtons();
    }

    private void createButtons() {

        //PlayButton
        playButton = new TextButton("Play", Assets.skin.get("textButton", TextButton
                .TextButtonStyle.class));
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        stage.addActor(playButton);

        //ExitButton
        exitButton = new TextButton("Exit", Assets.skin.get("textButton", TextButton
                .TextButtonStyle.class));
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

        //float viewportWidth = viewport.getWorldWidth();
        float viewportHeight = viewport.getWorldHeight();

        setActorPosition(titleLabel, "robotoCondensedTitle", "Defender", 0, viewportHeight * 0.35f);
        setActorPosition(playButton, "robotoCondensedButton", "Play", 0, 0);
        setActorPosition(exitButton, "robotoCondensedButton", "Exit", 0, viewportHeight * -0.11f);

        Gdx.app.log(Assets.TAG, "World " + viewport.getWorldHeight());
        Gdx.app.log(Assets.TAG, "Screen " + viewport.getScreenHeight());
    }

    private void setActorPosition(Actor actor, String font, String text, float x, float y) {
        layout.setText(Assets.skin.getFont(font), text);
        actor.setPosition(x -layout.width / 2,y -layout.height / 2);
    }

    @Override
    public void render(float delta) {
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
