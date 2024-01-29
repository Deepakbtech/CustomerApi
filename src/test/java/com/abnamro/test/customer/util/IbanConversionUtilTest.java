package com.abnamro.test.customer.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.abnamro.test.customer.exception.ConversionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IbanConversionUtilTest {

  @Test
  public void shouldGetIBAN() {
    assertEquals("NL07ABNA1000000982", IbanConversionUtil.toIban("1000000982"));
    assertEquals("NL20ABNA0100000992", IbanConversionUtil.toIban("100000992"));
    assertEquals("NL63ABNA0649090349", IbanConversionUtil.toIban("649090349"));
  }

  @Test
  public void shouldGetIbanAndBack() {
    String accountNumberWith10chars = "1000000982";
    assertEquals(
        accountNumberWith10chars,
        IbanConversionUtil.toBban(IbanConversionUtil.toIban(accountNumberWith10chars)));

    String accountNumberWith9chars = "128928433";
    assertEquals(
        accountNumberWith9chars,
        IbanConversionUtil.toBban(IbanConversionUtil.toIban(accountNumberWith9chars)));
  }

  @Test
  public void shouldThrowExceptionForBBANLessThan9Digits() {
    assertThrows(ConversionException.class, () -> IbanConversionUtil.toIban("000982"));
  }

  @Test
  public void shouldThrowExceptionForNullBban() {
    assertThrows(NullPointerException.class, () -> IbanConversionUtil.toIban(null));
  }

  @Test
  public void shouldThrowExceptionForBBANGreaterThan10Digits() {
    assertThrows(ConversionException.class, () -> IbanConversionUtil.toIban("1201212000982"));
  }

  @Test
  public void shouldThrowExceptionForBBANContainsCharacter() {
    assertThrows(ConversionException.class, () -> IbanConversionUtil.toIban("1201210009R"));
  }

  @Test
  public void shouldThrowExceptionForInvalidCharacterBBAN() {
    assertThrows(ConversionException.class, () -> IbanConversionUtil.toIban("12012100%9"));
  }

  @Test
  public void shouldGetBBAN() {
    assertEquals("1000000982", IbanConversionUtil.toBban("NL49RABO1000000982"));
    assertEquals("1234567890", IbanConversionUtil.toBban("AA0001234567890"));
  }

  @Test
  public void shouldThrowExceptionForIncorrectIbanLength() {
    assertThrows(ConversionException.class, () -> IbanConversionUtil.toBban("NO1000981"));
  }

  @Test
  public void shouldThrowExceptionForNullIban() {
    assertThrows(NullPointerException.class, () -> IbanConversionUtil.toBban(null));
  }

  @Test
  public void shouldGenerateBban(){
    var bban = IbanConversionUtil.generateBban(1L);
    assertNotNull(bban);
    assertEquals("0000000001", bban);
  }
}
