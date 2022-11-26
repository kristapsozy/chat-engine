package com.example.chatenginetemplate;

import com.example.chatenginetemplate.domain.Message;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ChatRepository {

    private ChatSession chatSession;

    private Map<String, String> userInputs = new HashMap<>();

    private List<Message> userMessages = new ArrayList<>();

    public void setChatSession(ChatSession chatSession) {
        this.chatSession = chatSession;
    }

    public ChatSession getChatSession() {
        return chatSession;
    }

    public Map<String, String> getUserInputs() {
        return userInputs;
    }

    public void saveUserInput(Message message) {
        Message userMessage = new Message("H: " + message.getText());
        userMessages.add(userMessage);
        String text = chatSession.getMessagesList().get(0);
        chatSession.getMessagesList().remove(0);
        String variableName = text.substring(text.indexOf(">=(") + 3, text.length() - 1);
        userInputs.put(variableName, message.getText());
    }

    public List<Message> getMessagesToSend() {
        List<Message> messagesToSend = new ArrayList<>();
        if (!userMessages.isEmpty()) {
            messagesToSend.add(userMessages.get(0));
            userMessages.remove(0);
        }
        for (String message : chatSession.getMessagesList()) {
            if (messageContainsUserData(message)) {
                messagesToSend.add(new Message(sendMessageWithUserData(message)));
                chatSession.getMessagesList().remove(0);
            } else if (message.startsWith("R:")) {
                messagesToSend.add(new Message(message));
                chatSession.getMessagesList().remove(0);
            } else {
                break;
            }
        }
        return messagesToSend;
    }

    public boolean messageContainsUserData(String text) {
        return text.contains("${");
    }


    public String sendMessageWithUserData(String text) {
        List<String> wordList = List.of(text.split(" "));
        String message = String.join(" ", wordList.stream().map(word -> word.contains("${") ?
                        word.replace(partToReplace(word), userInputs.get(variableToString(word))) : word)
                .toList());
        return message;
    }

    public String variableToString(String word) {
        return word.substring(word.indexOf("${") + 2, word.indexOf("}"));
    }

    public String partToReplace(String word) {
        return word.substring(word.indexOf("${"), word.indexOf("}") + 1);
    }
}
