package com.example.moneytransfer.service;

import com.example.moneytransfer.objects.*;
import com.example.moneytransfer.objects.requests.ConfirmOperationRequest;
import com.example.moneytransfer.objects.requests.TransferRequest;
import com.example.moneytransfer.objects.responses.FailTransferResponse;
import com.example.moneytransfer.objects.responses.GoodTransferResponse;
import com.example.moneytransfer.repository.TransferRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.moneytransfer.commons.Luhn;

@Service
public class MoneyTransferService {

  private final String MSG_FROM_CARD_NOT_VALID = "From card number not valid";
  private final String MSG_TO_CARD_NOT_VALID = "To card number not valid";
  private final String MSG_AMOUNT_NOT_VALID = "Amount value not valid";
  private final String MSG_INPUT_NOT_VALID = "Input data not valid";
  private final String MSG_CONFIRM_FAIL = "Confirm data is lost";

  private final TransferRepository transferRepository;

  public MoneyTransferService(TransferRepository transferRepository) {
    this.transferRepository = transferRepository;
  }

  public ResponseEntity<Object> transfer(TransferRequest transferRequest) {
    if (transferRequest == null)
      return ResponseEntity.badRequest().body(FailTransferResponse.builder().message(MSG_INPUT_NOT_VALID).build());
    if (!Luhn.check(transferRequest.getCardFromNumber()))
      return ResponseEntity.badRequest().body(FailTransferResponse.builder().message(MSG_FROM_CARD_NOT_VALID).build());
    if (!Luhn.check(transferRequest.getCardToNumber()))
      return ResponseEntity.badRequest().body(FailTransferResponse.builder().message(MSG_TO_CARD_NOT_VALID).build());
    if (transferRequest.getAmount().getValue() <= 0)
      return ResponseEntity.badRequest().body(FailTransferResponse.builder().message(MSG_AMOUNT_NOT_VALID).build());

    String operationId = transferRepository.add(Transfer.builder().transferRequest(transferRequest).build());
    return ResponseEntity.ok(GoodTransferResponse.builder().operationId(operationId).build());
  }

  public ResponseEntity<Object> confirmOperation(ConfirmOperationRequest confirmOperationRequest) {
    if (confirmOperationRequest == null)
      return ResponseEntity.badRequest().body(FailTransferResponse.builder().message(MSG_INPUT_NOT_VALID).build());

    Transfer transfer = transferRepository.get(confirmOperationRequest.getOperationId()).orElseThrow(() -> new RuntimeException(MSG_CONFIRM_FAIL));
    String operationId = transfer.getOperationId();
    transferRepository.remove(operationId);

    return ResponseEntity.ok(GoodTransferResponse.builder().operationId(operationId).build());
  }

}
