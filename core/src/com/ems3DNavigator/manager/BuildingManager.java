package com.ems3DNavigator.manager;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.ems3DNavigator.constants.APP;

/**
 * This class is used to manage the data that is used for interact with the system
 * application. It is used to group floor, lamp and hvac information in a class room which
 * represents and manages the information of the real room
 * 
 * @author PedroLopes
 */
public class BuildingManager {

    private Hashtable<String, Room> buildingRooms;
    private ModelInstance model;
    private Ems3DNavigator app;
    private Room lastRoomSelected;
    private Material selectionMaterial;
    private FileHandle fileHandle;
    private boolean transparencyEnabled = false;

    public BuildingManager(Ems3DNavigator app, ModelInstance model) {
        this.app = app;
        this.model = model;

        buildingRooms = new Hashtable<String, Room>();

        fileHandle = Gdx.files.internal(APP.BUILDINGS + "Taguspark-2.52.ifc");

        createRooms();

        lastRoomSelected = null;

        selectionMaterial = new Material(ColorAttribute.createDiffuse(Color.MAROON));
    }


    /**
     * Function used to initialize the hastable buildingRooms, with the data of the model,
     * creating the rooms with hvac model, lamp model, and floor model
     */
    private void createRooms() {
        String[] split, splitIfc, splitIfcAux;
        String floorId = "", lampId = "", hvacId = "", ifcFile = fileHandle.readString(), nodeId, roomId;
        boolean find = true;

        System.out.println("Loading rooms data...");

        for (Node n : model.nodes) {

            if (n.id.contains("Floor:ID")) {
                split = n.id.split("#");
                floorId = split[1];

                if (floorId.equals("2-2-6")) //SOLVE THIS
                    floorId = "2-2.6";

                if (floorId.equals("2-N16.4")) {// SOLVE THIS
                    if (find) {
                        floorId = "2-N16.0";
                        find = false;
                    }
                }

                if (!floorId.contains(".0")) // to avoid create wrong rooms, because of the group nodes
                    buildingRooms.put(floorId, new Room(n, floorId));
            }
        }

        for (Node k : model.nodes) {
            if (k.id.contains("lamp")) {
                split = k.id.split("#");
                lampId = split[1];
                if (buildingRooms.containsKey(lampId)) {
                    buildingRooms.get(lampId).setLampNode(k);
                    buildingRooms.get(lampId).setPositionVector(k.translation);
                }
            } else {
                if (k.id.contains("hvac")) {
                    split = k.id.split("#");
                    hvacId = split[1];
                    if (buildingRooms.containsKey(hvacId))
                        buildingRooms.get(hvacId).setHvacNode(k);
                    ;
                }
            }
        }

        splitIfc = ifcFile.split("\\r?\\n");

        for (int i = 0; i < splitIfc.length; i++) {

            if (splitIfc[i].contains("IFCSPACE")) {
                splitIfcAux = splitIfc[i].split("'");
                nodeId = splitIfcAux[1];
                roomId = splitIfcAux[5].replace(",", "."); // there is a bug with room 2-N11.7 and box 3fzykY5oTFQ8FBXD78V$TZ 

                if (model.getNode(nodeId) != null && buildingRooms.get(roomId) != null)
                    buildingRooms.get(roomId).setBoxNode(model.getNode(nodeId));
            }
        }

        System.out.println("Rooms loaded...");
    }

    /**
     * Function to retrieve a room by it's own id, if not exits returns null
     * 
     * @param roomID
     * @return the room
     */
    public Room getRoom(String roomID) {
        return buildingRooms.get(roomID);
    }

    /**
     * Prints the id of all rooms of the model
     */
    public void printRooms() {
        for (Room r : buildingRooms.values()) {
            System.out.println(r.getId());
        }
    }


    /**
     * Function to show to the user the room that has been searched in the text box
     * 
     * @param user text input
     * @return
     */
    public boolean showRoom(String text) {
        Room r = buildingRooms.get(text);

        if (r == null)
            return false; //Maybe thrown an exception

        float x = r.getX();
        float y = r.getY();
        float z = r.getZ();

        r.selectRoom(selectionMaterial);

        for (Room k : buildingRooms.values()) {
            if (!k.getId().equals(text)) {
                if (k.getBoxNode() != null) {
                    k.getBoxNode().parts.get(0).material.set(new BlendingAttribute(
                            GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 0.5f));
                }
                if (k.getFloorNode() != null) {
                    k.getFloorNode().parts.get(0).material.set(new BlendingAttribute(
                            GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 0.5f));
                }
                if (k.getHvacNode() != null) {
                    k.getHvacNode().parts.get(0).material.set(new BlendingAttribute(
                            GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 0.5f));
                }
                if (k.getLampNode() != null) {
                    k.getLampNode().parts.get(0).material.set(new BlendingAttribute(
                            GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 0.5f));
                }
            }
        }

        app.getPerspectiveCamera().position.set(x, y + 30, z);
        app.getPerspectiveCamera().lookAt(r.getPositionVector());
        app.getPerspectiveCamera().near = 1.0f;
        app.getPerspectiveCamera().far = 300.0f;
        app.getPerspectiveCamera().update();

        if (lastRoomSelected == null || lastRoomSelected.equals(r))
            lastRoomSelected = r;
        else {
            lastRoomSelected.deselectRoom();
            lastRoomSelected = r;
        }

        return true;
    }


    /**
     * Function to change the type of view over the model, in this case it shows only the
     * floor of each room
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
     * meshes unnecessary to this kind of view
     */
    public void setBoxesView() {
        hideEverything();
        for (Room r : buildingRooms.values()) {
            if (r.getBoxNode() != null)
                r.getBoxNode().parts.get(0).enabled = true;
            else
                System.out.println(r.getId());
        }

        /*setOverView();
        for (Node n : model.nodes) {
            if (n.id.contains("lamp") || n.id.contains("hvac")
                    || n.id.contains("Basic Wall") || n.id.contains("Painel do sistema")
                    || n.id.contains("M_Concreto-Redondo-Coluna")
                    || n.id.contains("Compound Ceiling:Plano"))
                n.parts.get(0).enabled = false;
            else {
                if(n.id.contains("1uFc0789j8FgFBNv8rClfI"))
                    n.parts.get(0).enabled = false;
                else
                    n.parts.get(0).enabled = true;
            }
        }*/
    }

    /**
     * Function to change the view of the system, giving a perspective overview of the
     * model with all meshes
     */
    public void setOverView() {
        for (Node n : model.nodes) {
          n.parts.get(0).enabled = true;
        }
    }

    /**
     * Function to set the default view of the model
     */
    public void setNormalView() {
        app.resetCamera();
        setOverView();
        for(Room r :  buildingRooms.values()){
            if(r.getBoxNode() != null)
            r.getBoxNode().parts.get(0).enabled = false;
        }
    }

    private void hideEverything() {
        for (Node n : model.nodes)
            n.parts.get(0).enabled = false;
    }

    public void setTransparentView() {
        if (!transparencyEnabled) {
            for (Material m : model.materials)
                m.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 0.5f));
            transparencyEnabled = true;
        } else {
            
            for (Material m : model.materials)
                m.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 1f));
            transparencyEnabled = false;
        }
    }
}
