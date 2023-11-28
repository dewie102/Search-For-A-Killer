package com.game.view;

import com.game.view.framework.InputCollector;
import com.game.view.framework.InvalidInputException;

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
    void executeViewLogic() {
        for (int i = 0; i < options.size(); i++){
            Console.print(Integer.toString(i + 1) + ": ");
            Console.printNewLine(this.options.get(i));
        }
    }

    @Override
    String collectInput() {
        return InputCollector.collectInput(options);
    }
}

