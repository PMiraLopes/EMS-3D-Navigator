package com.ems3DNavigator.manager;

import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * This class is used to store each room information, the nodes in the model, material,
 * id, position and the information related to the energy consumption of the room.
 * 
 * @author PedroLopes
 */

public class Room {
    private Material originalFloorMaterial;
    private Material originalBoxMaterial;
    private Node floorNode;
    private Node lampNode;
    private Node hvacNode;
    private Node boxNode;
    private String id;
    private Vector3 position;
    private float lightConsumption;
    private Array<Node> walls = new Array<Node>();

    public Room(Node floorNode, String id, Vector3 position) {
        this.floorNode = floorNode;
        this.id = id;
        this.position = position;
        originalFloorMaterial = floorNode.parts.get(0).material;
    }

    public Room(Node floorNode, String id) {
        this.floorNode = floorNode;
        this.id = id;
        originalFloorMaterial = floorNode.parts.get(0).material;
    }

    /**
     * Returns the Floor {@link Node} of the room.
     * 
     * @return {@link Node}
     */
    public Node getFloorNode() {
        return floorNode;
    }

    /**
     * Returns the Box {@link Node} of the room.
     * 
     * @return {@link Node}
     */
    public Node getBoxNode() {
        return boxNode;
    }

    /**
     * Returns the {@link Room} own Id.
     * 
     * @return {@link String}
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the Floor {@link Material} of the {@link Room}.
     * 
     * @return {@link Material}
     */
    public Material getFloorMaterial() {
        return floorNode.parts.get(0).material;
    }

    /**
     * Returns the {@link MeshPart} of the Floor node of the {@link Room}.
     * 
     * @return {@link MeshPart}
     */
    public MeshPart getFloorMesh() {
        return floorNode.parts.get(0).meshPart;
    }

    /**
     * Returns the x coordinate of the {@link Room} position.
     * 
     * @return {@link Float}
     */
    public float getX() {
        return position.x;
    }

    /**
     * Returns the y coordinate of the {@link Room} position.
     * 
     * @return {@link Float}
     */
    public float getY() {
        return position.y;
    }

    /**
     * Returns the z coordinate of the {@link Room} position.
     * 
     * @return {@link Float}
     */
    public float getZ() {
        return position.z;
    }

    /**
     * Sets the {@link Room} position to a given position.
     * 
     * @param {@link Vector3} position
     */
    public void setPositionVector(Vector3 position) {
        this.position = position;
    }

    /**
     * Returns the {@link Room}'s position.
     * 
     * @return {@link Vector3}
     */
    public Vector3 getPositionVector() {
        return position;
    }

    /**
     * Sets the {@link Room}'s lamp {@link Node} to a given node.
     * 
     * @param {@link Node} lamp
     */
    public void setLampNode(Node lamp) {
        this.lampNode = lamp;
        position = lamp.translation;
    }

    public Node getLampNode() {
        return lampNode;
    }

    /**
     * Sets the {@link Room} Hvac {@link Node}.
     * 
     * @param hvac Node
     */
    public void setHvacNode(Node hvac) {
        this.hvacNode = hvac;
    }

    /**
     * Sets the {@link Room} Box {@link Node}.
     * 
     * @param box Node
     */
    public void setBoxNode(Node box) {
        this.boxNode = box;
        originalBoxMaterial = box.parts.get(0).material;
    }

    public Node getHvacNode() {
        return hvacNode;
    }

    public void setFloorMaterial(Material m) {
        floorNode.parts.get(0).material = m;
    }

    public void setBoxMaterial(Material m) {
        boxNode.parts.get(0).material = m;
    }

    public void selectRoom(Material m) {
        setFloorMaterial(m);
        setBoxMaterial(m);
    }

    public void deselectRoom() {
        setFloorMaterial(originalFloorMaterial);
        setBoxMaterial(originalBoxMaterial);
    }
    
    public void addWall(Node n){
        walls.add(n);
    }
    
    public Array<Node> getWalls(){
        return walls;
    }
}
