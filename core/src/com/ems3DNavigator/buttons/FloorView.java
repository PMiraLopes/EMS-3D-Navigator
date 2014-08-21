package com.ems3DNavigator.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ems3DNavigator.manager.BuildingManager;
import com.ems3DNavigator.manager.Room;

// TODO: Auto-generated Javadoc
/**
 * The Class FloorView that changes the view of the scene to one where it is only show the
 * floor, the hvac, and the lamp of the {@link Room} to each room.
 */
public class FloorView
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
    public FloorView(ImageButtonStyle style, BuildingManager manager) {
        super(style);

        this.manager = manager;

        addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                floorView();
                return true;
            }
        });
    }

    /**
     * Floor view.
     */
    private void floorView() {
        this.manager.setFloorView();
    }

}
