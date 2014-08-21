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
     * The manager.
     */
    private BuildingManager manager;
    
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
     * @param  ImageButtonStyle style
     * @param BuildingManagermanager
     * @param Actor actor
     */
    public Search(ImageButtonStyle style, BuildingManager manager, Actor actor) {
        super(style);

        this.manager = manager;
        this.textBox = actor;

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                showTextBox();
                return true;
            }
        });

    }

    /**
     * Sets the text box as visible or invisible, and allows the user to insert input with the keyboard.
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
