package com.game.controller.controllers;


import com.game.controller.CheckWinningConditions;
import com.game.model.Conversation;
import com.game.model.Player;
import com.game.view.gui.GameWindow;
import com.game.view.gui.MultipleChoiceDisplayView;
import com.game.view.terminal.ConsoleText;
import com.game.view.terminal.MultipleChoiceConsoleView;

import java.util.ArrayList;
import java.util.List;

import com.game.model.Character;

public class ConversationController {
    private MultipleChoiceConsoleView consoleView;
    private List<ConsoleText> mainText = new ArrayList<>();
    private List<ConsoleText> secondaryText = new ArrayList<>();
    private Character character;
    public CheckWinningConditions checkWinningConditions;
    public int result = -1;
    public boolean followedUpQuestion = false;


    public ConversationController(List<ConsoleText> mainText, CheckWinningConditions checkWinningConditions){
        this.mainText = mainText;
        setCheckWinningConditions(checkWinningConditions);
    }

    public void run(Player player, Character character){
        run(player, character, character.getConversation());
    }

    private void run(Player player, Character character, Conversation currentConversation){
        this.character = character;
        List<String> questions = currentConversation.getConversationQuestions();


        // While the last option selected wasn't the last option and winning condition is undefined, keep asking.
        // The last option added to question essentially goes back
//        while (result != questions.size() - 1 && (checkWinningConditions == null || checkWinningConditions.checkWinningConditions() == GameResult.UNDEFINED)) {
//            if (checkWinningConditions != null && checkWinningConditions.checkWinningConditions() != GameResult.UNDEFINED)
//                break;
//            consoleView = new MultipleChoiceConsoleView(List.of(mainText, secondaryText), questions, false);
//            secondaryText.clear();
//            secondaryText.add(new ConsoleText(String.format("This is a conversation between you and %s:", character.getName())));

//            );


        secondaryText.add(new ConsoleText(String.format("This is a conversation between you and %s:", character.getName())));
        MultipleChoiceDisplayView displayView = new MultipleChoiceDisplayView(List.of(secondaryText), questions, GameWindow.talkTextArea, GameWindow.talkButtonPanel);

        GameWindow.gameTextPanel.setVisible(false);
        GameWindow.mainTalkPanel.setVisible(true);
        GameWindow.talkButtonPanel.removeAll();
        GameWindow.talkButtonPanel.revalidate();
        GameWindow.talkButtonPanel.repaint();


        if (result != -1) {
                secondaryText.clear();
                secondaryText.add(new ConsoleText(String.format("This is a conversation between you and %s:", character.getName())));
                secondaryText.add(new ConsoleText(String.format("%s: %s", player.getName(), questions.get(result))));
                secondaryText.add(new ConsoleText(String.format("%s: %s", character.getName(), currentConversation.getDialog(result).getResponse())));
                // This will report in case is possible, triggering a callback to report when the player tells the detective which one was the murder
        }
            // We check if the option selected has follow-up questions/dialog
//            result = Integer.parseInt(consoleView.show());
        displayView.show();
        if (result != -1) {
//            currentConversation.getDialog(result).reportIfAble(); //report murder
            if(currentConversation.getDialog(result).getFollowUpConversation() != null){
//                run(player, character, currentConversation.getDialog(result).getFollowUpConversation());
                handleFollowUp(currentConversation.getDialog(result).getFollowUpConversation());
//                result = -1;
                // This fixes the loop but we lost the secondary text
            } else if(currentConversation.getDialog(result).endsConversation()) {
//               result = questions.size() - 1;
            }
            else if (result == questions.size() - 1) {
                GameWindow.gameTextPanel.setVisible(true);
                GameWindow.mainTalkPanel.setVisible(false);
                result = -1;
                secondaryText.clear();
            }
        }
    }

    private void handleFollowUp(Conversation conversation) {
        List<String> questions = conversation.getConversationQuestions();

        MultipleChoiceDisplayView displayView = new MultipleChoiceDisplayView(List.of(secondaryText), questions, GameWindow.talkTextArea, GameWindow.talkButtonPanel);

        GameWindow.talkButtonPanel.removeAll();
        GameWindow.talkButtonPanel.revalidate();
        GameWindow.talkButtonPanel.repaint();

        displayView.show();

        followedUpQuestion = true;
        secondaryText.clear();

    }

    public void setCheckWinningConditions(CheckWinningConditions checkWinningConditions) {
        this.checkWinningConditions = checkWinningConditions;
    }

}
