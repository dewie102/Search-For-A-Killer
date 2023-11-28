package com.game.model;

import java.util.ArrayList;
import java.util.List;

/*
 * The Entity class represents any Object in the game
 */
public abstract class Entity {
    private String name;
    private String description;
    private Inventory inventory = new Inventory();
    private List<String> jsonInventory = new ArrayList<>();

    public Entity(){
        this.inventory = new Inventory();
    }

    public Entity(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<String> getJsonInventory() {
        return jsonInventory;
    }
}
