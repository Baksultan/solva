package kz.solva.service.limitService;

import kz.solva.model.entity.Customer;
import kz.solva.model.entity.Limit;
import kz.solva.model.requestModel.LimitRequest;
import kz.solva.repository.CustomerRepository;
import kz.solva.repository.LimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LimitService {

    private final LimitRepository limitRepository;
    private final CustomerRepository customerRepository;

    public Limit findLastLimitForCustomer(Long customerId) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/debug";
        String username = "debug";
        String password = "debug";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String sql = "SELECT * FROM customer_limit WHERE customer_id = ? ORDER BY limit_date DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, customerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Limit lastLimit = new Limit();
                lastLimit.setId(resultSet.getLong("id"));
                lastLimit.setProductLimit(resultSet.getBigDecimal("product_limit"));
                lastLimit.setServiceLimit(resultSet.getBigDecimal("service_limit"));
                Timestamp timestamp = resultSet.getTimestamp("limit_date");
                if (timestamp != null) {
                    Instant instant = timestamp.toInstant();
                    ZoneId zoneId = ZoneId.systemDefault();
                    ZonedDateTime zonedDateTime = instant.atZone(zoneId);
                    lastLimit.setLimitDate(zonedDateTime);
                }

                return lastLimit;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResponseEntity<List<Limit>> getLimitsByBankAcc(String bankAcc) {
        Customer customer = customerRepository.getCustomersByBankAccount(bankAcc);
        List<Limit> limits = customer.getLimit();

        if (!limits.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(limits);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<Limit> addNewLimit(LimitRequest limitRequest) {

        if (limitRequest == null || limitRequest.getBankAcc() == null
                || limitRequest.getService_limit() == null || limitRequest.getProduct_limit() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Limit limit = new Limit();
        limit.setServiceLimit(limitRequest.getService_limit());
        limit.setProductLimit(limitRequest.getProduct_limit());
        limit.setCustomer(customerRepository.getCustomersByBankAccount(limitRequest.getBankAcc()));
        limit.setLimitDate(ZonedDateTime.now());

        return ResponseEntity.status(HttpStatus.OK).body(limitRepository.save(limit));
    }

}
