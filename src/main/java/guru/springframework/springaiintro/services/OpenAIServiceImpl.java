package guru.springframework.springaiintro.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient openAIChatClient;

    public OpenAIServiceImpl(ChatClient openAIChatClient) {
        this.openAIChatClient = openAIChatClient;
    }

    @Override
    public String getAnswer(String question) {
        return this.openAIChatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
