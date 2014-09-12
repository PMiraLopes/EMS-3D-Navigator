package com.ems3DNavigator.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ems3DNavigator.manager.BuildingManager;

/**
 * The Class Heat is used to show that the hvac are used to produce serious resultus.
 */
public class Heat
        extends ImageButton {

    /**
     * The manager.
     */
    private BuildingManager manager;

    /**
     * The Constructor.
     *
     * @param style the style
     * @param manager the manager
     */
    public Heat(ImageButtonStyle style, BuildingManager manager) {
        super(style);

        this.manager = manager;

        addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                turn();
                return true;
            }
        });
    }

    /**
     * Enables or disables the the heat representation.
     */
    private void turn() {
        this.manager.turnHeat();
    }

}
