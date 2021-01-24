package ru.itis.antonov.chat.listeners;

import java.util.ArrayList;
import java.util.List;

public class ChatObserver {
    private List<ChatListener> list;

    public ChatObserver(){
        list = new ArrayList<>();
    }

    public void setLastMessage(String lastMessage) {
        ChatEvent e = new ChatEvent(lastMessage);
        activate(e);
    }

    public void addEventListener(ChatListener l){
        list.add(l);
    }

    private void activate(ChatEvent e){
        for (ChatListener listener : list){
            listener.activate(e);
        }
    }
}
