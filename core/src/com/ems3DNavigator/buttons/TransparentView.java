package com.ems3DNavigator.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ems3DNavigator.manager.BuildingManager;

/**
 * The Class TransparentView, sets all materials of the system as transparent, allowing
 * the user to see through the objects.
 */
public class TransparentView
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
    public TransparentView(ImageButtonStyle style, BuildingManager manager) {
        super(style);

        this.manager = manager;

        addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                transparentView();
                return true;
            }
        });
    }

    /**
     * Enables or disables the transparent view.
     */
    private void transparentView() {
        this.manager.setTransparentView();
    }

}
