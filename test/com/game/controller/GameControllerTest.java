package com.game.controller;

import com.game.model.Character;
import com.game.model.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameControllerTest {
    private GameController gameController;

    @Before
    public void setUp() {
        gameController = GameController.getInstance();
        gameController.initialize();
    }

    @Test
    public void testCheckForWinningConditionsWhenUndefined() {
        // check without reporting murderer and weapon
        GameResult result = gameController.checkForWinningConditions();
        assertEquals(GameResult.UNDEFINED, result);
    }

    @Test
    public void testCheckForWinningConditionsWhenWin() {
        // Set up conditions for a winning scenario
        gameController.reportedMurder = new Character("Gardener", new ArrayList<>(), "");
        gameController.reportedMurderWeapon = new Item("Knife", new ArrayList<>(), true);

        GameResult result1 = gameController.checkForWinningConditions();

        gameController.reportedMurder = new Character("Butler", new ArrayList<>(), "");
        gameController.reportedMurderWeapon = new Item("Pen", new ArrayList<>(), true);

        GameResult result2 = gameController.checkForWinningConditions();

        gameController.reportedMurder = new Character("Nanny", new ArrayList<>(), "");
        gameController.reportedMurderWeapon = new Item("Glove", new ArrayList<>(), true);

        GameResult result3 = gameController.checkForWinningConditions();

        // check if any of the randomly generated combination matches
        assertTrue(result1 == GameResult.WIN || result2 == GameResult.WIN || result3 == GameResult.WIN);
    }
}
