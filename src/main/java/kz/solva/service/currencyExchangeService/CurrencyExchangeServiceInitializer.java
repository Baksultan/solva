package kz.solva.service.currencyExchangeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CurrencyExchangeServiceInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    public CurrencyExchangeServiceInitializer(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        currencyExchangeService.updateCurrencyExchangeRates();
    }
}
