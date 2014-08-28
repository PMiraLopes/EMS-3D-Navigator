package com.ems3DNavigator.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ems3DNavigator.manager.BuildingManager;

/**
 * The Class Lights, turns on/off the light of each lamp.
 */
public class Lights
        extends ImageButton {

    /**
     * The manager.
     */
    private BuildingManager manager;

    /**
     * The Constructor.
     *
     * @param ImageButtonStyle style
     * @param BuildingManager manager
     */
    public Lights(ImageButtonStyle style, BuildingManager manager) {
        super(style);

        this.manager = manager;

        addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                turnLights();
                return true;
            }
        });
    }

    /**
     * Enables or disables the transparent view.
     */
    private void turnLights() {
        this.manager.turnLights();
    }

}
