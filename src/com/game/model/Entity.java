package com.game.model;

/*
 * The Entity class represents any Object in the game
 */
public abstract class Entity {
    private String name;
    private String description;
    private Inventory inventory = new Inventory();

    public Entity(){

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
}
