package com.game.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.game.view.framework.InputCollector.collectInput;

public class MultipleChoiceConsoleView extends ConsoleView{
    private List<ConsoleText> options;

    public MultipleChoiceConsoleView(List<ConsoleText> text, List<ConsoleText> options) {
        super(text.toArray(new ConsoleText[0]));
        this.options = options;
    }


    public void addOptions(ConsoleText ...options){
        this.options.addAll(Arrays.asList(options));
    }

    @Override
    public String show(){
        for(ConsoleText t : text){
            Console.print(t.text, t.textColor, t.backgroundColor);
        }
        for (int i = 0; i < options.size(); i++){
            Console.print(Integer.toString(i + 1) + ": ");
            Console.printNewLine(this.options.get(i));
        }
        // INPUT COLLECTOR
        // call collectInput with an empty ignoreList
        // returns the collected input String
        return collectInput(options);
    }
}
