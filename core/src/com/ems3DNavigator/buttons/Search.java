package com.ems3DNavigator.buttons;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.ems3DNavigator.manager.BuildingManager;

public class Search
        extends ImageButton {

    private BuildingManager manager;
    private Actor textBox;
    private boolean active = false;

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
