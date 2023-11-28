package com.game.controller;

import java.util.List;

public class Command {
    private String keyWord;
    private List<String> synonyms;
    private String description;
    private boolean standalone;

    public Command(String keyWord, List<String> synonyms, String description, boolean standalone){
        this.keyWord = keyWord;
        this.synonyms = synonyms;
        this.description = description;
        this.standalone = standalone;
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
