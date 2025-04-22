package guru.springframework.springaifunctions.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record StockQuoteResponse(@JsonPropertyDescription("Ticker") String ticker,
                                 @JsonPropertyDescription("Name") String name,
                                 @JsonPropertyDescription("Current Price") Double price,
                                 @JsonPropertyDescription("Exchange") String exchange,
                                 @JsonPropertyDescription("Price currency") String currency,
                                 @JsonPropertyDescription("Price currency") String updated) {
}


