package kz.solva.model.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @ManyToOne
   @JoinColumn(name = "account_from_id") // Указываем имя столбца
   private Customer accountFrom;

   @ManyToOne
   @JoinColumn(name = "account_to_id") // Указываем имя столбца
   private Customer accountTo;
   private String type;
   private String currencyShortname;
   private BigDecimal sum;
   private Boolean expenseCategory;
   private ZonedDateTime datetime;
}
