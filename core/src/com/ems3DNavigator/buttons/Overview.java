package com.ems3DNavigator.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ems3DNavigator.manager.BuildingManager;

/**
 * The Class Overview that show the user the building itself, with all floors. 
 */
public class Overview extends ImageButton{
    
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
    public Overview(ImageButtonStyle style, BuildingManager manager){
        super(style);
        
        this.manager = manager;

        addListener(new ClickListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                overview();
                return true;
            }
        });
    }
    
    /**
     * Overview.
     */
    private void overview(){
        this.manager.setOverView();
    }
     
}
