package com.dcasado.defender;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Created by David on 17/01/2016.
 */
public class Assets {

    //Log Tag
    public final static String TAG = "DEFENDER";

    public static Skin skin;
    public static Sprite playerSprite;
    public static Sprite enemySprite;
    public static Sprite shotSprite;

    public static void load() {
        playerSprite =
                new Sprite(new Texture(Gdx.files.internal("player.jpg")));
        enemySprite =
                new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));
        shotSprite = new Sprite(new Texture(Gdx.files.internal("shot.jpg")));

        BitmapFont robotoCondensedTitle = generateFont(100);
        BitmapFont robotoCondensedButton = generateFont(50);
        BitmapFont robotoCondensedDialogButton = generateFont(100);

        //Style
        Label.LabelStyle titleLabelStyle =
                new Label.LabelStyle(robotoCondensedTitle, Color.RED);

        Label.LabelStyle dialogLabelStyle = new Label.LabelStyle
                (robotoCondensedButton, Color.BLACK);

        TextButton.TextButtonStyle textButtonStyle =
                new TextButton.TextButtonStyle();
        textButtonStyle.font = robotoCondensedButton;
        textButtonStyle.fontColor = Color.BLACK;

        //Dialog
        Window.WindowStyle dialogStyle = new Window.WindowStyle
                (robotoCondensedTitle, Color.BLACK, /*new SpriteDrawable(enemySprite)*/ new BaseDrawable());

        TextButton.TextButtonStyle dialogButtonStyle = new TextButton
                .TextButtonStyle();
        dialogButtonStyle.font = robotoCondensedDialogButton;
        dialogButtonStyle.fontColor = Color.BLACK;

        //skin
        skin = new Skin();
        skin.add("robotoCondensedTitle", robotoCondensedTitle);
        skin.add("robotoCondensedButton", robotoCondensedButton);
        skin.add("textButton", textButtonStyle,
                TextButton.TextButtonStyle.class);
        skin.add("titleLabel", titleLabelStyle, Label.LabelStyle.class);
        skin.add("dialog", dialogStyle, Window.WindowStyle.class);
        skin.add("dialogLabel", dialogLabelStyle, Label.LabelStyle.class);
        skin.add("dialogButton", dialogButtonStyle, TextButton.TextButtonStyle.class);
    }

    private static BitmapFont generateFont(int size) {
        FreeTypeFontGenerator generator = null;
        try {
            generator = new FreeTypeFontGenerator(
                    Gdx.files.internal("fonts/RobotoCondensedRegular.ttf"));
            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            parameter.size = size;

            return generator.generateFont(parameter);
        } finally {
            if (generator != null)
                generator
                        .dispose(); // don't forget to dispose to avoid memory leaks!
        }
    }
}
