package com.ems3DNavigator.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.ems3DNavigator.buildingData.Room;
import com.ems3DNavigator.buttons.BoxView;
import com.ems3DNavigator.buttons.FloorView;
import com.ems3DNavigator.buttons.Home;
import com.ems3DNavigator.buttons.Lights;
import com.ems3DNavigator.buttons.Overview;
import com.ems3DNavigator.buttons.Search;
import com.ems3DNavigator.buttons.TransparentView;
import com.ems3DNavigator.buttons.ViewButton;
import com.ems3DNavigator.constants.APP;
import com.ems3DNavigator.screens.NavigationScreen;

/**
 * This class is used to manage the interface of the application, setting the widgets and
 * the information of the rooms.
 */
public class InterfaceManager {
    private BuildingManager manager;
    private NavigationScreen navScreen;
    private TextureRegion upTextureRegion, downTextureRegion;
    private TextureRegionDrawable upImage, downImage;
    private ImageButtonStyle style;
    private Table widgetsTable;
    private Table infoTable;
    private Skin uiSkin;

    public InterfaceManager(NavigationScreen navScreen, BuildingManager manager) {
        this.navScreen = navScreen;
        this.manager = manager;
        uiSkin = new Skin(Gdx.files.internal(APP.SKINS + "uiskin.json"));
    }

    /**
     * Sets the HUD to be displayed to the user, putting some buttons in a {@link Table},
     * that will be drawn by a 2D batch ( {@link SpriteBatch} ).
     */
    public void setHUD() {

        widgetsTable = new Table();
        infoTable = new Table();

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

        widgetsTable.setFillParent(false);
        widgetsTable.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        widgetsTable.top().left();

        infoTable.setFillParent(false);
        infoTable.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight());
        infoTable.top().right();
        infoTable.setPosition(3 * Gdx.graphics.getWidth() / 4, 0);


        widgetsTable.add(createSearchButton(textField));
        widgetsTable.add(textField);
        widgetsTable.row();
        widgetsTable.add(createViewButton(actors));
        widgetsTable.add(homeButton);
        widgetsTable.add(floorViewButton);
        widgetsTable.add(boxViewButton);
        widgetsTable.add(overviewButton);
        widgetsTable.row();
        widgetsTable.add(createTransparentButton());
        widgetsTable.row();
        widgetsTable.add(createLightsButton());
        widgetsTable.row();

        navScreen.getStage().addActor(infoTable);
        navScreen.getStage().addActor(widgetsTable);

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

        return new FloorView(style, manager);
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

        return new BoxView(style, manager);
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

        return new Overview(style, manager);
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

        return new Home(style, manager);
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

        return new TransparentView(style, manager);
    }


    /**
     * Creates a {@link TransparentView} type to be inserted in the table of HUD, to set
     * the default view of the system when pressed.
     * 
     * @return {@link TransparentView}
     */
    private Lights createLightsButton() {
        upTextureRegion = new TextureRegion(new Texture(Gdx.files.internal(APP.LIGHTS_BUTTON)));
        downTextureRegion =
            new TextureRegion(new Texture(Gdx.files.internal(APP.LIGHTS_SELECTED_BUTTON)));
        upImage = new TextureRegionDrawable(upTextureRegion);
        downImage = new TextureRegionDrawable(downTextureRegion);

        defineStyle();

        return new Lights(style, manager);
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
                    manager.showRoom(textField.getText());
                    if (manager.getRoom(textField.getText()) != null)
                        showInfo(manager.getRoom(textField.getText()));
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

    /**
     * Displays in the right side of the screen the information about the room.
     * 
     * @param the searched {@link Room}
     */
    public void showInfo(Room room) {
        clearInfo();

        //infoTable.debug();
        
        infoTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files
                .internal("buttons/Table.png")))));
        
        infoTable.add(new Label("Room ID: " + room.getId(), uiSkin)).colspan(3).center()
                .width(Gdx.graphics.getWidth() / 4);
        infoTable.row();

        infoTable.add(new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(
                Gdx.files.internal(APP.LAMP_BUTTON))))));
        infoTable.add(new Label("Light Consumption: ", uiSkin)).left();
        infoTable.add(new Label(String.valueOf(room.getLightConsumption()) + "W", uiSkin)).left();
        infoTable.row();

        infoTable.add(new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(
                Gdx.files.internal(APP.HVAC_BUTTON))))));
        infoTable.add(new Label("Hvac Consumption: ", uiSkin)).left();
        infoTable.add(new Label(String.valueOf(room.getHvacConsumption()) + "W", uiSkin)).left();
        infoTable.row();

        infoTable.add(new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(
                Gdx.files.internal(APP.ELECTRICITY_BUTTON))))));
        infoTable.add(new Label("Electric Consumption: ", uiSkin)).left();
        infoTable.add(new Label(String.valueOf(room.getElectricityComsumption()) + "W", uiSkin))
                .left();
        infoTable.row();

        infoTable.add(new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(
                Gdx.files.internal(APP.PERSON_BUTTON))))));
        infoTable.add(new Label("Ocupancy: ", uiSkin)).left();
        infoTable.add(new Label(String.valueOf(room.getOcupancy()), uiSkin)).left();
        infoTable.row();
    }

    /**
     * Clears the information displayed about some room.
     */
    public void clearInfo() {
        infoTable.clear();
    }
}
