package com.example.chatenginetemplate;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatSession {
    private List<String> messagesList;

    public ChatSession(String... messages) {
        this.messagesList = new CopyOnWriteArrayList<>(List.of(messages));
    }

    public List<String> getMessagesList() {
        return messagesList;
    }

}
