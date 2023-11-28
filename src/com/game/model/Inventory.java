package com.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * An inventory is a collection of Items.
 */
public class Inventory {
    private List<Item> inventory = new ArrayList<>();

    public Inventory(Item ...items){
        inventory.addAll(Arrays.asList(items));
    }

    //TODO: Implement
    public Item findByName(String itemName){
        throw new NotImplementedException("findByName");
    }
}
