package com.game.model;

import java.util.List;

public class Item extends Entity{
    private boolean pickUpable = false;
    private boolean isMurderWeapon;
    private boolean isWeapon;

    public Item(){}
    public Item(String name, List<String> decription, boolean canPickUp){
        super(name, decription);
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
}
