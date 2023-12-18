package com.game.model;

import java.util.List;

public class Item extends Entity {
    private boolean pickUpable = false;
    private boolean isMurderWeapon;
    private boolean isWeapon;
    private boolean isHidden;
    private String jsonItemInside = "";

    public Item(){}
    public Item(String name, List<String> description, boolean canPickUp){
        super(name, description);
        setPickUpable(canPickUp);
    }

    public boolean isPickUpable() {
        return pickUpable;
    }

    public void setPickUpable(boolean canPickUp){
        pickUpable = canPickUp;
    }

    public boolean isMurderWeapon() {
        return isMurderWeapon;
    }

    public boolean isWeapon() {
        return isWeapon;
    }
    
    public boolean isHidden() {
        return isHidden;
    }
    
    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
    
    public String getJsonItemInside() {
        return jsonItemInside;
    }
    
    public void setJsonItemInside(String jsonItemInside) {
        this.jsonItemInside = jsonItemInside;
    }
}
