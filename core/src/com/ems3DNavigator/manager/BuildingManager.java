package com.ems3DNavigator.manager;

import ifc2x3javatoolbox.ifc2x3tc1.IfcBuildingElementProxy;
import ifc2x3javatoolbox.ifc2x3tc1.IfcFlowTerminal;
import ifc2x3javatoolbox.ifc2x3tc1.IfcRelSpaceBoundary;
import ifc2x3javatoolbox.ifc2x3tc1.IfcSpace;
import ifc2x3javatoolbox.ifcmodel.IfcModel;

import java.io.File;
import java.util.Collection;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.ems3DNavigator.app.Ems3DNavigator;
import com.ems3DNavigator.buildingData.Room;
import com.ems3DNavigator.constants.APP;

/**
 * This class is used to manage the data that is used for interact with the system
 * application. It is used to group floor, lamp and hvac information in a class room which
 * represents and manages the information of the real room
 * 
 * @author PedroLopes
 */
public class BuildingManager {
    private TreeMap<String, Room> buildingRooms;
    private ModelInstance model;
    private Ems3DNavigator app;
    private boolean transparencyEnabled = false, lightsOn = false, heatOn = false, coldOn = false;
    private IfcModel ifcModel;

    public BuildingManager(Ems3DNavigator app, ModelInstance model) {
        this.app = app;
        this.model = model;

        buildingRooms = new TreeMap<String, Room>();

        File ifcFile = new File(APP.BUILDINGS + APP.IFC_FILE_NAME);
        ifcModel = new IfcModel();

        try {
            ifcModel.readStepFile(ifcFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        createRooms();
    }


    /**
     * Function used to initialize the hastable buildingRooms, with the data of the model,
     * creating the rooms with hvac model, lamp model, and floor model.
     */
    private void createRooms() {
        String[] split;
        String roomId = "", relatedId = "";
        Collection<IfcRelSpaceBoundary> associations;
        Array<Node> walls;
        Node floorNode = null;
        Room newRoom;

        System.err.println("Loading rooms data...");

        for (IfcSpace space : ifcModel.getCollection(IfcSpace.class)) {
            walls = new Array<Node>();
            BoundingBox boundingBox = new BoundingBox();
            associations = space.getBoundedBy_Inverse();

            for (IfcRelSpaceBoundary e : associations) {
                if (e.getRelatedBuildingElement() != null) {
                    relatedId = e.getRelatedBuildingElement().getName().toString();

                    if (relatedId.contains("Floor")) { //is a generic floor, not specific to a room.
                        floorNode = model.getNode(relatedId);
                        floorNode.calculateBoundingBox(boundingBox);

                        if (relatedId.contains("Floor:ID"))
                            // this floor is specific to a room, so represents the room id.
                            roomId = relatedId.split("#")[1];
                        else
                            roomId = relatedId;
                    } else if (relatedId.contains("Wall")){
                        if(model.getNode(relatedId) != null)   
                            walls.add(model.getNode(relatedId));
                    }
                }
            }

            newRoom = new Room(floorNode, roomId, boundingBox.getCenter());
            newRoom.setBoxNode(model.getNode(space.getGlobalId().toString()));
            newRoom.setWalls(walls);
            buildingRooms.put(roomId, newRoom);
        }

        for (IfcBuildingElementProxy element : ifcModel
                .getCollection(IfcBuildingElementProxy.class)) {
            split = element.getName().toString().split("#");
            if (buildingRooms.containsKey(split[1]))
                buildingRooms.get(split[1]).setHvacNode(model.getNode(element.getName()
                                                                .getDecodedValue()));
        }

        for (IfcFlowTerminal element : ifcModel.getCollection(IfcFlowTerminal.class)) {
            split = element.getName().toString().split("#");
            if (buildingRooms.containsKey(split[1]))
                buildingRooms.get(split[1]).setLampNode(model.getNode(element.getName()
                                                                .getDecodedValue()));
        }

        System.err.println("Rooms loaded...");
        setNormalView();
    }

    /**
     * Function to retrieve a room by it's own id, if not exits returns null.
     *
     * @param roomID the room id
     * @return the room
     */
    public Room getRoom(String roomID) {
        return buildingRooms.get(roomID);
    }

    /**
     * Prints the id of all rooms of the model.
     */
    public void printRooms() {
        for (Room r : buildingRooms.values()) 
            System.err.println(r.getId());
    }


    /**
     * Function to show to the user the room that has been searched in the text box.
     *
     * @param text the text
     * @return {@link Boolean}
     */
    public boolean showRoom(String text) {
        Room r = buildingRooms.get(text);

        if (r == null)
            return false; //Maybe thrown an exception

        Vector3 roomPos = r.getPosition();

      /*  for (Room otherRoom : buildingRooms.values())
            otherRoom.hideWalls();
        
        r.showWalls();*/
        
        app.getNavigationScreen().getPointer().transform.setTranslation(roomPos.x, roomPos.y + 5,
                                                                        roomPos.z);
        app.getNavigationScreen().getPointer().nodes.get(0).parts.get(0).enabled = true;

        app.setCameraPos(roomPos);

        return true;
    }


    /**
     * Function to change the type of view over the model, in this case it shows only the
     * floor of each room.
     */
    public void setFloorView() {
        setOverView();

        for (Node n : model.nodes) {
            if (n.id.contains("Floor") || n.id.contains("hvac") || n.id.contains("lamp")
                    || n.id.contains("Railing"))
                n.parts.get(0).enabled = true;
            else
                n.parts.get(0).enabled = false;
        }
    }

    /**
     * Changes the view of the system, to a view where each room is a box, hiding all the
     * meshes unnecessary to this kind of view.
     */
    public void setBoxesView() {
        hideEverything();
        for (Room r : buildingRooms.values()) {
            if (r.getBoxNode() != null)
                r.getBoxNode().parts.get(0).enabled = true;
            if (r.getLampNode() != null)
                r.getLampNode().parts.get(0).enabled = true;
            if (r.getHvacNode() != null)
                r.getHvacNode().parts.get(0).enabled = true;
        }
    }

    /**
     * Function to change the view of the system, giving a perspective overview of the
     * model with all meshes.
     */
    public void setOverView() {
        for (Node n : model.nodes) {
            if (n.id.contains("Compound Celling"))
                n.parts.get(0).enabled = false;
            else
                n.parts.get(0).enabled = true;
        }
    }

    /**
     * Function to set the default view of the model.
     */
    public void setNormalView() {
        app.resetCamera();
        hideEverything();
        for (Node n : model.nodes) {
            if (n.id.contains("Basic Wall") || n.id.contains("Railing") || n.id.contains("hvac")
                    || n.id.contains("lamp") || n.id.contains("Coluna") || n.id.contains("Floor")
                    || n.id.contains("Painel do sistema"))
                n.parts.get(0).enabled = true;
        }
    }

    private void hideEverything() {
        for (Node n : model.nodes) {
            if (n.id.contains("Floor") || n.id.contains("Railing"))
                n.parts.get(0).enabled = true;
            else
                n.parts.get(0).enabled = false;
        }
    }

    /**
     * Sets the transparent view over all buildings.
     */
    public void setTransparentView() {
        if (!transparencyEnabled) {
            for (Node n : model.nodes)
                if (!(n.id.contains("hvac") || n.id.contains("lamp")))
                    n.parts.get(0).material.set(new BlendingAttribute(GL20.GL_SRC_ALPHA,
                            GL20.GL_ONE_MINUS_SRC_ALPHA, 0.4f));
            transparencyEnabled = true;
        } else {

            for (Material m : model.materials)
                m.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 1f));
            transparencyEnabled = false;
        }
    }

    /**
     * Turn lights on/off.
     */
    public void turnLights() {
        if (!lightsOn) {
            for (Room r : buildingRooms.values()) {
                if (r.getLampNode() != null)
                    app.getNavigationScreen().getEnvironment() // The lamps have light
                            .add(r.getRoomLight());
            }
            lightsOn = true;
        } else {
            for (Room r : buildingRooms.values()) {
                if (r.getLampNode() != null)
                    app.getNavigationScreen().getEnvironment() // The lamps have light
                            .remove(r.getRoomLight());
            }
            lightsOn = false;
        }
    }

    /**
     * Turn heat on/off.
     */
    public void turnHeat() {
        if (!heatOn) {
            for (Room r : buildingRooms.values()) {
                if (r.getHvacNode() != null)
                    r.getHvacNode().parts.get(0).material =
                        new Material(ColorAttribute.createDiffuse(Color.RED));
            }
            heatOn = true;
        } else {
            for (Room r : buildingRooms.values()) {
                if (r.getHvacNode() != null)
                    r.getHvacNode().parts.get(0).material =
                        new Material(ColorAttribute.createDiffuse(Color.GRAY));
            }
            heatOn = false;
        }
    }

    /**
     * Turn cold. on/off
     */
    public void turnCold() {
        if (!coldOn) {
            for (Room r : buildingRooms.values()) {
                if (r.getHvacNode() != null)
                    r.getHvacNode().parts.get(0).material =
                        new Material(ColorAttribute.createDiffuse(Color.BLUE));
            }
            coldOn = true;
        } else {
            for (Room r : buildingRooms.values()) {
                if (r.getHvacNode() != null)
                    r.getHvacNode().parts.get(0).material =
                        new Material(ColorAttribute.createDiffuse(Color.GRAY));
            }
            coldOn = false;
        }
    }
    
    public Collection<Room> getRooms(){
        return buildingRooms.values();
    }
}
