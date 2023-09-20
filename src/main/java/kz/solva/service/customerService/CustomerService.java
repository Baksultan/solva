package kz.solva.service.customerService;

import kz.solva.model.entity.Customer;
import kz.solva.model.entity.Limit;
import kz.solva.model.requestModel.CustomerRequest;
import kz.solva.repository.CustomerRepository;
import kz.solva.repository.LimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LimitRepository limitRepository;

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
        if (customerRepository.getCustomersByBankAccount(c.getBankAcc()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            Customer customer = new Customer();
            customer.setBankAccount(c.getBankAcc());
            Limit limit = new Limit();

            if (c.getProductLimit() != null) {
                limit.setProductLimit(c.getProductLimit());
            } else {
                limit.setProductLimit(new BigDecimal("1000"));
            }

            if (c.getServiceLimit() != null) {
                limit.setServiceLimit(c.getServiceLimit());
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

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

}
