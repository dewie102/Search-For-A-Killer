package com.game.controller;

import com.game.view.framework.InputCollector;
import org.junit.Before;
import org.junit.Test;

import javax.print.attribute.HashPrintJobAttributeSet;
import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MapLoaderControllerTest {
    
    MapLoaderController mapLoaderController;
    Map<String, Map<String, String>> rooms;
    Map<String, Map<String, Map<String, String>>> levels;
    Map<String, Map<String, Map<String, Map<String, String>>>> layers;
    
    @Before
    public void setUp() {
        mapLoaderController = new MapLoaderController();
        rooms = new HashMap<>();
        rooms.put("Office", new HashMap<>());
    
        levels = new HashMap<>();
        levels.put("1", rooms);
        
        layers = new LinkedHashMap<>();
        layers.put("1", levels);
        
        mapLoaderController.setGameMap(layers);
    }
    
    @Test
    public void loadIcons_correctly() {
        mapLoaderController.loadIcons();
        ImageIcon icon = new ImageIcon("data/icons/Office.png");
        assertEquals(icon.getImage(), mapLoaderController.getRoomIcons().get("Office").getImage());
    }
}