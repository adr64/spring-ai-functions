package guru.springframework.springaifunctions.services;


import guru.springframework.springaifunctions.functions.WeatherServiceFunction;
import guru.springframework.springaifunctions.model.Answer;
import guru.springframework.springaifunctions.model.Question;
import guru.springframework.springaifunctions.model.WeatherRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jt, Spring Framework Guru.
 */
@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${sfg.aiapp.apiNinjasKey}")
    private String apiNinjasKey;

    private final OpenAiChatModel openAiChatModel;

    @Override
    public Answer getAnswer(Question question) {
        var promptOptions = OpenAiChatOptions.builder()
                .functionCallbacks(List.of(FunctionCallback.builder()
                                .function("CurrentWeather", new WeatherServiceFunction(apiNinjasKey))
                                .description("Get the current weather for a location")
                                .inputType(WeatherRequest.class)
                      .build()))
                .build();

        Message userMessage = new PromptTemplate(question.question()).createMessage();

        Message systemMessage = new SystemPromptTemplate("You are a weather service. You receive weather information " +
                "from a service which gives you the information based on the metrics system. When answering the " +
                "weather in an imperial system country, you should convert the temperature to Fahrenheit and the " +
                "wind speed to miles per hour. Include all available data and also inform what data " +
                "is not available all the time, convert the EPOC time to local time of request if needed. Please " +
                "use a simple paragraph or sentence to return answer, avoid line break chars and things like that, you " +
                "can use Celsius or Fahrenheit symbols all the time.").createMessage();

        var response = openAiChatModel.call(new Prompt(List.of(userMessage, systemMessage), promptOptions));

        return new Answer(response.getResult().getOutput().getText());
    }
}


















