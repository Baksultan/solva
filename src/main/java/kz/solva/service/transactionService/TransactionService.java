package kz.solva.service.transactionService;

import kz.solva.model.entity.Customer;
import kz.solva.model.entity.Limit;
import kz.solva.model.entity.Transaction;
import kz.solva.model.requestModel.TransactionRequest;
import kz.solva.model.responseModel.TransactionResponse;
import kz.solva.repository.CustomerRepository;
import kz.solva.repository.TransactionRepository;
import kz.solva.service.currencyExchangeService.ConvertService;
import kz.solva.service.limitService.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ConvertService convertService;

    @Autowired
    private LimitService limitService;


    public ResponseEntity<TransactionResponse> addTransaction(TransactionRequest transactionRequest) {

        Customer c1 = customerRepository.getCustomersByBankAccount(transactionRequest.getAccountFromBankAcc());
        Customer c2 = customerRepository.getCustomersByBankAccount(transactionRequest.getAccountToBackAcc());

        if  (c1 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if (c2 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Transaction transaction = new Transaction();
        transaction.setAccountFrom(c1);
        transaction.setAccountTo(c2);
        transaction.setType(transactionRequest.getType());
        transaction.setSum(transactionRequest.getSum());
        transaction.setCurrencyShortname(transactionRequest.getCurrencyShortname());
        transaction.setDatetime(ZonedDateTime.now());

        Limit lastesLimit = limitService.findLastLimitForCustomer(transaction.getAccountFrom().getId());

        if (transaction.getType().equals("service")) {

            BigDecimal bigOne = getTransactionsSum(transaction.getAccountFrom().getBankAccount(), transaction.getType());
            bigOne = bigOne.add(convertService.convertToUSD(transaction.getCurrencyShortname(), transaction.getSum()));

            if (lastesLimit.getServiceLimit().subtract(bigOne).compareTo(new BigDecimal("0")) < 0) {
                transaction.setExpenseCategory(true);
            } else {
                transaction.setExpenseCategory(false);
            }

        } else if (transaction.getType().equals("product")) {

            BigDecimal bigOne = getTransactionsSum(transaction.getAccountFrom().getBankAccount(), transaction.getType());
            bigOne = bigOne.add(convertService.convertToUSD(transaction.getCurrencyShortname(), transaction.getSum()));

            if (lastesLimit.getProductLimit().subtract(bigOne).compareTo(new BigDecimal("0")) < 0) {
                transaction.setExpenseCategory(true);
            } else {
                transaction.setExpenseCategory(false);
            }

        }   else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        transactionRepository.save(transaction);

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setAccountFrom(transaction.getAccountFrom().getBankAccount());
        transactionResponse.setAccountTo(transaction.getAccountTo().getBankAccount());
        transactionResponse.setType(transaction.getType());
        transactionResponse.setSum(transaction.getSum());
        transactionResponse.setCurrencyShortname(transaction.getCurrencyShortname());
        transactionResponse.setExpenseCategory(transaction.getExpenseCategory());
        transactionResponse.setDatetime(transaction.getDatetime());

        return ResponseEntity.status(HttpStatus.OK).body(transactionResponse);
    }

    public BigDecimal getTransactionsSum(String bankAcc, String type) {
        List<Transaction> transactions = transactionRepository.
                getTransactionByAccountFrom(customerRepository.getCustomersByBankAccount(bankAcc));
        BigDecimal sum = new BigDecimal("0");
        for (Transaction t: transactions) {
            if (t.getType().equals(type)) {
                sum = sum.add(convertService.convertToUSD(t.getCurrencyShortname(), t.getSum()));
            }
        }
        return sum;
    }

    public ResponseEntity<List<Transaction>> getTransactionsByBankAcc(String bankAcc) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(transactionRepository.getTransactionByAccountFrom(customerRepository.getCustomersByBankAccount(bankAcc)));
        transactions.addAll(transactionRepository.getTransactionByAccountTo(customerRepository.getCustomersByBankAccount(bankAcc)));
        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(transactions);
        }
    }

    public ResponseEntity<TransactionResponse> getTransaction(Long id) {

        Transaction transaction = transactionRepository.getTransactionById(id);
        TransactionResponse transactionResponse = new TransactionResponse();

        transactionResponse.setId(transaction.getId());
        transactionResponse.setAccountFrom(transaction.getAccountFrom().getBankAccount());
        transactionResponse.setAccountTo(transaction.getAccountTo().getBankAccount());
        transactionResponse.setType(transaction.getType());
        transactionResponse.setSum(transaction.getSum());
        transactionResponse.setCurrencyShortname(transaction.getCurrencyShortname());
        transactionResponse.setExpenseCategory(transaction.getExpenseCategory());
        transactionResponse.setDatetime(transaction.getDatetime());

        return ResponseEntity.status(HttpStatus.OK).body(transactionResponse);
    }



}
