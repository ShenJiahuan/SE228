package com.shenjiahuan.eBook.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserCheckTest {

    @Test
    public void checkEmailValid() {
        assertTrue(UserCheck.checkEmail("shenjiahuan@sjtu.edu.cn"));
    }

    @Test
    public void checkEmailWithoutAt() {
        assertFalse(UserCheck.checkEmail("shenjiahuansjtu.edu.cn"));
    }

    @Test
    public void checkEmailWithoutUsername() {
        assertFalse(UserCheck.checkEmail("@sjtu.edu.cn"));
    }

    @Test
    public void checkEmailWithAtNearDot() {
        assertFalse(UserCheck.checkEmail("shenjiahuan@.sjtu.edu.cn"));
    }

    @Test
    public void checkEmailWithoutDot() {
        assertFalse(UserCheck.checkEmail("shenjiahuan@sjtueducn"));
    }

    @Test
    public void checkEmailTerminatedWithDot() {
        assertFalse(UserCheck.checkEmail("shenjiahuan@sjtu.edu.cn."));
    }

    @Test
    public void checkEmailNull() {
        assertFalse(UserCheck.checkEmail(null));
    }

    @Test
    public void checkPassValid() {
        assertTrue(UserCheck.checkPass("abcd1234"));
    }

    @Test
    public void checkPassTooShort() {
        assertFalse(UserCheck.checkPass("abcd123"));
    }

    @Test
    public void checkPassWithoutEnglishCharacters() {
        assertFalse(UserCheck.checkPass("12345678"));
    }

    @Test
    public void checkPassWithoutDigits() {
        assertFalse(UserCheck.checkPass("abcdefgh"));
    }

    @Test
    public void checkPassNull() {
        assertFalse(UserCheck.checkPass(null));
    }
}
