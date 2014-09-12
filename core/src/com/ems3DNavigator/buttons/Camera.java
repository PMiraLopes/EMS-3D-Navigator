package com.ems3DNavigator.buttons;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;

/**
 * Button to allow the user to see the different actions that the camera can make.
 * 
 * @author PedroLopes
 */
public class Camera
        extends ImageButton {

    private Array<Actor> buttons;
    private boolean active = false;

    public Camera(ImageButtonStyle style, Array<Actor> actors) {
        super(style);
        buttons = actors;
        
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                showCameraOptions();
                return true;
            }
        });
    }
    
    public void showCameraOptions(){
        active = !active;
    }


}
