package com.example.moneytransfer.repository;

import com.example.moneytransfer.objects.Transfer;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransferRepository {
  private final Map<String, Transfer> transferMap;

  public TransferRepository() {
    this.transferMap = new ConcurrentHashMap<>();
  }

  public String add(Transfer transfer) {
    String operationId = transfer.getOperationId();
    transferMap.put(operationId, transfer);
    return operationId;
  }

  public void remove(String operationId) {
    transferMap.remove(operationId);
  }

  public Optional<Transfer> get(String operationId) {
    return Optional.ofNullable(transferMap.get(operationId));
  }
}
