package com.example.moneytransfer.commons;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LuhnTest {

  @Test
  void checkTrue() {
    Assertions.assertTrue(Luhn.check("4111 1111 4555 1142"));
  }

  @Test
  void checkFalse() {
    Assertions.assertFalse(Luhn.check("5111 1111 4555 1142"));
  }
}
