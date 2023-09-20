package kz.solva.controller;

import kz.solva.model.entity.CurrencyExchange;
import kz.solva.service.currencyExchangeService.CurrencyExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/exchange_rate")
@RequiredArgsConstructor
public class CurrencyExchangeRestController {

    private final CurrencyExchangeService exchangeService;


    @GetMapping
    public ResponseEntity<List<CurrencyExchange>> currencyExchangeRateList(){
        return exchangeService.getCurrencyExchanges();
    }

    @GetMapping(value = "{c1}/{c2}")
    public ResponseEntity<Double> getRate(@PathVariable(name = "c1") String c1, @PathVariable(name = "c2") String c2) {
        return exchangeService.getRate(c1, c2);
    }


}
