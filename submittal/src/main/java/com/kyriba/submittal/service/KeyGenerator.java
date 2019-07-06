package com.kyriba.submittal.service;

import org.springframework.stereotype.Component;


/**
 * @author M-ABL
 */
@Component
public class KeyGenerator
{
  public static String VALID_KEY = "some_valid_key";
  public static String INVALID_KEY = "some_invalid_key";


  public String generate(final KeyType keyType)
  {
    return VALID_KEY;
  }
}
