package com.game.view.gui;

import com.game.view.terminal.ConsoleText;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultipleChoiceDisplayView extends DisplayView {
    private final List<ConsoleText> options;
    private JPanel panel;

    public MultipleChoiceDisplayView(List<List<ConsoleText>> textList, List<String> options, JTextComponent textComponent) {
        super(textList, textComponent);
        this.options = new ArrayList<>();
        for (var option : options){
            addOptions(new ConsoleText(option));
        }
    }

    public MultipleChoiceDisplayView(List<List<ConsoleText>> textList, List<String> options, JTextComponent textComponent, JPanel panel) {
        this(textList, options, textComponent);
        this.panel = panel;
    }

    public void addOptions(ConsoleText ...options){
        this.options.addAll(Arrays.asList(options));
    }

    @Override
    public void executeViewLogic() {
        for (int i = 0; i < options.size(); i++){
            Display.printNewLineWithButton(this.options.get(i).getText(), i, getDisplayComponent(), panel);
        }
    }

    @Override
    public String collectInput() {
        return "";
    }

    public List<ConsoleText> getOptions() {
        return options;
    }

    public void setOptions(List<ConsoleText> options) {
        this.options.clear();
        this.options.addAll(options);
    }
}
