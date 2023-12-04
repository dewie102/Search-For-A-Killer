package com.game.controller.io;

import java.util.List;

public class JsonConversation {
    private List<String> greetings;
    private List<String> introductions;

    public List<String> getGreetings() {
        return greetings;
    }

    public void setGreetings(List<String> greetings) {
        this.greetings = greetings;
    }

    public List<String> getIntroductions() {
        return introductions;
    }

    public void setIntroductions(List<String> introductions) {
        this.introductions = introductions;
    }
}
