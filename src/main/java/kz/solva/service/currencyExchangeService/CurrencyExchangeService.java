package kz.solva.service.currencyExchangeService;

import kz.solva.model.entity.CurrencyExchange;
import kz.solva.model.responseModel.CurrencyExchangeResponse;
import kz.solva.repository.CurrencyExchangeRepository;
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

import java.util.List;


@Service
public class CurrencyExchangeService {

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;
    private RestTemplate restTemplate;

    @Value("${exchange.api.url}")
    private String apiUrl;

    @Value("${exchange.api.key}")
    private String apiKey;

    @Autowired
    public CurrencyExchangeService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void updateCurrencyExchangeRates() {
        List<CurrencyExchange> currencyExchangeRateList = currencyExchangeRepository.findAll();

        for (CurrencyExchange c: currencyExchangeRateList) {

            if (c.getFirstCurrency().equals("KZT") && c.getSecondCurrency().equals("USD")) {
                String url = apiUrl + "?symbol=" + c.getSecondCurrency() + "/" + c.getFirstCurrency() + "&apikey=" + apiKey;

                try {
                    ResponseEntity<CurrencyExchangeResponse> response = restTemplate.exchange(url, HttpMethod.GET, null,
                            new ParameterizedTypeReference<CurrencyExchangeResponse>() {
                            });

                    if (response.getStatusCode() == HttpStatus.OK) {
                        CurrencyExchangeResponse responseBody = response.getBody();
                        Double rate = (1 / responseBody.getRate());
                        CurrencyExchange exchangeRate = c;
                        exchangeRate.setFirstCurrency(c.getFirstCurrency());
                        exchangeRate.setSecondCurrency(c.getSecondCurrency());
                        exchangeRate.setRate(rate);
                        currencyExchangeRepository.save(exchangeRate);
                    }
                } catch (RestClientException e) {
                    e.printStackTrace();
                }

                continue;
            }

            String url = apiUrl + "?symbol=" + c.getFirstCurrency() + "/" + c.getSecondCurrency() + "&apikey=" + apiKey;

            try {
                ResponseEntity<CurrencyExchangeResponse> response = restTemplate.exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<CurrencyExchangeResponse>() {});

                if (response.getStatusCode() == HttpStatus.OK) {
                    CurrencyExchangeResponse responseBody = response.getBody();
                    Double rate = responseBody.getRate();
                    CurrencyExchange exchangeRate = c;
                    exchangeRate.setFirstCurrency(c.getFirstCurrency());
                    exchangeRate.setSecondCurrency(c.getSecondCurrency());
                    exchangeRate.setRate(rate);
                    currencyExchangeRepository.save(exchangeRate);
                }
            } catch (RestClientException e) {
                e.printStackTrace();
            }
        }




    }

    public ResponseEntity<List<CurrencyExchange>> getCurrencyExchanges(){
        List<CurrencyExchange> currencyExchanges = currencyExchangeRepository.findAll();
        if (currencyExchanges.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(currencyExchanges);
        }

    }

    public ResponseEntity<Double> getRate(String c1, String c2) {

        CurrencyExchange exchangeRate =
                currencyExchangeRepository.getCurrencyExchangeRateByFirstCurrencyAndSecondCurrency(c1, c2);

        if (exchangeRate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(currencyExchangeRepository
                            .getCurrencyExchangeRateByFirstCurrencyAndSecondCurrency(c1, c2)
                            .getRate());
        }

    }



}
