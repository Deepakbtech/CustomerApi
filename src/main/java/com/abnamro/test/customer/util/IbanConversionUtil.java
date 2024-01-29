package com.abnamro.test.customer.util;

import com.abnamro.test.customer.exception.ConversionException;
import java.math.BigInteger;
import java.util.Objects;
import java.util.regex.Pattern;

public class IbanConversionUtil {

  private static final int BBAN_LENGTH_9 = 9;
  private static final int MOD_97 = 97;
  private static final int VALUE_98 = 98;
  private static final int VALUE_10 = 10;
  private static final String BANK_NAME = "ABNA";
  private static final String COUNTRY = "NL";
  private static final int ACCOUNT_NUMBER_LENGTH = 10;

  private static final Pattern PATTERN_IBAN =
      Pattern.compile("[A-Z]{2}[0-9]{2}([a-zA-Z0-9]{11,30})");
  private static final Pattern PATTERN_BBAN = Pattern.compile("[0-9]{9,10}+");
  private static final Pattern LEADING_ZEROS = Pattern.compile("^0+(?!$)");

  private static final Pattern FOUR_DIGIT_GROUP_WITH_SPACES = Pattern.compile("(.{4})");

  private IbanConversionUtil() {
  }

  public static String toIban(final String bban) {
    Objects.requireNonNull(bban, "Requested BBAN should not be null");
    if (!matchesBbanPattern(bban)) {
      throw new ConversionException("Bban to iban conversion failed. Could not convert " + bban);
    }
    String bbanWith10Digits = bban.length() == BBAN_LENGTH_9 ? "0" + bban : bban;
    String rearrangedIban = BANK_NAME + bbanWith10Digits + COUNTRY + "00";
    final StringBuilder converted = new StringBuilder();
    for (final char charDigit : rearrangedIban.toCharArray()) {
      if (Character.isDigit(charDigit)) {
        converted.append(charDigit);
      } else {
        int charToInt = (charDigit - 'A') + VALUE_10;
        converted.append(charToInt);
      }
    }
    BigInteger convertedInt = new BigInteger(converted.toString());
    BigInteger mod = convertedInt.mod(BigInteger.valueOf(MOD_97));
    String checkDigit = BigInteger.valueOf(VALUE_98).subtract(mod).toString();
    return COUNTRY
        + "00".substring(checkDigit.length())
        + checkDigit
        + BANK_NAME
        + bbanWith10Digits;
  }

  public static String toBban(final String iban) {
    Objects.requireNonNull(iban, "Requested IBAN should not be null");
    if (!matchesIbanPattern(iban)) {
      throw new ConversionException("Iban to bban conversion failed. Could not convert " + iban);
    }
    String accountNumber = iban.substring(iban.length() - ACCOUNT_NUMBER_LENGTH);
    return removeLeadingZeros(accountNumber);
  }

  private static String removeLeadingZeros(final String str) {
    return LEADING_ZEROS.matcher(str).replaceAll("");
  }

  public static String generateBban(final Long value) {
    return ("0000000000" + value.toString()).substring(value.toString().length());
  }


  public static boolean matchesIbanPattern(String arrangementNumber) {
    return PATTERN_IBAN.matcher(arrangementNumber).matches();
  }

  public static boolean matchesBbanPattern(String arrangementNumber) {
    return PATTERN_BBAN.matcher(arrangementNumber).matches();
  }
}
