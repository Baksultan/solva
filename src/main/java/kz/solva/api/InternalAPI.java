package kz.solva.api;

import kz.solva.model.entity.Customer;
import kz.solva.model.requestModel.CustomerRequest;
import kz.solva.model.requestModel.TransactionRequest;
import kz.solva.model.responseModel.TransactionResponse;
import kz.solva.service.customerService.CustomerService;
import kz.solva.service.transactionService.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/internal")
@RequiredArgsConstructor
public class InternalAPI {

    private final TransactionService transactionService;

    private final CustomerService customerService;

    @PostMapping(value = "/addTransaction")
    public ResponseEntity<TransactionResponse> addTransaction(@RequestBody TransactionRequest transaction) {
        return transactionService.addTransaction(transaction);
    }

    @GetMapping(value = "/getTransaction/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable(name = "transactionId") Long id) {
        return transactionService.getTransaction(id);
    }

    @PostMapping(value = "/addCustomer")
    public ResponseEntity<Customer> addNewCustomer(@RequestBody CustomerRequest customer) {
        return customerService.addCustomer(customer);
    }

    @GetMapping(value = "/getAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/getCustomerById/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(name = "id") Long id) {
        return customerService.getCustomer(id);
    }

    @GetMapping(value = "/getCustomer/{bankAcc}")
    public ResponseEntity<Customer> getCustomerByBankAcc(@PathVariable(name = "bankAcc") String bankAcc) {
        return customerService.getCustomerByBankAcc(bankAcc);
    }

}
