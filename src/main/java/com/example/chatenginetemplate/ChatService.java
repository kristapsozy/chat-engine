package com.example.chatenginetemplate;

import com.example.chatenginetemplate.domain.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    public void startSession(ChatSession chatSession) {
        chatRepository.setChatSession(chatSession);
    }

    public ChatSession getChatSession() {
        return chatRepository.getChatSession();
    }

    public void saveUserInput(Message message) {
        chatRepository.saveUserInput(message);
    }

    public List<Message> getMessagesToSend() {
        return chatRepository.getMessagesToSend();
    }


}
