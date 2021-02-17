package com.example.moneytransfer.objects.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransferRequest {
  private String cardFromNumber;
  private String cardFromValidTill;
  private String cardFromCVV;
  private String cardToNumber;
  private Amount amount;

  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  public static class Amount {
    private String currency;
    private int value;
  }
}



