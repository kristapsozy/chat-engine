package com.example.chatenginetemplate;

import com.example.chatenginetemplate.domain.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class ChatEngineTest {

    @Mock
    private ChatSession chatSession;

    @InjectMocks
    private ChatRepository chatRepository;


    @Test
    public void testGetMessagesToSend() {
        List<String> messages = new ArrayList<>();
        messages.add("R: Hello");
        messages.add("R: My name is Jeff!");
        messages.add("R: What's your name?");
        messages.add("H: <input-field>=(firstName)");
        messages.add("R: How old are you, ${firstName}?");
        messages.add("H: <input-field>=(age)");

        Mockito.doAnswer(invocation -> messages).when(chatSession).getMessagesList();

        List<Message> messageToSend = chatRepository.getMessagesToSend();

        Assertions.assertEquals(3, messageToSend.size());
    }

    @Test
    public void testGetMessagesToSendWithUserInput() {
        List<String> messages = new ArrayList<>();
        messages.add("H: <input-field>=(firstName)");
        messages.add("R: How old are you, ${firstName}?");
        messages.add("H: <input-field>=(age)");

        Mockito.doAnswer(invocation -> messages).when(chatSession).getMessagesList();

        chatRepository.saveUserInput(new Message("John"));
        List<Message> messageToSend = chatRepository.getMessagesToSend();

        Assertions.assertEquals(2, messageToSend.size());
    }

    @Test
    public void testGetMessagesToSendWithUserInputAndTwoResponses() {
        List<String> messages = new ArrayList<>();
        messages.add("H: <input-field>=(firstName)");
        messages.add("R: Nice to meet you, ${firstName}!");
        messages.add("R: How old are you?");

        Mockito.doAnswer(invocation -> messages).when(chatSession).getMessagesList();

        chatRepository.saveUserInput(new Message("John"));
        List<Message> messageToSend = chatRepository.getMessagesToSend();

        Assertions.assertEquals(3, messageToSend.size());
    }

    @Test
    public void testSaveUserInput() {
        List<String> messages = new ArrayList<>();
        messages.add("H: <input-field>=(firstName)");
        messages.add("R: How old are you, ${firstName}?");
        messages.add("H: <input-field>=(age)");

        Mockito.doAnswer(invocation -> messages).when(chatSession).getMessagesList();

        chatRepository.saveUserInput(new Message("John"));
        Map<String, String> userInputsMap = chatRepository.getUserInputs();

        Assertions.assertEquals("{firstName=John}", userInputsMap.toString());
    }

    @Test
    public void testMessageWithUserData() {
        List<String> messages = new ArrayList<>();
        messages.add("H: <input-field>=(firstName)");
        messages.add("R: How old are you, ${firstName}?");
        messages.add("H: <input-field>=(age)");

        Mockito.doAnswer(invocation -> messages).when(chatSession).getMessagesList();

        chatRepository.saveUserInput(new Message("John"));

        String message = chatRepository.messageWithUserData("R: How old are you, ${firstName}?");

        Assertions.assertEquals("R: How old are you, John?", message);
    }


    @Test
    public void testMessageContainsUserData() {
        boolean messageContainsUserData =
                chatRepository.messageContainsUserData("R: How old are you, ${firstName}?");

        Assertions.assertEquals(true, messageContainsUserData);
    }

    @Test
    public void testMessageDoesNotContainsUserData() {
        boolean messageContainsUserData =
                chatRepository.messageContainsUserData("R: How old are you?");

        Assertions.assertEquals(false, messageContainsUserData);
    }

    @Test
    public void convertVariableToString() {

        String convertedString = chatRepository.variableToString("${John}");

        Assertions.assertEquals("John", convertedString);

    }


}
