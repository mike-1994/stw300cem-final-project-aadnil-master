package com.android.digitalparking;

import com.android.digitalparking.BLL.LoginBLL;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UniteTest {
    @Test
    public void  testLogin()
    {
        LoginBLL loginBL = new LoginBLL("abc","abc");
        boolean result = loginBL.checkUser();
        assertEquals(true,result);
    }

    @Test
    public void failedTestLogin()
    {
        LoginBLL loginBL = new LoginBLL("sdfsdfsdfsf@gmail.com","password");
        boolean result = loginBL.checkUser();
        assertEquals(true,result);
    }

}
