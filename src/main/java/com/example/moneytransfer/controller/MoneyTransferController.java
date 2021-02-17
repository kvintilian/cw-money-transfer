package com.example.moneytransfer.controller;

import com.example.moneytransfer.objects.requests.ConfirmOperationRequest;
import com.example.moneytransfer.objects.requests.TransferRequest;
import com.example.moneytransfer.objects.responses.FailTransferResponse;
import com.example.moneytransfer.service.MoneyTransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoneyTransferController {
  private final MoneyTransferService moneyTransferService;

  public MoneyTransferController(MoneyTransferService moneyTransferService) {
    this.moneyTransferService = moneyTransferService;
  }

  @PostMapping("/transfer")
  public ResponseEntity<Object> transfer(@RequestBody TransferRequest transferRequest) {
    return moneyTransferService.transfer(transferRequest);
  }

  @PostMapping("/confirmOperation")
  public ResponseEntity<Object> confirmOperation(@RequestBody ConfirmOperationRequest confirmOperationRequest) {
    return moneyTransferService.confirmOperation(confirmOperationRequest);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Object> handleException(RuntimeException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FailTransferResponse.builder().message(e.getMessage()).build());
  }
}
