package com.example.moneytransfer.objects;

import com.example.moneytransfer.objects.requests.TransferRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transfer {
  private String operationId;
  private TransferRequest transferRequest;

  private int transferCommission = 1;

  public String getOperationId() {
    if (operationId == null || operationId.isEmpty())
      operationId = UUID.randomUUID().toString();
    return operationId;
  }

  public String getLog() {
    return String.format("OPERATION_ID: %s, FROM_CARD: %s, TO_CARD: %s, AMOUNT: %s, CURR: %s",
            operationId,
            transferRequest.getCardFromNumber(),
            transferRequest.getCardToNumber(),
            transferRequest.getAmount().getValue().toString(),
            transferRequest.getAmount().getCurrency());
  }
}
