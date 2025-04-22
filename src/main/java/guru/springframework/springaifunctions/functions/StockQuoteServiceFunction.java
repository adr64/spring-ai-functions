package guru.springframework.springaifunctions.functions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.springaifunctions.model.StockQuoteRequest;
import guru.springframework.springaifunctions.model.StockQuoteResponse;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.function.Function;

public class StockQuoteServiceFunction implements Function<StockQuoteRequest, StockQuoteResponse> {

    public static final String STOCK_PRICE_URL = "https://api.api-ninjas.com/v1/stockprice";

    private final String apiNinjasKey;

    public StockQuoteServiceFunction(String apiNinjasKey) {
        this.apiNinjasKey = apiNinjasKey;
    }

    @Override
    public StockQuoteResponse apply(StockQuoteRequest stockQuoteRequest) {
        RestClient restClient = RestClient.builder()
                .baseUrl(STOCK_PRICE_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-Api-Key", apiNinjasKey);
                    httpHeaders.set("Accept", "application/json");
                    httpHeaders.set("Content-Type", "application/json");
                }).build();

        JsonNode jsonNode = restClient.get().uri(uriBuilder -> {
            System.out.println("Building URI for stock quote with ticker: " + stockQuoteRequest.ticker());
            uriBuilder.queryParam("ticker", stockQuoteRequest.ticker());
            return uriBuilder.build();
        }).retrieve().body(JsonNode.class);
        System.out.println("Response: " + jsonNode);
        if (Optional.ofNullable(jsonNode).filter(node -> !node.isEmpty()).isPresent()) {
            StockQuoteResponse stockQuoteResponse =  new ObjectMapper().convertValue(jsonNode, StockQuoteResponse.class);
            if (stockQuoteResponse.updated() != null) {
                LocalDateTime updatedTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(
                        stockQuoteResponse.updated())), ZoneId.systemDefault());
                System.out.println("Updated time: " + updatedTime);
                stockQuoteResponse = new StockQuoteResponse(stockQuoteResponse.ticker(), stockQuoteResponse.name(),
                        stockQuoteResponse.price(), stockQuoteResponse.exchange(), stockQuoteResponse.currency(),
                        updatedTime.toString());
            }
            return stockQuoteResponse;
        }
        return new StockQuoteResponse(null, null, null, null, null, null);
    }
}















