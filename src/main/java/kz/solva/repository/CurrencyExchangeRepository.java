package kz.solva.repository;

import kz.solva.model.entity.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
    public CurrencyExchange getCurrencyExchangeRateByFirstCurrencyAndSecondCurrency(String c1, String c2);
}
