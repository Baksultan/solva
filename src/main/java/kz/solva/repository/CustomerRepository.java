package kz.solva.repository;

import kz.solva.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer getCustomersByBankAccount(String bankAccount);

}
