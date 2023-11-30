package com.game.controller;

import com.game.controller.commands.CommandCallBack;
import com.game.model.Entity;

import java.util.List;

public class Command {
    // Main keyword of the command, it should be unique
    private String keyWord;
    // Any synonym that can be used instead of the main keyword e.g. command "go" may have "run", "move", "travel" as synonyms
    private List<String> synonyms;
    // A description to be shown when the user checks the list of commands and what they do
    private String description;
    // A standalone command is one that doesn't need a target e.g. "quit", "help"
    // On the other hand a non-standalone command would be one with a target e.g. "go there", "get egg"
    private boolean standalone;
    // This will be a functional interface instance that receives an Entity and returns true/false
    private CommandCallBack callBack;

    public Command(String keyWord, List<String> synonyms, String description, boolean standalone){
        this.keyWord = keyWord;
        this.synonyms = synonyms;
        this.description = description;
        this.standalone = standalone;
    }

    public Command(String keyWord, List<String> synonyms, String description, boolean standalone, CommandCallBack callBack){
        this(keyWord, synonyms, description, standalone);
        this.callBack = callBack;
    }

    // Returns true/false if the keyWord matches the command's keyWord or one of its synonyms
    public boolean isAMatch(String keyWord){
        return (keyWord.toLowerCase().equals(this.keyWord.toLowerCase())
                || this.synonyms.contains(keyWord));
    }

    public boolean executeCommand(Entity target){
        return callBack.execute(target);
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStandalone() {
        return standalone;
    }

    public void setStandalone(boolean standalone) {
        this.standalone = standalone;
    }
}
