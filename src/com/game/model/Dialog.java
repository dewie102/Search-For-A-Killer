package com.game.model;

public class Dialog {
    private String question;
    private String response;
    private Conversation followUpConversation;
    private int dept;

    public Dialog(String question, String response){
        this.question = question;
        this.response = response;
        this.dept = 0;
    }

    public Dialog(String question, String response, Conversation followUpConversation){
        this.question = question;
        this.response = response;
        this.followUpConversation = followUpConversation;
        this.dept = 0;
    }

    public Dialog(String question, String response, int dept){
        this(question, response);
        this.dept = dept;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getDept() {
        return dept;
    }

    public void setDept(int dept) {
        this.dept = dept;
    }

    public Conversation getFollowUpConversation() {
        return followUpConversation;
    }

    public void setFollowUpConversation(Conversation followUpConversation) {
        this.followUpConversation = followUpConversation;
    }
}
