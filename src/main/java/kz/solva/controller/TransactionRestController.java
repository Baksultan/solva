package kz.solva.controller;

import kz.solva.model.entity.Transaction;
import kz.solva.model.requestModel.TransactionRequest;
import kz.solva.model.responseModel.TransactionResponse;
import kz.solva.service.transactionService.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transaction")
@RequiredArgsConstructor
public class TransactionRestController {

    private final TransactionService transactionService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable(name = "id") Long id) {
        return transactionService.getTransaction(id);
    }

    @GetMapping(value = "/getByAcc/{customerAcc}")
    public ResponseEntity<List<Transaction>> getTransactionsByBankAcc(@PathVariable(name = "customerAcc") String customerAcc) {
        return transactionService.getTransactionsByBankAcc(customerAcc);
    }

    @PostMapping(value = "/addTransaction")
    public ResponseEntity<TransactionResponse> addTransaction(@RequestBody TransactionRequest transaction) {
        return transactionService.addTransaction(transaction);
    }


}
