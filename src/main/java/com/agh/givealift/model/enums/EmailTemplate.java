package com.agh.givealift.model.enums;

public enum EmailTemplate {
    USER_SIGN_UP("Witamy w Give a lift", "ELO"),
    PASSWORD_RESET("Reset Hasła", "ELO");


    private String subject;
    private String text;

    EmailTemplate(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

}
