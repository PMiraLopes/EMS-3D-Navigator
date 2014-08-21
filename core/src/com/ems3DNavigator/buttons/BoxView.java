package com.ems3DNavigator.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ems3DNavigator.manager.BuildingManager;

public class BoxView extends ImageButton{
    private BuildingManager manager;
    
    public BoxView(ImageButtonStyle style, BuildingManager manager){
        super(style);
        
        this.manager = manager;

        addListener(new ClickListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                boxView();
                return true;
            }
        });
    }
    
    private void boxView(){
        this.manager.setBoxesView();
    }
     
}
