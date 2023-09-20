package kz.solva.service.currencyExchangeService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CurrencyExchangeRateUpdater {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Scheduled(cron = "0 0 0,12 * * *") // Запускать в 00:00 и в 12:00
    public void updateCurrencyExchangeRatesDaily() {
        currencyExchangeService.updateCurrencyExchangeRates();
    }
}
