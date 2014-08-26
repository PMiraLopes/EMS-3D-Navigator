package com.ems3DNavigator.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ems3DNavigator.manager.BuildingManager;

/**
 * The Class BoxView that changes the view of the scene to one where the rooms are boxes,
 * and all the system is shown as a set of boxes.
 */
public class BoxView
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
    public BoxView(ImageButtonStyle style, BuildingManager manager) {
        super(style);

        this.manager = manager;

        addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boxView();
                return true;
            }
        });
    }

    /**
     * Box view.
     */
    private void boxView() {
        this.manager.setBoxesView();
    }

}
