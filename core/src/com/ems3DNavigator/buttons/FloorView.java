package com.ems3DNavigator.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ems3DNavigator.manager.BuildingManager;

public class FloorView
        extends ImageButton {
    private BuildingManager manager;

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

    private void floorView() {
        this.manager.setFloorView();
    }

}
