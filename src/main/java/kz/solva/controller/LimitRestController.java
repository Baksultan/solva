package kz.solva.controller;

import kz.solva.model.entity.Limit;
import kz.solva.service.limitService.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/limit")
@RequiredArgsConstructor
public class LimitRestController {

    private final LimitService limitService;

    @GetMapping(value = "{userBankAcc}")
    public ResponseEntity<List<Limit>> getLimits(@PathVariable(name = "userBankAcc") String userBankAcc) {
        return limitService.getLimitsByBankAcc(userBankAcc);
    }



}
