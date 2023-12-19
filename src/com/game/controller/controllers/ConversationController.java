package com.game.controller.controllers;


import com.game.controller.CheckWinningConditions;
import com.game.controller.GameResult;
import com.game.controller.MainController;
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
        if (!MainController.PLAY_IN_GUI) {
            run(player, character, character.getConversation());
        } else {
            runConversation(player, character, character.getConversation());
        }

    }

    private void run(Player player, Character character, Conversation currentConversation){
        this.character = character;
        List<String> questions = currentConversation.getConversationQuestions();

        int result = -1;
        // While the last option selected wasn't the last option and winning condition is undefined, keep asking.
        // The last option added to question essentially goes back
        while (result != questions.size() - 1 && (checkWinningConditions == null || checkWinningConditions.checkWinningConditions() == GameResult.UNDEFINED)) {
            if(checkWinningConditions != null && checkWinningConditions.checkWinningConditions() != GameResult.UNDEFINED)
                break;
            consoleView = new MultipleChoiceConsoleView(List.of(mainText, secondaryText), questions, false);
            secondaryText.clear();
            secondaryText.add(new ConsoleText(String.format("This is a conversation between you and %s:", character.getName())));
            if(result != -1) {
                secondaryText.add(new ConsoleText(String.format("%s: %s", player.getName(), questions.get(result))));
                secondaryText.add(new ConsoleText(String.format("%s: %s", character.getName(), currentConversation.getDialog(result).getResponse())));
                // This will report in case is possible, triggering a callback to report when the player tells the detective which one was the murder
            }
            // We check if the option selected has follow-up questions/dialog
//            result = Integer.parseInt(consoleView.show());
            currentConversation.getDialog(result).reportIfAble();

            if(currentConversation.getDialog(result).getFollowUpConversation() != null){
                run(player, character, currentConversation.getDialog(result).getFollowUpConversation());
                result = -1;
                // This fixes the loop but we lost the secondary text
            } else if(currentConversation.getDialog(result).endsConversation()) {
                result = questions.size() - 1;
            }
        }
    }

    private void runConversation(Player player, Character character, Conversation currentConversation){
        this.character = character;
        List<String> questions = currentConversation.getConversationQuestions();

        secondaryText.add(new ConsoleText(String.format("This is a conversation between you and %s:", character.getName())));
        MultipleChoiceDisplayView displayView = new MultipleChoiceDisplayView(List.of(secondaryText), questions, GameWindow.talkTextArea, GameWindow.talkButtonPanel);

        // make talk panel visible and hide game text panel
        GameWindow.gameTextPanel.setVisible(false);
        GameWindow.mainTalkPanel.setVisible(true);
        GameWindow.talkButtonPanel.removeAll();
        GameWindow.talkButtonPanel.revalidate();
        GameWindow.talkButtonPanel.repaint();


        // already in conversation
        if (result != -1) {
                secondaryText.clear();
                secondaryText.add(new ConsoleText(String.format("This is a conversation between you and %s:", character.getName())));
                secondaryText.add(new ConsoleText(String.format("%s: %s", player.getName(), questions.get(result))));
                secondaryText.add(new ConsoleText(String.format("%s: %s", character.getName(), currentConversation.getDialog(result).getResponse())));
        }

        displayView.show();

        if (result != -1) {
            // if dialog has follow-up questions (to get response for murderer and murder weapon)
            if(currentConversation.getDialog(result).getFollowUpConversation() != null){
                handleFollowUp(currentConversation.getDialog(result).getFollowUpConversation());

            // exit conversation
            } else if (result == questions.size() - 1) {
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

        // display talk panel with follow-up questions
        displayView.show();

        // this will be used to check winning condition after each follow-up question answered
        followedUpQuestion = true;
        secondaryText.clear();
    }

    public void setCheckWinningConditions(CheckWinningConditions checkWinningConditions) {
        this.checkWinningConditions = checkWinningConditions;
    }

}
