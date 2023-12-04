package com.game.controller.controllers;

import com.game.model.Conversation;
import com.game.model.Dialog;
import com.game.model.Player;
import com.game.view.ConsoleText;
import com.game.view.MultipleChoiceConsoleView;

import java.util.ArrayList;
import java.util.List;
import com.game.model.Character;

public class ConversationController {
    private MultipleChoiceConsoleView consoleView;
    private List<ConsoleText> mainText = new ArrayList<>();
    private List<ConsoleText> secondaryText = new ArrayList<>();
    private Character character;

    public ConversationController(List<ConsoleText> mainText){
        this.mainText = mainText;
    }

    public void run(Player player, Character character){
        this.character = character;
        List<String> questions = character.getConversation().getConversationQuestions();
        consoleView = new MultipleChoiceConsoleView(List.of(mainText, secondaryText), questions, false);
        int result = Integer.parseInt(consoleView.show());
        while (result != questions.size() - 1) {
            secondaryText.clear();
            secondaryText.add(new ConsoleText(String.format("%s: %s", player.getName(), questions.get(result))));
            secondaryText.add(new ConsoleText(String.format("%s: %s", character.getName(), character.getConversation().getDialog(result).getResponse())));
            result = Integer.parseInt(consoleView.show());
        }
    }

    // TODO DELETE
    public static void main(String[] args) {
        List<ConsoleText> mainText = new ArrayList<>();
        mainText.add(new ConsoleText("Menu and options blah blah blah"));

        ConversationController controller = new ConversationController(mainText);
        Character character = new Character("Bob", "This is Bob", "Kitchen");
        Conversation conversation = new Conversation();
        conversation.addDialog(new Dialog("Hi how are you?", "I am fine."));
        conversation.addDialog(new Dialog("Bye!", "See ya!"));

        Player player = new Player("Player Name", "This is you bud", "Kitchen");

        character.setConversation(conversation);
        controller.run(player, character);
    }
}
