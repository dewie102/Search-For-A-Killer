package com.game.model;

public class Story {
    private String murderer;
    private String murderWeapon;
    
    public Story() {
        this("", "");
    }
    
    public Story(String murderer, String murderWeapon) {
        this.murderer = murderer;
        this.murderWeapon = murderWeapon;
    }
    
    public String getMurderer() {
        return murderer;
    }
    
    public void setMurderer(String murderer) {
        this.murderer = murderer;
    }
    
    public String getMurderWeapon() {
        return murderWeapon;
    }
    
    public void setMurderWeapon(String murderWeapon) {
        this.murderWeapon = murderWeapon;
    }
}