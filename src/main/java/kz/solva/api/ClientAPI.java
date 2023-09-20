package kz.solva.api;

import kz.solva.model.entity.Limit;
import kz.solva.model.requestModel.LimitRequest;
import kz.solva.model.responseModel.TransactionResponse;
import kz.solva.service.clientService.ClientService;
import kz.solva.service.limitService.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/client")
@RequiredArgsConstructor
public class ClientAPI {

    private final ClientService clientService;

    private final LimitService limitService;

    @GetMapping(value = "/getAllTransactions/{clientBankAcc}")
    public ResponseEntity<List<TransactionResponse>> getAllTransactions(@PathVariable(name = "clientBankAcc") String clientBankAcc) {
        return clientService.getClientTransactions(clientBankAcc);
    }

    @GetMapping(value = "/getTransactions/{clientBankAcc}")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable(name = "clientBankAcc") String clientBankAcc) {
        return clientService.getClientTransactions(clientBankAcc, true);
    }

    @GetMapping(value = "/getLimits/{userBankAcc}")
    public ResponseEntity<List<Limit>> getLimit(@PathVariable(name = "userBankAcc") String userBankAcc) {
        return limitService.getLimitsByBankAcc(userBankAcc);
    }

    @PostMapping(value = "/addNewLimit")
    public ResponseEntity<Limit> addNewLimit(@RequestBody LimitRequest limit) {
        return limitService.addNewLimit(limit);
    }


}
