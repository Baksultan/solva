package kz.solva.service.currencyExchangeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class ConvertService {

    private RestTemplate restTemplate;

    @Autowired
    public ConvertService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Value("http://localhost:9000/exchange_rate/")
    private String apiUrl;

    public BigDecimal convertToUSD(String c2, BigDecimal amount) {

        if (c2.equals("USD")) {
            return amount;
        }

        String url = apiUrl + c2 + "/USD";

        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<BigDecimal>() {});
            if (response.getStatusCode() == HttpStatus.OK) {
                return amount.multiply(response.getBody());
            } else {
                return null;
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }

}
