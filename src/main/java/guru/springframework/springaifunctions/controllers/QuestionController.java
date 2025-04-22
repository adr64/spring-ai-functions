package guru.springframework.springaifunctions.controllers;

import guru.springframework.springaifunctions.model.Answer;
import guru.springframework.springaifunctions.model.Question;
import guru.springframework.springaifunctions.services.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    @PostMapping("/weather")
    public Answer getWeather(@RequestBody Question question) {
        return openAIService.getWeather(question);
    }

    @PostMapping("/stock")
    public Answer getStockQuote(@RequestBody Question question) {
        return openAIService.getStockPrice(question);
    }

}
