package guru.springframework.springaiintro.services;

import guru.springframework.springaiintro.model.*;

public interface OllamaService {
    String getAnswer(String question);
    Answer getAnswer(Question question);

    GetCapitalResponse getAnswer(GetCapitalRequest getCapitalRequest);

    GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest);
}
