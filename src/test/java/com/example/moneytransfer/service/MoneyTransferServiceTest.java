package com.example.moneytransfer.service;

import com.example.moneytransfer.objects.requests.ConfirmOperationRequest;
import com.example.moneytransfer.objects.requests.TransferRequest;
import com.example.moneytransfer.objects.responses.GoodTransferResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;

@SpringBootTest
public class MoneyTransferServiceTest {

  @Autowired
  private MoneyTransferService moneyTransferService;

  @Test
  void transferGood() {
    TransferRequest transferRequest = Mockito.mock(TransferRequest.class);
    Mockito.when(transferRequest.getCardFromNumber()).thenReturn("4111111145551142");
    Mockito.when(transferRequest.getCardToNumber()).thenReturn("4988438843884305");
    Mockito.when(transferRequest.getAmount()).thenReturn(TransferRequest.Amount.builder().value(BigInteger.TEN).build());

    ResponseEntity<Object> transferResponse = moneyTransferService.transfer(transferRequest);
    Assertions.assertEquals(HttpStatus.OK, transferResponse.getStatusCode());
    Object transferBody = transferResponse.getBody();
    Assertions.assertTrue((transferBody instanceof GoodTransferResponse));
    GoodTransferResponse goodTransferResponse = (GoodTransferResponse) transferBody;
    String operationId = goodTransferResponse.getOperationId();

    ConfirmOperationRequest confirmOperationRequest = Mockito.mock(ConfirmOperationRequest.class);
    Mockito.when(confirmOperationRequest.getOperationId()).thenReturn(operationId);
    ResponseEntity<Object> objectResponseEntity = moneyTransferService.confirmOperation(confirmOperationRequest);
    Assertions.assertEquals(HttpStatus.OK, objectResponseEntity.getStatusCode());
  }

  @Test
  void transferFail() {
    TransferRequest transferRequest = Mockito.mock(TransferRequest.class);
    Mockito.when(transferRequest.getCardFromNumber()).thenReturn("1111111145551142");

    ResponseEntity<Object> transferResponse = moneyTransferService.transfer(transferRequest);
    Assertions.assertEquals(HttpStatus.BAD_REQUEST, transferResponse.getStatusCode());
  }
}
