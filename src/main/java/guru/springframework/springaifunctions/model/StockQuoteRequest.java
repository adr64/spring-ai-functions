package guru.springframework.springaifunctions.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Stock Price API request using stock ticker")
public record StockQuoteRequest(@JsonProperty(required = true, value = "ticker")
                                @JsonPropertyDescription("Ticker of desire stock") String ticker) {
}