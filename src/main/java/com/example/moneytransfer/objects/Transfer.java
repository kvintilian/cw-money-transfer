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

  public String getOperationId() {
    if (operationId == null || operationId.isEmpty())
      operationId = UUID.randomUUID().toString();
    return operationId;
  }
}
