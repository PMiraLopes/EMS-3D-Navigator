package com.ems3DNavigator.buildingData;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
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

    private String id;
    private Vector3 position;

    private Material originalFloorMaterial;
    private Material originalBoxMaterial;

    private Node floorNode;
    private Node lampNode;
    private Node hvacNode;
    private Node boxNode;
    private Array<Node> walls;

    private float lightConsumption = 10;
    private float roomOcupancy = 0;
    private float hvacConsumption = 10;
    private float electricityComsumption = 10;
    private float area;
    private float volume;
    private float perimeter;
    private DirectionalLight roomLight;

    public Room(Node floorNode, String id, Vector3 position) {
        this.floorNode = floorNode;
        this.id = id;
        this.position = position;
        originalFloorMaterial = floorNode.parts.get(0).material;
        this.walls = new Array<Node>();
    }

    public Room(Node floorNode, String id) {
        this.floorNode = floorNode;
        this.id = id;
        originalFloorMaterial = floorNode.parts.get(0).material;
        this.walls = new Array<Node>();
    }

    /**
     * @return the position
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * @return the originalFloorMaterial
     */
    public Material getOriginalFloorMaterial() {
        return originalFloorMaterial;
    }

    /**
     * @param originalFloorMaterial the originalFloorMaterial to set
     */
    public void setOriginalFloorMaterial(Material originalFloorMaterial) {
        this.originalFloorMaterial = originalFloorMaterial;
    }

    /**
     * @return the originalBoxMaterial
     */
    public Material getOriginalBoxMaterial() {
        return originalBoxMaterial;
    }

    /**
     * @param originalBoxMaterial the originalBoxMaterial to set
     */
    public void setOriginalBoxMaterial(Material originalBoxMaterial) {
        this.originalBoxMaterial = originalBoxMaterial;
    }

    /**
     * @return the floorNode
     */
    public Node getFloorNode() {
        return floorNode;
    }

    /**
     * @param floorNode the floorNode to set
     */
    public void setFloorNode(Node floorNode) {
        this.floorNode = floorNode;
        setOriginalFloorMaterial(floorNode.parts.get(0).material);
    }

    /**
     * @return the lampNode
     */
    public Node getLampNode() {
        return lampNode;
    }

    /**
     * @param lampNode the lampNode to set
     */
    public void setLampNode(Node lampNode) {
        this.lampNode = lampNode;
        setRoomLight(new DirectionalLight().set(Color.WHITE, position));
    }

    /**
     * @return the hvacNode
     */
    public Node getHvacNode() {
        return hvacNode;
    }

    /**
     * @param hvacNode the hvacNode to set
     */
    public void setHvacNode(Node hvacNode) {
        this.hvacNode = hvacNode;
    }

    /**
     * @return the boxNode
     */
    public Node getBoxNode() {
        return boxNode;
    }

    /**
     * @param boxNode the boxNode to set
     */
    public void setBoxNode(Node boxNode) {
        this.boxNode = boxNode;
    }

    /**
     * @return the roomLight
     */
    public DirectionalLight getRoomLight() {
        return roomLight;
    }

    /**
     * @param roomLight the roomLight to set
     */
    public void setRoomLight(DirectionalLight roomLight) {
        this.roomLight = roomLight;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the lightConsumption
     */
    public float getLightConsumption() {
        return lightConsumption;
    }

    /**
     * @return the roomOcupancy
     */
    public float getRoomOcupancy() {
        return roomOcupancy;
    }

    /**
     * @return the hvacConsumption
     */
    public float getHvacConsumption() {
        return hvacConsumption;
    }

    /**
     * @return the electricityComsumption
     */
    public float getElectricityComsumption() {
        return electricityComsumption;
    }

    /**
     * @return the area
     */
    public float getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(float area) {
        this.area = area;
    }

    /**
     * @return the volume
     */
    public float getVolume() {
        return volume;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * @return the perimeter
     */
    public float getPerimeter() {
        return perimeter;
    }

    /**
     * @param perimeter the perimeter to set
     */
    public void setPerimeter(float perimeter) {
        this.perimeter = perimeter;
    }

    public Array<Node> getWalls() {
        return walls;
    }

    public void setWalls(Array<Node> walls) {
        this.walls = walls;
    }

    public void hideWalls() {
        for (Node n : walls){
            n.parts.get(0).enabled = false;
        }
    }
    
    public void showWalls() {
        for (Node n : walls)
            n.parts.get(0).enabled = true;
    }

}
