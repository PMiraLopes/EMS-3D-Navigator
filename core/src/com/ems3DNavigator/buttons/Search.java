package com.ems3DNavigator.buttons;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.ems3DNavigator.manager.BuildingManager;

/**
 * The Class Search to allow the user to seach for a desired room.
 */
public class Search
        extends ImageButton {

    /**
     * The text box for user input.
     */
    private Actor textBox;

    /**
     * Flag to know if the text box is being displayed or not.
     */
    private boolean active = false;

    /**
     * The Constructor.
     *
     * @param {@link ImageButtonStyle}
     * @param {@link BuildingManager}BuildingManager
     * @param {@link Actor}
     */
    public Search(ImageButtonStyle style, Actor actor) {
        super(style);

        this.textBox = actor;

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                showTextBox();
                return true;
            }
        });

    }

    /**
     * Sets the text box as visible or invisible, and allows the user to insert input with
     * the keyboard.
     */
    public void showTextBox() {
        if (!active) {
            textBox.setTouchable(Touchable.enabled);
            textBox.setVisible(true);
            active = true;
        } else {
            textBox.setTouchable(Touchable.disabled);
            textBox.setVisible(false);
            active = false;
        }
    }
}
