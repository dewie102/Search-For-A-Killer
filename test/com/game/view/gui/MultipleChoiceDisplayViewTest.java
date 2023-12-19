package com.game.view.gui;

import com.game.view.terminal.ConsoleText;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MultipleChoiceDisplayViewTest {

    private JTextComponent textComponent;
    private JPanel panel;

    @Before
    public void setUp() {
        textComponent = new JTextArea();
        panel = new JPanel();
    }

    @Test
    public void shouldAddOptionsToOptionsList() {
        List<List<ConsoleText>> textList = List.of(List.of(new ConsoleText("Main Text")));
        List<String> options = List.of("Hi", "My name is detective");
        MultipleChoiceDisplayView displayView = new MultipleChoiceDisplayView(textList, options, textComponent, panel);

        // add more options
        displayView.addOptions(new ConsoleText("What do you know?"), new ConsoleText("Bye"));

        List<ConsoleText> actualOptions = displayView.getOptions();
        // check options
        assertEquals(4, actualOptions.size());
        assertEquals("Hi", actualOptions.get(0).getText());
        assertEquals("My name is detective", actualOptions.get(1).getText());
        assertEquals("What do you know?", actualOptions.get(2).getText());
        assertEquals("Bye", actualOptions.get(3).getText());
    }
}
