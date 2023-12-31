package kz.solva.controller;

import kz.solva.model.entity.Customer;
import kz.solva.model.requestModel.CustomerRequest;
import kz.solva.model.requestModel.CustomerUpdateRequest;
import kz.solva.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class CustomerRestController {

    private final CustomerService customerService;

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(name = "id") Long id) {
        return customerService.getCustomer(id);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/getCustomer/{bankAcc}")
    public ResponseEntity<Customer> getCustomerByBankAcc(@PathVariable(name = "bankAcc") String bankAcc) {
        return customerService.getCustomerByBankAcc(bankAcc);
    }

    @PostMapping(value = "/addCustomer")
    public ResponseEntity<Customer> addCustomer(@RequestBody CustomerRequest customer) {
        return customerService.addCustomer(customer);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerUpdateRequest customer) {
        return customerService.updateCustomer(customer);
    }


}
