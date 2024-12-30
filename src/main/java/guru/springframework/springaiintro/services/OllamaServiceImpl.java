package guru.springframework.springaiintro.services;

import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OllamaServiceImpl implements OllamaService {

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

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

    @Override
    public Answer getAnswer(Question question) {
        return new Answer(this.ollamaChatClient
                .prompt()
                .user(question.question())
                .call()
                .content());
    }

    @Override
    public Answer getAnswer(GetCapitalRequest getCapitalRequest) {
        //PromptTemplate promptTemplate = new PromptTemplate("What is the capital of {stateOrCountry} ?");
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        String answer = this.ollamaChatClient
                .prompt(prompt)
                .call()
                .content();

        return new Answer(answer);
    }
}
