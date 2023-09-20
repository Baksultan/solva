package kz.solva.model.responseModel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private String accountFrom;
    private String accountTo;
    private String type;
    private String currencyShortname;
    private BigDecimal sum;
    private Boolean expenseCategory;
    private ZonedDateTime datetime;

}
