package com.game.model;

import com.game.controller.GameController;

import java.util.ArrayList;
import java.util.List;

/*
 * The Entity class represents any Object in the game
 */
public abstract class Entity {
    private String name;
    private List<String> description;
    private Inventory inventory = new Inventory();
    private final List<String> jsonInventory = new ArrayList<>();

    public Entity(){
        this.inventory = new Inventory();
    }

    public Entity(String name, List<String> description){
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
        if(description.size() > GameController.getInstance().STORYLINE) {
            return description.get(GameController.getInstance().STORYLINE);
        } else {
            return description.get(0);
        }
    }

    public void setDescription(List<String> description) {
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

    @Override
    public String toString(){
        return this.getName();
    }
}
