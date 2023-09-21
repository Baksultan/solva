package kz.solva.service.customerService;

import kz.solva.model.entity.Customer;
import kz.solva.model.entity.Limit;
import kz.solva.model.requestModel.CustomerRequest;
import kz.solva.repository.CustomerRepository;
import kz.solva.repository.LimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final LimitRepository limitRepository;


    public ResponseEntity<Customer> getCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        }

    }

    public ResponseEntity<Customer> getCustomerByBankAcc(String bankAcc) {
        Customer customer = customerRepository.getCustomersByBankAccount(bankAcc);

        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        }
    }

    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(customerRepository.findAll());
    }

    public ResponseEntity<Customer> addCustomer(CustomerRequest c) {
        if (customerRepository.getCustomersByBankAccount(c.getBank_account()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            Customer customer = new Customer();
            customer.setBankAccount(c.getBank_account());
            Limit limit = new Limit();

            if (c.getProduct_limit() != null) {
                limit.setProductLimit(c.getProduct_limit());
            } else {
                limit.setProductLimit(new BigDecimal("1000"));
            }

            if (c.getService_limit() != null) {
                limit.setServiceLimit(c.getService_limit());
            } else {
                limit.setServiceLimit(new BigDecimal("1000"));
            }

            limit.setLimitDate(ZonedDateTime.now());
            limit.setCustomer(customer);

            customerRepository.save(customer);
            limitRepository.save(limit);

            return ResponseEntity.status(HttpStatus.OK).body(customer);
        }
    }

    public ResponseEntity<Customer> updateCustomer(Customer customer) {

        if (customer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(customerRepository.save(customer));
    }


}
