package guru.springframework.springaiintro.controllers;

import guru.springframework.springaiintro.model.*;
import guru.springframework.springaiintro.services.OllamaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    private final OllamaService ollamaService;

    public QuestionController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping("/capitalWithInfo")
    public GetCapitalWithInfoResponse getCapitalWithInfo(@RequestBody GetCapitalRequest getCapitalRequest) {
        return ollamaService.getCapitalWithInfo(getCapitalRequest);
    }

    @PostMapping("/capital")
    public GetCapitalResponse getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
        return ollamaService.getAnswer(getCapitalRequest);
    }

    @PostMapping("/ask")
    public Answer addQuestion(@RequestBody Question question) {
        return ollamaService.getAnswer(question);
    }
}
