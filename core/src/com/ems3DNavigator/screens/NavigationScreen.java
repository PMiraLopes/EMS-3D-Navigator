package com.ems3DNavigator.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.ems3DNavigator.app.Ems3DNavigator;
import com.ems3DNavigator.constants.APP;
import com.ems3DNavigator.manager.BuildingManager;
import com.ems3DNavigator.manager.InterfaceManager;


/**
 * This class is the main interface of the system, is where the model will be presented to
 * the user, and where the user can interact with the model, choosing different kinds of
 * views of the system and inspected the information of the consumptions of the building,
 * each room or group of rooms.
 * 
 * @author PedroLopes
 */
public class NavigationScreen
        extends ScreenAdapter {

    private Stage stage;

    private final Ems3DNavigator application;
    private Array<ModelInstance> modelInstances = new Array<ModelInstance>();
    private boolean loading;
    private Environment env;
    private ModelInstance pointer;
    private InterfaceManager interfaceManager;
    private InputMultiplexer multiplexer;
    private ModelBuilder modelBuilder;

    public NavigationScreen(Ems3DNavigator app) {

        application = app;

        env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1.0f));
        env.add(new DirectionalLight().set(0.4f, 0.4f, 0.4f, -1.0f, -1.0f, -1.0f));

        stage = new Stage();

        loading = true;

    }

    /**
     * This function is used only one time when the system starts, to load the model to
     * memory, create a {@link BuildingManager} to manage the rooms and it's information,
     * and in the end sets the view to the user. In the end of the function (setHUD) it
     * calls a function to display the different buttons so that the user can interact
     * with the system
     */
    public void doneLoading() {

        modelInstances.add(new ModelInstance(application.getAssetManager()
                .get(APP.BUILDINGS + APP.MODEL + APP.MODEL_EXTENSION, Model.class)));

        modelBuilder = new ModelBuilder();
        Model cone =
            modelBuilder.createCone(2, 2, 2, 100,
                                    new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                                    Usage.Position | Usage.Normal);

        pointer = new ModelInstance(cone);
        pointer.transform.rotate(Vector3.Z, 180);

        modelInstances.add(pointer);
       //modelInstances.add(createFloor());
      // modelInstances.add(createSkyBox());

        application.createBuildingManager(modelInstances.first());

        interfaceManager = new InterfaceManager(this, application.getBuildingManager());
        interfaceManager.setHUD();

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(application.getCameraInputController());
        Gdx.input.setInputProcessor(multiplexer);

        loading = false;

    }

    /**
     * Function to render the model and the HUD.
     *
     * @param delta the delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0.0f, 0.7f, 1f, 0.2f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        application.updateCamera();

        if (loading && application.getAssetManager().update())
            doneLoading();

        application.getModelBatch().begin(application.getPerspectiveCamera());
        application.getModelBatch().render(modelInstances, env);
        application.getModelBatch().end();

        if (!loading) {
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
            application.updateCamera();
        }

    }

    public ModelInstance getPointer() {
        return pointer;
    }

    public Stage getStage() {
        return stage;
    }

    public Environment getEnvironment() {
        return env;
    }

    private ModelInstance createFloor() {
        Texture texture = new Texture(Gdx.files.internal(APP.BUTTONS + "grass.jpg"));
        Material m = new Material(new TextureAttribute(TextureAttribute.Diffuse, texture));
        Model rec =
            modelBuilder.createBox(256, 1, 256, m, Usage.Position | Usage.Normal
                    | Usage.TextureCoordinates);
        ModelInstance floor = new ModelInstance(rec);
       // floor.transform.setToRotation(0, 1, 0, 15);
        floor.transform.setTranslation(new Vector3(0,-2,0));

        return floor;
    }
    
    private ModelInstance createSkyBox() {
        Texture texture = new Texture(Gdx.files.internal(APP.BUTTONS + "skybox.jpg"));
        Material m = new Material(new TextureAttribute(TextureAttribute.Diffuse, texture));
        Model rec =
            modelBuilder.createSphere(300, 300, 300, 150, 150, m,  Usage.Position | Usage.Normal
                                      | Usage.TextureCoordinates);
        ModelInstance sky = new ModelInstance(rec);
        sky.materials.get(0).set(new IntAttribute(IntAttribute.CullFace, 0));


        return sky;
    }

}
