package kz.solva.model.requestModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private String accountFromBankAcc;
    private String accountToBackAcc;
    private String type;
    private String currencyShortname;
    private BigDecimal sum;

}
