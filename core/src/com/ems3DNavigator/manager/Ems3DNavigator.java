package com.ems3DNavigator.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.ems3DNavigator.constants.APP;


/**
 * This is the base class of the program, it is used when the program starts setting the
 * values to the cameras, batch, and loading the models that gona be displayed to the user
 * 
 * @author PedroLopes
 */

public class Ems3DNavigator
        extends Game {

    private ModelBatch batch;
    private PerspectiveCamera camera3D;
    private AssetManager assets;
    private NavigationScreen navigationScreen;
    private CameraInputController cameraController;
    private BuildingManager buildingManager;


    /**
     * First function to be called when the program starts, this is a default behaviour of
     * libgdx
     */
    public void create() {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        batch = new ModelBatch();

        assets = new AssetManager();

        setCamera();

        loadModel("Tagus");

        navigationScreen = new NavigationScreen(this);
        // sets the first screen to be the navigation screen of the loaded model

        this.setScreen(navigationScreen);
    }

    /**
     * Function called when the program needs to perform rendering
     */
    public void render() {
        super.render();
    }

    /**
     * Last function to be called when the program finish, it is used to free the memory
     * allocated during the execution
     */
    public void dispose() {
        batch.dispose();
        assets.dispose();
        navigationScreen.dispose();
    }

    /**
     * Function to return the batch to 3D objects
     * 
     * @return {@link ModelBatch}
     */
    public ModelBatch getModelBatch() {
        return batch;
    }

    /**
     * Returns the default camera, that is set in the start of the program
     * 
     * @return {@link PerspectiveCamera}
     */
    public PerspectiveCamera getPerspectiveCamera() {
        return camera3D;
    }

    /**
     * Returns the assetManager that was created in the start of the program
     * 
     * @return {@link AssetManager}
     */
    public AssetManager getAssetManager() {
        return assets;
    }

    public void loadModel(String model) {
        assets.load(APP.BUILDINGS + model + APP.MODEL_EXTENSION, Model.class);
    }

    /**
     * Function called in the rendering time, to update the camera
     */
    public void updateCamera() {
        cameraController.update();
    }

    /**
     * Returns the NavigationScreen object
     * 
     * @return {@link NavigationScreen}
     */
    public NavigationScreen getNavigationScreen() {
        return navigationScreen;
    }

    /**
     * Returns the CameraInputController object
     * 
     * @return C
     */
    public CameraInputController getCameraInputController() {
        return cameraController;
    }

    /**
     * Function to the set values of the default perspective camera
     */
    public void setCamera() {
        camera3D = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera3D.position.set(100.0f, 50.0f, 50.0f);
        camera3D.lookAt(100.0f, 0.0f, 0.0f);
        camera3D.near = 1.0f;
        camera3D.far = 200.0f;
        camera3D.update();

        setCameraAsInputController();
    }

    public void resetCamera() {
        camera3D.position.set(100.0f, 50.0f, 50.0f);
        camera3D.lookAt(50.0f, 0.0f, 0.0f);
        camera3D.near = 1.0f;
        camera3D.far = 300.0f;
        camera3D.up.set(Vector3.Y);
        camera3D.update();
    }

    /**
     * Add the camera controller as input controller
     */
    public void setCameraAsInputController() {
        cameraController = new CameraInputController(camera3D);
        Gdx.input.setInputProcessor(cameraController);
    }


    public void createBuildingManager(ModelInstance modelInstance) {
        buildingManager = new BuildingManager(this, modelInstance);
        buildingManager.setNormalView();
    }

    public BuildingManager getBuildingManager() {
        return buildingManager;
    }

}
