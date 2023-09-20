package kz.solva.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "customer_limit")
@Getter
@Setter
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_limit")
    private BigDecimal productLimit;
    @Column(name = "service_limit")
    private BigDecimal serviceLimit;
    @Column(name = "limit_date")
    private ZonedDateTime limitDate; //date when limit has been placed
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

}
