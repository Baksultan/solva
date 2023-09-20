package kz.solva.service.clientService;

import kz.solva.model.entity.Customer;
import kz.solva.model.entity.Transaction;
import kz.solva.model.responseModel.TransactionResponse;
import kz.solva.repository.TransactionRepository;
import kz.solva.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final CustomerService customerService;

    private final TransactionRepository transactionRepository;

    public ResponseEntity<List<TransactionResponse>> getClientTransactions(String bankAcc) {
        Customer customer = customerService.getCustomerByBankAcc(bankAcc).getBody();
        List<TransactionResponse> transactions = new ArrayList<>();

        for (Transaction t: transactionRepository.getTransactionByAccountFrom(customer)) {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setId(t.getId());
            transactionResponse.setAccountFrom(t.getAccountFrom().getBankAccount());
            transactionResponse.setAccountTo(t.getAccountTo().getBankAccount());
            transactionResponse.setType(t.getType());
            transactionResponse.setCurrencyShortname(t.getCurrencyShortname());
            transactionResponse.setSum(t.getSum());
            transactionResponse.setExpenseCategory(t.getExpenseCategory());
            transactionResponse.setDatetime(t.getDatetime());

            transactions.add(transactionResponse);
        }

        for (Transaction t: transactionRepository.getTransactionByAccountTo(customer)) {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setId(t.getId());
            transactionResponse.setAccountFrom(t.getAccountFrom().getBankAccount());
            transactionResponse.setAccountTo(t.getAccountTo().getBankAccount());
            transactionResponse.setType(t.getType());
            transactionResponse.setCurrencyShortname(t.getCurrencyShortname());
            transactionResponse.setSum(t.getSum());
            transactionResponse.setExpenseCategory(t.getExpenseCategory());
            transactionResponse.setDatetime(t.getDatetime());

            transactions.add(transactionResponse);
        }

        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }


    public ResponseEntity<List<TransactionResponse>> getClientTransactions(String bankAcc, Boolean expenseCategory) {

        Customer customer = customerService.getCustomerByBankAcc(bankAcc).getBody();
        List<TransactionResponse> transactions = new ArrayList<>();

        for (Transaction t: transactionRepository.getTransactionByAccountFromAndExpenseCategory(customer, expenseCategory)) {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setId(t.getId());
            transactionResponse.setAccountFrom(t.getAccountFrom().getBankAccount());
            transactionResponse.setAccountTo(t.getAccountTo().getBankAccount());
            transactionResponse.setType(t.getType());
            transactionResponse.setCurrencyShortname(t.getCurrencyShortname());
            transactionResponse.setSum(t.getSum());
            transactionResponse.setExpenseCategory(t.getExpenseCategory());
            transactionResponse.setDatetime(t.getDatetime());

            transactions.add(transactionResponse);
        }

        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }
}
