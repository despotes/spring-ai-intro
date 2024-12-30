package guru.springframework.springaiintro.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class OllamaServiceImplTest {

    @Autowired
    private OllamaService ollamaService;

    @Test
    void getAnswer() {
        String answer = ollamaService.getAnswer("how many 'r' are in the word strawberries?");
        System.out.println("Got the answer:");
        System.out.println(answer);
    }
}