package com.game.model;

public class Item extends Entity{
    private boolean pickUpable = false;

    public Item(){}
    public Item(String name, String decription, boolean canPickUp){
        super(name, decription);
        setPickUpable(canPickUp);
    }

    public boolean isPickUpable() {
        return pickUpable;
    }

    public void setPickUpable(boolean canPickUp){
        pickUpable = canPickUp;
    }
}
