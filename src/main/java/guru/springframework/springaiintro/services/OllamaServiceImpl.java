package guru.springframework.springaiintro.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OllamaServiceImpl implements OllamaService {

    private final ChatClient ollamaChatClient;

    public OllamaServiceImpl(ChatClient ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }

    @Override
    public String getAnswer(String question) {
        return this.ollamaChatClient
                .prompt()
                .user(question)
                .call()
                .content();
    }
}
