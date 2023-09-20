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
public class LimitRequest {
    private String bankAcc;
    private BigDecimal product_limit;
    private BigDecimal service_limit;
}
