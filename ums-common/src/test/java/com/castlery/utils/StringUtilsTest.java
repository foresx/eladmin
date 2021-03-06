package com.castlery.utils;

import static com.castlery.utils.StringUtils.toCamelCase;
import static com.castlery.utils.StringUtils.toCapitalizeCamelCase;
import static com.castlery.utils.StringUtils.toUnderScoreCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class StringUtilsTest {

  @Test
  public void testToCamelCase() {
    assertNull(toCamelCase(null));
  }

  @Test
  public void testToCapitalizeCamelCase() {
    assertNull(StringUtils.toCapitalizeCamelCase(null));
    assertEquals("HelloWorld", toCapitalizeCamelCase("hello_world"));
  }

  @Test
  public void testToUnderScoreCase() {
    assertNull(StringUtils.toUnderScoreCase(null));
    assertEquals("hello_world", toUnderScoreCase("helloWorld"));
    assertEquals("\u0000\u0000", toUnderScoreCase("\u0000\u0000"));
    assertEquals("\u0000_a", toUnderScoreCase("\u0000A"));
  }

  //  @Test
  //  public void testGetWeekDay() {
  //    SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
  //    assertEquals(simpleDateformat.format(new Date()), getWeekDay());
  //  }

  //  @Test
  //  public void testGetIP() {
  //    assertEquals("127.0.0.1", getIp(new MockHttpServletRequest()));
  //  }
}
