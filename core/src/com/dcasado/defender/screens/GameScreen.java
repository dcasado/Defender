package com.dcasado.defender.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dcasado.defender.Assets;
import com.dcasado.defender.Defender;
import com.dcasado.defender.World;
import com.dcasado.defender.model.systems.BoundsSystem;
import com.dcasado.defender.model.systems.CollisionSystem;
import com.dcasado.defender.model.systems.LifeSystem;
import com.dcasado.defender.model.systems.MovementSystem;
import com.dcasado.defender.model.systems.RenderSystem;
import com.dcasado.defender.model.systems.ShootSystem;

/**
 * Created by David on 20/01/2016.
 */
public class GameScreen extends ScreenAdapter {
    private ExitDialog exitDialog;
    private Defender game;
    private PooledEngine pooledEngine;
    private Stage stage;
    private World world;
    private Vector2 touch;
    private State state;
    private Viewport viewport;

    public GameScreen(Defender game) {
        viewport = new FitViewport(1280, 720);
        Gdx.input.setCatchBackKey(true);
        this.game = game;
        this.pooledEngine = new PooledEngine();
        this.stage = new Stage();
        exitDialog = new ExitDialog();
        world = new World(pooledEngine);
        touch = new Vector2();

        world.create();

        pooledEngine.addSystem(new MovementSystem());
        pooledEngine.addSystem(new BoundsSystem());
        pooledEngine.addSystem(new CollisionSystem());
        pooledEngine.addSystem(new RenderSystem(game.getBatch()));
        pooledEngine.addSystem(new ShootSystem(pooledEngine, world, touch));
        pooledEngine.addSystem(new LifeSystem(game, pooledEngine));

        state = State.RUNNING;
    }

    @Override
    public void render(float delta) {
        processTouches();
        pooledEngine.update(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void processTouches() {
        if (Gdx.input.isTouched()) {
            world.touched = true;
            touch.set(Gdx.input.getX(), Gdx.input.getY());
        } else {
            world.touched = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed
                (Input.Keys.BACKSPACE)) {
            pause();
        }
    }

    @Override
    public void pause() {
        if (state == State.RUNNING) {
            exitDialog.show(stage);
            Gdx.input.setInputProcessor(stage);
            pauseSystems();
            state = State.PAUSED;
        }
    }

    private void pauseSystems() {
        pooledEngine.getSystem(MovementSystem.class).setProcessing(false);
        pooledEngine.getSystem(BoundsSystem.class).setProcessing(false);
        pooledEngine.getSystem(CollisionSystem.class).setProcessing(false);
        pooledEngine.getSystem(ShootSystem.class).setProcessing(false);
    }

    private void resumeSystems() {
        pooledEngine.getSystem(MovementSystem.class).setProcessing(true);
        pooledEngine.getSystem(BoundsSystem.class).setProcessing(true);
        pooledEngine.getSystem(CollisionSystem.class).setProcessing(true);
        pooledEngine.getSystem(ShootSystem.class).setProcessing(true);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        pooledEngine.clearPools();
    }

    private enum State {
        PAUSED,
        RUNNING
    }

    class ExitDialog extends Dialog {

        public ExitDialog() {
            super("Do you want to exit the level?", Assets.skin, "dialog");
            TextButton yesButton = new TextButton("Yes", Assets.skin, "dialogButton");
            button(yesButton, true);
            //button("Yes", true, Assets.skin.get("dialogButton", TextButton.TextButtonStyle.class));
            TextButton noButton = new TextButton("No", Assets.skin, "dialogButton");
            button(noButton, false);
            //button("No", false, Assets.skin.get("dialogButton", TextButton.TextButtonStyle.class));
            getContentTable().padBottom(viewport.getWorldHeight() * 0.37f);
            getButtonTable().getCell(yesButton).expand();
            getButtonTable().getCell(noButton).expand();
            setModal(true);
            setMovable(false);
            setResizable(false);

            key(Input.Keys.ENTER, false);
            key(Input.Keys.ESCAPE, true);

            Gdx.app.log(Assets.TAG, "Dialog width: " + getWidth());
            Gdx.app.log(Assets.TAG, "Dialog height: " + getHeight());
            Gdx.app.log(Assets.TAG, "Dialog prefWidth: " + getPrefWidth());
        }

        @Override
        protected void result(Object object) {
            if ((Boolean) object) {
                game.setScreen(new MenuScreen(game));
                dispose();
            } else {
                hide();
                cancel();
                remove();
                state = State.RUNNING;
                resumeSystems();
            }
        }
    }
    /*
    private void createDialog() {

        final Dialog dialog =
                new Dialog("Holi", Assets.skin, "dialog") {
                    protected void result(Object object) {
                        Gdx.app.log(Assets.TAG, "Chosen: " + object);
                    }
                };
        dialog.padTop(50).padBottom(50);
        dialog.getButtonTable().padTop(50);

        TextButton dbutton = new TextButton("Yes", Assets.skin, "textButton");
        dbutton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
                dispose();
            }
        });
        dialog.button(dbutton, true);

        dbutton = new TextButton("No", Assets.skin, "textButton");
        dbutton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });
        dialog.button(dbutton, false);
        dialog.key(Input.Keys.ENTER, true).key(Input.Keys.ESCAPE, false);
        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        dialog.show(stage);

        Gdx.input.setInputProcessor(stage);
    }*/
}
