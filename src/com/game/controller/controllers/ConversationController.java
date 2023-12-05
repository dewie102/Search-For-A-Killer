package com.game.controller.controllers;

import com.game.controller.LoadController;
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
        run(player, character, character.getConversation());
    }

    private void run(Player player, Character character, Conversation currentConversation){
        this.character = character;
        List<String> questions = currentConversation.getConversationQuestions();
        consoleView = new MultipleChoiceConsoleView(List.of(mainText, secondaryText), questions, false);
        int result = Integer.parseInt(consoleView.show());
        while (result != questions.size() - 1) {
            secondaryText.clear();
            secondaryText.add(new ConsoleText(String.format("This is a conversation between you and %s:", character.getName())));
            secondaryText.add(new ConsoleText(String.format("%s: %s", player.getName(), questions.get(result))));
            secondaryText.add(new ConsoleText(String.format("%s: %s", character.getName(), currentConversation.getDialog(result).getResponse())));
            // We check if the option selected has follow-up questions/dialog
            if(currentConversation.getDialog(result).getFollowUpConversation() != null){
                run(player, character, currentConversation.getDialog(result).getFollowUpConversation());
            }
            result = Integer.parseInt(consoleView.show());
        }
    }

    // TODO DELETE
    public static void main(String[] args) {
        while (true) {
            Player player = LoadController.getPlayer();


            List<ConsoleText> mainText = new ArrayList<>();
            mainText.add(new ConsoleText("This is a conversation between"));

            ConversationController controller = new ConversationController(mainText);

            controller.run(player, LoadController.getCharacters().get("Gardener"));
        }


//        Character character = new Character("Bob", "This is Bob", "Kitchen");
//        Conversation conversation = new Conversation();
//        conversation.addDialog(new Dialog("Hi how are you?", "I am fine."));
//        conversation.addDialog(new Dialog("Bye!", "See ya!"));
//
//        player = new Player("Player Name", "This is you bud", "Kitchen");
//
//        character.setConversation(conversation);
//        controller.run(player, character);
    }
}
