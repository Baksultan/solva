package kz.solva.repository;

import kz.solva.model.entity.Customer;
import kz.solva.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction getTransactionById(Long id);

    List<Transaction> getTransactionByAccountFrom(Customer customer);

    List<Transaction> getTransactionByAccountTo(Customer customer);

    List<Transaction> getTransactionByAccountFromAndExpenseCategory(Customer customer, Boolean expenseCategory);
}
