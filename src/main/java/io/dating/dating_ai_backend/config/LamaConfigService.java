package io.dating.dating_ai_backend.config;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.model.ChatResponse;

@Configuration
public class LamaConfigService {

    @Autowired
    private OllamaChatModel chatModel;

    public String generateResult(String prompt) {
        ChatResponse response = chatModel.call(
                new Prompt(
                        prompt,
                        OllamaOptions.create()
                                .withModel("llama3.2:1b")));
        return response.getResult().getOutput().getContent();
    }
}
