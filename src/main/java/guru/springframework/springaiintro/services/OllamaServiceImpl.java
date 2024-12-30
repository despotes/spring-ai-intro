package guru.springframework.springaiintro.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OllamaServiceImpl implements OllamaService {

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-prompt-with-info.st")
    private Resource getCapitalPromptWithPrompt;

    private final ChatClient ollamaChatClient;

    @Autowired
    ObjectMapper objectMapper;

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

        System.out.println(answer);

        String responseString;
        try {
            JsonNode jsonNode = objectMapper.readTree(answer);
            responseString = jsonNode.get("answer").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new Answer(responseString);
    }

    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        String answer = this.ollamaChatClient
                .prompt(prompt)
                .call()
                .content();

        return new Answer(answer);
    }
}
