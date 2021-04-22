package com.example.moneytransfer.service;

import com.example.moneytransfer.objects.*;
import com.example.moneytransfer.objects.requests.ConfirmOperationRequest;
import com.example.moneytransfer.objects.requests.TransferRequest;
import com.example.moneytransfer.objects.responses.FailTransferResponse;
import com.example.moneytransfer.objects.responses.GoodTransferResponse;
import com.example.moneytransfer.repository.TransferRepository;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.moneytransfer.commons.Luhn;

import java.math.BigInteger;

@Log
@Service
public class MoneyTransferService {

  private final String MSG_FROM_CARD_NOT_VALID = "Ваша карта недействительна, проверьте введённый номер!";
  private final String MSG_TO_CARD_NOT_VALID = "Карта получателя недействительна, проверьте введённый номер!";
  private final String MSG_AMOUNT_NOT_VALID = "Отсутствую данные о переводе!";
  private final String MSG_INPUT_NOT_VALID = "Неверные входные данные!";
  private final String MSG_CONFIRM_FAIL = "Ошибка данных, транзакции не существует!";

  private final TransferRepository transferRepository;

  public MoneyTransferService(TransferRepository transferRepository) {
    this.transferRepository = transferRepository;
  }

  public ResponseEntity<Object> transfer(TransferRequest transferRequest) {
    if (transferRequest == null)
      return ResponseEntity.badRequest().body(FailTransferResponse.builder().message(MSG_INPUT_NOT_VALID).build());
    if (!Luhn.checkCardNumber(transferRequest.getCardFromNumber()))
      return ResponseEntity.badRequest().body(FailTransferResponse.builder().message(MSG_FROM_CARD_NOT_VALID).build());
    if (!Luhn.checkCardNumber(transferRequest.getCardToNumber()))
      return ResponseEntity.badRequest().body(FailTransferResponse.builder().message(MSG_TO_CARD_NOT_VALID).build());
    if (transferRequest.getAmount().getValue().compareTo(BigInteger.ZERO) <= 0)
      return ResponseEntity.badRequest().body(FailTransferResponse.builder().message(MSG_AMOUNT_NOT_VALID).build());

    String operationId = transferRepository.add(Transfer.builder().transferRequest(transferRequest).build());
    log.info("NEW TRANSFER: " + transferRepository.get(operationId).orElseThrow(() -> new RuntimeException(MSG_CONFIRM_FAIL)).getLog());
    return ResponseEntity.ok(GoodTransferResponse.builder().operationId(operationId).build());
  }

  public ResponseEntity<Object> confirmOperation(ConfirmOperationRequest confirmOperationRequest) {
    if (confirmOperationRequest == null)
      return ResponseEntity.badRequest().body(FailTransferResponse.builder().message(MSG_INPUT_NOT_VALID).build());

    String operationId = confirmOperationRequest.getOperationId();

    if (transferRepository.contains(operationId)) {
      transferRepository.remove(operationId);
      log.info("TRANSFER ACCEPTED: OPERATION_ID = " + operationId + ", CODE = " + confirmOperationRequest.getCode());
      return ResponseEntity.ok(GoodTransferResponse.builder().operationId(operationId).build());
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FailTransferResponse.builder().message(MSG_CONFIRM_FAIL));
    }
  }

}
