package io.dating.dating_ai_backend.conversations;

import io.dating.dating_ai_backend.profiles.Profile;

import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    private OllamaChatModel chatClient;

    public ConversationService(OllamaChatModel chatClient) {
        this.chatClient = chatClient;
    }

    public Conversation generateProfileResponse(Conversation conversation,
            Profile profile,
            Profile user) {
        // System message
        String systemMessageStr = String.format(
                """
                                You are a %s year old %s %s called %s %s matched
                                with a %s year old %s %s called %s %s on Tinder.
                                This is an in-app text conversation between you two.
                                Pretend to be the provided person and respond to the conversation as if writing on Tinder.
                                Your bio is: %s and your Myers Briggs personality type is %s. Respond in the role of this person only.
                                 # Personality and Tone:

                                 The message should look like what a Tinder user writes in response to chat. Keep it short and brief. No hashtags or generic messages.
                                 Be friendly, approachable, and slightly playful.
                                 Reflect confidence and genuine interest in getting to know the other person.
                                 Use humor and wit appropriately to make the conversation enjoyable.
                        """,
                profile.age(),
                profile.ethnicity(),
                profile.gender(),
                profile.firstName(),
                profile.lastName(),
                user.age(),
                user.ethnicity(),
                user.gender(),
                user.firstName(),
                user.lastName(),
                profile.bio(),
                profile.myersBriggsPersonalityType());

        SystemMessage systemMessage = new SystemMessage(systemMessageStr);

        List<AbstractMessage> conversationMessages = conversation.messages().stream().map(message -> {
            if (message.authorId().equals(profile.id())) {
                return new AssistantMessage(message.messageText());
            } else {
                return new UserMessage(message.messageText());
            }
        }).toList();

        List<Message> allMessages = new ArrayList<>();
        allMessages.add(systemMessage);
        allMessages.addAll(conversationMessages);

        Prompt prompt = new Prompt(allMessages);
        ChatResponse response = chatClient.call(prompt);
        conversation.messages().add(new ChatMessage(
                response.getResult().getOutput().getContent(),
                profile.id(),
                LocalDateTime.now()));
        return conversation;
    }

}