package com.ems3DNavigator.buttons;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.utils.Array;

// TODO: Auto-generated Javadoc
/**
 * The Class ViewButton.
 */
public class ViewButton
        extends ImageButton {
    
    /**
     * The actors list.
     */
    private Array<Actor> actors;
    
    /**
     * The active flag.
     */
    private boolean active = false;

    /**
     * The Constructor.
     *
     * @param style the style
     * @param actors the actors
     */
    public ViewButton(ImageButtonStyle style, Array<Actor> actors) {
        super(style);

        this.actors = actors;

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
            for (Actor actor : actors) {
                actor.setTouchable(Touchable.enabled);
                actor.setVisible(true);
            }
            active = true;
        } else {

            for (Actor actor : actors) {
                actor.setTouchable(Touchable.disabled);
                actor.setVisible(false);
            }
            active = false;
        }
    }
}
