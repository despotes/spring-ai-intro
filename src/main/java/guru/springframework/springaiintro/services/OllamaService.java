package guru.springframework.springaiintro.services;

import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.Question;

public interface OllamaService {
    String getAnswer(String question);
    Answer getAnswer(Question question);
}
