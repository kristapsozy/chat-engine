package com.example.chatenginetemplate;

import com.example.chatenginetemplate.domain.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/start-chat")
    public ResponseEntity<List<Message>> startingMessage() {
        ChatSession chatSession = new ChatSession("R: Hi there, I'm Jeff 👋",
                "R: Your new best friend for finding great loan offers!",
                "R: First things first - let's get your account set up 🛠️",
                "R: What is your first name?",
                "H: <input-field>=(firstName)",
                "R: And what is your last name?",
                "H: <input-field>=(lastName)",
                "R: Nice to meet you, ${firstName} ${lastName}!",
                "R: ${firstName}, what's your email address?",
                "H: <input-field>=(email)",
                "R: Fantastic. We are 70% done with the setup!",
                "R: Your age is another important value for finding the best offers. Please enter your date of birth 📅",
                "H: <input-field>=(birthDate)",
                "R: And what do you need the money for?",
                "H: <input-field>=(needMoneyFor)",
                "R: Nice, I already have some options for you");
        chatService.startSession(chatSession);
        return new ResponseEntity<>(chatService.getMessagesToSend(), HttpStatus.OK);
    }

    @PutMapping("/chat")
    public ResponseEntity<Message> getUserInput(@RequestBody Message message) {
        chatService.saveUserInput(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/chat")
    public ResponseEntity<List<Message>> sendMessage() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(chatService.getMessagesToSend(), HttpStatus.OK);
    }

}
