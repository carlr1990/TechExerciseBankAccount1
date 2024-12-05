package tech.excercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.excercise.service.BankAccountService;

@RestController
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance() {
        double balance = bankAccountService.retrieveBalance();
        return ResponseEntity.ok(balance);
    }
}
