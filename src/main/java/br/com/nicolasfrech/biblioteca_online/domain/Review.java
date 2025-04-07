package br.com.nicolasfrech.biblioteca_online.domain;

public class Review {
    private String username;
    private String content;

    public Review() {}

    public Review(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }
}