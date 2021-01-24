package ru.itis.antonov.chat.server;

import ru.itis.antonov.chat.listeners.ChatListener;
import ru.itis.antonov.chat.listeners.ChatObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chat {

    private ChatObserver observer;

    private List<String> messages;

    public Chat(){
        messages = new ArrayList<String>();
        observer = new ChatObserver();
    }

    public void addMessage(String nick, String message){
        messages.add(0, nick + ": " + message);
        System.out.println(nick + ": " + message);
        observer.setLastMessage(nick + ": " + message);
    }

    public List<String> getMessages() {
        ArrayList<String> r = new ArrayList<>();
        Collections.copy(r, messages);
        return r;
    }

    public String getLastMessage(){
        return messages.get(0);
    }

    public void addEventListener(ChatListener l){
        observer.addEventListener(l);
    }
}
