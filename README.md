# Chat Engine

## Description

![](https://github.com/kristapsozy/chat-engine/blob/main/chat-engine.gif)


1. This application sends Messages and receives user inputs via REST endpoints.
2. Endpoints are processed with Angular fronted application (frontend can be improved, it's just for testing purposes).
- Link to Angular frontend: https://github.com/kristapsozy/chat-engine-frontend

## How it works

1. Chat script needs to be added in ChatSession. Like in example below:
```
ChatSession chatSession = new ChatSession("R: Hi there, I'm Jeff 👋",
                "R: Your new best friend for finding great loan offers!",
                "R: First things first - let's get your account set up 🛠️",
                "R: What is your first name?",
                "H: <input-field>=(firstName)",
                "R: Nice to meet you, ${firstName}");
```
2. Application will read this script and process user inputs.
3. User inputs are saved to Map object. They can be used later if needed. 
4. There are some rules for script formating:
- Message is allowed to start with ```H: ``` (User message) or```R: ``` (Computer message)
- User input field needs to be: ```H: <input-field>=(firstName)```. In this example Map object key will be ```firstName```. It can be replaced with any variable name.
- To use user inputs in messages ```${firstName}``` needs to be included. The application will replace this part of the message with user input. 
The variable name in the input field needs to be the same as in the message text between the ```${}```.
5. To test how it works, clone this project and launch Angular application.
- Chat engine can be tested on ```http://localhost:4200/chat-engine```
