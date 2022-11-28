package com.example.chatenginetemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatSession {
    private List<String> messagesList;

    public ChatSession(String... messages) {
        this.messagesList = new ArrayList<>(List.of(messages));
    }

    public List<String> getMessagesList() {
        return messagesList;
    }

    @Override
    public String toString() {
        return "ChatSession{" +
                "messagesList=" + messagesList +
                '}';
    }
}
