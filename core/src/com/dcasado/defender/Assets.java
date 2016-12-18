package com.dcasado.defender;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
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
                new Sprite(new Texture(Gdx.files.internal("player.png")));
        enemySprite =
                new Sprite(new Texture(Gdx.files.internal("enemy.png")));
        shotSprite = new Sprite(new Texture(Gdx.files.internal("shot.jpg")));

        //Fonts
        BitmapFont titleFont = generateFont(60);
        BitmapFont buttonFont = generateFont(35);

        BitmapFont dialogTitleFont = generateFont(55);
        BitmapFont dialogButton = generateFont(50);

        //Styles
        Label.LabelStyle titleLabelStyle = new Label.LabelStyle(titleFont, Color.RED);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.fontColor = Color.BLACK;

        //Dialog
        Dialog.WindowStyle dialogStyle = new Dialog.WindowStyle(dialogTitleFont, Color.BLACK, new BaseDrawable());

        TextButton.TextButtonStyle dialogButtonStyle = new TextButton
                .TextButtonStyle();
        dialogButtonStyle.font = dialogButton;
        dialogButtonStyle.fontColor = Color.BLACK;

        //Skin
        skin = new Skin();
        skin.add("robotoCondensedTitle", titleFont);
        skin.add("button", buttonFont);
        skin.add("textButton", textButtonStyle, TextButton.TextButtonStyle.class);
        skin.add("titleLabel", titleLabelStyle, Label.LabelStyle.class);
        skin.add("dialog", dialogStyle, Dialog.WindowStyle.class);
        skin.add("dialogButton", dialogButtonStyle, TextButton.TextButtonStyle.class);
    }

    private static BitmapFont generateFont(int size) {
        FreeTypeFontGenerator generator = null;
        try {
            generator = new FreeTypeFontGenerator(
                    Gdx.files.internal("fonts/RobotoCondensedRegular.ttf"));
            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            int fontSize = Gdx.graphics.getWidth() * size / 1024;
            parameter.size = fontSize;

            return generator.generateFont(parameter);
        } finally {
            if (generator != null)
                generator.dispose(); // don't forget to dispose to avoid memory leaks!
        }
    }
}
