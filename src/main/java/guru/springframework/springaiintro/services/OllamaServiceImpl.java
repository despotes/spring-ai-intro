package guru.springframework.springaiintro.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.springaiintro.model.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

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
    public GetCapitalResponse getAnswer(GetCapitalRequest getCapitalRequest) {
        BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);
        String format = converter.getFormat();
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(), "format", format));
        return converter.convert(Objects.requireNonNull(this.ollamaChatClient.prompt(prompt).call().content()));
    }

    @Override
    public GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        BeanOutputConverter<GetCapitalWithInfoResponse> converter = new BeanOutputConverter<>(GetCapitalWithInfoResponse.class);
        String format = converter.getFormat();
        System.out.println(format);
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(), "format", format));
        String answer = this.ollamaChatClient.prompt(prompt).call().content();
        System.out.println(answer);
        return converter.convert(Objects.requireNonNull(answer));
    }
}
