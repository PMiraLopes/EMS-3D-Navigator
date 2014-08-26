package com.ems3DNavigator.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.ems3DNavigator.buttons.BoxView;
import com.ems3DNavigator.buttons.FloorView;
import com.ems3DNavigator.buttons.Home;
import com.ems3DNavigator.buttons.Overview;
import com.ems3DNavigator.buttons.Search;
import com.ems3DNavigator.buttons.TransparentView;
import com.ems3DNavigator.buttons.ViewButton;
import com.ems3DNavigator.constants.APP;


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
    private InputMultiplexer multiplexer;

    private final Ems3DNavigator application;
    private Array<ModelInstance> modelInstances = new Array<ModelInstance>();
    private boolean loading;
    private Environment env;
    private TextureRegion upTextureRegion, downTextureRegion;
    private TextureRegionDrawable upImage, downImage;
    private ImageButtonStyle style;
    private ModelInstance pointer;

    public NavigationScreen(Ems3DNavigator app) {

        application = app;

        env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1.0f));
        env.add(new DirectionalLight().set(0.4f, 0.4f, 0.4f, -1.0f, -1.0f, -1.0f));

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
        
        ModelBuilder modelBuilder = new ModelBuilder();
        Model cone =
            modelBuilder.createCone(2, 2, 2, 100,
                                    new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                                    Usage.Position | Usage.Normal);
        pointer = new ModelInstance(cone);
        pointer.transform.rotate(Vector3.Z, 180);
        modelInstances.add(pointer);

        application.createBuildingManager(modelInstances.first());

        setHUD();

        loading = false;

    }

    /**
     * Function to render the model and the HUD.
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

    /**
     * Sets the HUD to be displayed to the user, putting some buttons in a {@link Table},
     * that will be drawn by a 2D batch ( {@link SpriteBatch} ).
     */
    private void setHUD() {


        stage = new Stage();
        Table table = new Table();

        Array<Actor> actors = new Array<Actor>();

        Home homeButton = createHomeButton();
        FloorView floorViewButton = createFloorViewButton();
        BoxView boxViewButton = createBoxViewButton();
        Overview overviewButton = createOverviewButton();
        TextField textField = createTextField();

        actors.add(homeButton);
        actors.add(floorViewButton);
        actors.add(boxViewButton);
        actors.add(overviewButton);

        table.setFillParent(true);

        table.top().left();

        table.add(createSearchButton(textField));
        table.add(textField);
        table.row();
        table.add(createViewButton(actors));
        table.add(homeButton).space(0);
        table.add(floorViewButton);
        table.add(boxViewButton);
        table.add(overviewButton);
        table.row();
        table.add(createTransparentButton());
        table.row();

        stage.addActor(table);


        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(application.getCameraInputController());
        Gdx.input.setInputProcessor(multiplexer);

    }

    /**
     * Creates a {@link Search} type to be inserted in the table of HUD, to enable the
     * search box a allow the user to search for some room or group.
     * 
     * @return {@link Search}
     */
    private Search createSearchButton(TextField textField) {
        upTextureRegion = new TextureRegion(new Texture(Gdx.files.internal(APP.SEARCH_BUTTON)));
        downTextureRegion =
            new TextureRegion(new Texture(Gdx.files.internal(APP.SEARCH_SELECTED_BUTTON)));
        upImage = new TextureRegionDrawable(upTextureRegion);
        downImage = new TextureRegionDrawable(downTextureRegion);

        defineStyle();

        return new Search(style, textField);
    }

    /**
     * Creates a {@link FloorView} type to be inserted in the table of HUD, to set a view
     * of only the floor of each room of the model.
     * 
     * @return {@link FloorView}
     */
    private FloorView createFloorViewButton() {
        upTextureRegion = new TextureRegion(new Texture(Gdx.files.internal(APP.FLOOR_BUTTON)));
        downTextureRegion =
            new TextureRegion(new Texture(Gdx.files.internal(APP.FLOOR_SELECTED_BUTTON)));
        upImage = new TextureRegionDrawable(upTextureRegion);
        downImage = new TextureRegionDrawable(downTextureRegion);

        defineStyle();

        return new FloorView(style, application.getBuildingManager());
    }

    /**
     * Creates a {@link BoxView} type to be inserted in the table of HUD, and change the
     * view to a box view of the system.
     * 
     * @return {@link BoxView}
     */
    private BoxView createBoxViewButton() {
        upTextureRegion = new TextureRegion(new Texture(Gdx.files.internal(APP.BOX_BUTTON)));
        downTextureRegion =
            new TextureRegion(new Texture(Gdx.files.internal(APP.BOX_SELECTED_BUTTON)));
        upImage = new TextureRegionDrawable(upTextureRegion);
        downImage = new TextureRegionDrawable(downTextureRegion);

        defineStyle();

        return new BoxView(style, application.getBuildingManager());
    }

    /**
     * Creates an {@link Overview} type to be inserted in the table of HUD and to change
     * the view to a perspective overview of the system.
     * 
     * @return {@link Overview}
     */
    private Overview createOverviewButton() {
        upTextureRegion = new TextureRegion(new Texture(Gdx.files.internal(APP.OVERVIEW_BUTTON)));
        downTextureRegion =
            new TextureRegion(new Texture(Gdx.files.internal(APP.OVERVIEW_SELECTED_BUTTON)));
        upImage = new TextureRegionDrawable(upTextureRegion);
        downImage = new TextureRegionDrawable(downTextureRegion);

        defineStyle();

        return new Overview(style, application.getBuildingManager());
    }


    /**
     * Creates a {@link Home} type to be inserted in the table of HUD, to set the default
     * view of the system when pressed.
     * 
     * @return {@link Home}
     */
    private Home createHomeButton() {
        upTextureRegion = new TextureRegion(new Texture(Gdx.files.internal(APP.HOME_BUTTON)));
        downTextureRegion =
            new TextureRegion(new Texture(Gdx.files.internal(APP.HOME_SLECTED_BUTTON)));
        upImage = new TextureRegionDrawable(upTextureRegion);
        downImage = new TextureRegionDrawable(downTextureRegion);

        defineStyle();

        return new Home(style, application.getBuildingManager());
    }

    /**
     * Creates a {@link TransparentView} type to be inserted in the table of HUD, to set
     * the default view of the system when pressed.
     * 
     * @return {@link TransparentView}
     */
    private TransparentView createTransparentButton() {
        upTextureRegion =
            new TextureRegion(new Texture(Gdx.files.internal(APP.TRANSPARENT_BUTTON)));
        downTextureRegion =
            new TextureRegion(new Texture(Gdx.files.internal(APP.TRANSPARENT_SELECTED_BUTTON)));
        upImage = new TextureRegionDrawable(upTextureRegion);
        downImage = new TextureRegionDrawable(downTextureRegion);

        defineStyle();

        return new TransparentView(style, application.getBuildingManager());
    }

    private void defineStyle() {
        style = new ImageButtonStyle(upImage, downImage, upImage, upImage, downImage, upImage);
        style.imageUp = upImage;
        style.imageDown = downImage;
        style.checked = downImage;
    }

    /**
     * Creates {@link TextField} with a listener, to check if something were written, and
     * to be displayed/hide when the user press the {@link Search}.
     * 
     * @return {@link TextField}
     */
    private TextField createTextField() {
        TextField textField =
            new TextField("Insert room", new Skin(Gdx.files.internal(APP.SKINS + "uiskin.json")));

        textField.setWidth(100);

        textField.setTextFieldListener(new TextFieldListener() {

            @Override
            public void keyTyped(TextField textField, char c) {
                if (c == '\n' || c == '\r') {
                    application.getBuildingManager().showRoom(textField.getText());
                    return;
                }
            }
        });

        textField.setVisible(false);
        textField.setTouchable(Touchable.disabled);

        return textField;
    }

    /**
     * Creates a {@link ViewButton} to show the different views that the system supports.
     * 
     * @return {@link Search}
     */
    private ViewButton createViewButton(Array<Actor> actors) {
        upTextureRegion = new TextureRegion(new Texture(Gdx.files.internal(APP.VIEW_BUTTON)));
        downTextureRegion =
            new TextureRegion(new Texture(Gdx.files.internal(APP.VIEW_SELECTED_BUTTON)));
        upImage = new TextureRegionDrawable(upTextureRegion);
        downImage = new TextureRegionDrawable(downTextureRegion);

        defineStyle();
        for (Actor actor : actors) {
            actor.setVisible(false);
            actor.setTouchable(Touchable.disabled);
        }

        return new ViewButton(style, actors);
    }
    
    public ModelInstance getPointer(){
        return pointer;
    }


}
