package com.ems3DNavigator.buttons;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;

/**
 * Button to show the user the different options buttons.
 * @author PedroLopes
 *
 */
public class Options
        extends ImageButton {

    private boolean active = false;
    private Array<Actor> buttons;

    public Options(ImageButtonStyle style, Array<Actor> actors) {
        super(style);
        buttons = actors;

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                showOptions();
                return true;
            }
        });
    }

    public void showOptions() {
        if (!active) {
            for (Actor button : buttons) {
                button.setVisible(true);
                button.setTouchable(Touchable.enabled);
            }
        } else {
            for (Actor button : buttons) {
                button.setVisible(false);
                button.setTouchable(Touchable.disabled);
            }
        }
        active = !active;
    }

}
