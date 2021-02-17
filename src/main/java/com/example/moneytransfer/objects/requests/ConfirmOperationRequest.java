package com.example.moneytransfer.objects.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfirmOperationRequest {
  private String operationId;
  private String code;
}
