package com.example.moneytransfer;

import com.example.moneytransfer.objects.requests.TransferRequest;
import com.example.moneytransfer.repository.TransferRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneytransferIntegrationTests {
  @Autowired
  TestRestTemplate restTemplate;
//  public static GenericContainer<?> transferService = new GenericContainer<>("moneytransfer").withExposedPorts(5500);

  @Mock
  TransferRepository transferRepositoryMock;

  @BeforeAll
  public static void setUp() {
//    transferService.start();
  }

  @Test
  void contextLoads() {
    assertNotNull(transferRepositoryMock);
    TransferRequest transferRequest = TransferRequest.builder()
            .cardFromCVV("737")
            .cardFromValidTill("10/24")
            .cardFromNumber("4111111145551142")
            .cardToNumber("4988438843884305")
            .amount(
                    TransferRequest.Amount.builder()
                            .currency("RUR")
                            .value(BigInteger.valueOf(45000))
                            .build()
            )
            .build();

//    ResponseEntity<Object> forEntity = restTemplate.postForEntity(
//            "http://localhost:" + transferService.getMappedPort(5500),
//            transferRequest,
//            Object.class);
//    assertEquals(HttpStatus.OK, forEntity.getStatusCode());
//    System.out.println(forEntity.getBody());
  }

}
