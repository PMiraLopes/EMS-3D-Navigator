package com.ems3DNavigator.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.ems3DNavigator.constants.APP;

public class PauseScreen extends ScreenAdapter {
    private final Ems3DNavigator application;
    private Stage stage;
    
    public class MyActor extends Actor{
        Texture texture;
        float actorX = Gdx.graphics.getWidth()/2, actorY = Gdx.graphics.getHeight()/2;
        boolean started = false;
        
        public MyActor(){
            
            texture = new Texture(Gdx.files.internal(APP.BUTTONS + "startButton.png"));
            
            setBounds(actorX, actorY, texture.getWidth(), texture.getWidth());
            
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    application.setCamera();
                    application.setScreen(application.getNavigationScreen());
                    return true;
                }
            });
        }
        
        @Override
        public void draw(Batch batch, float alpha){
            batch.draw(texture, this.getX(), this.getY());
        }
    }
    
    
    public PauseScreen(Ems3DNavigator app){
        stage = new Stage();
        //Gdx.input.setInputProcessor(stage);
        
        application = app;
        MyActor actor = new MyActor();
        actor.setTouchable(Touchable.enabled);
        stage.addActor(actor);
    }
    
    public void render(float delta){
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        
        stage.draw();
        
    }
    
    public void dispose(){
        stage.dispose();
    }

}
