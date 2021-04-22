package com.example.moneytransfer.commons;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LuhnTest {

  @Test
  void checkTrue() {
    Assertions.assertTrue(Luhn.checkCardNumber("4111 1111 4555 1142"));
  }

  @Test
  void checkFalse() {
    Assertions.assertFalse(Luhn.checkCardNumber("5111 1111 4555 1142"));
  }
}
