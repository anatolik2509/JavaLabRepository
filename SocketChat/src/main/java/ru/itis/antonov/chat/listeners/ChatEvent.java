package ru.itis.antonov.chat.listeners;

public class ChatEvent {
    private String lastMessage;

    public ChatEvent(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
