package com.rfacad.rvkybard;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.rfacad.rvkybard.interfaces.KybardCode;
import com.rfacad.rvkybard.interfaces.KybardFlag;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

public class KybardPressServletTest
{
    @Test
    public void shouldFindKeys()
    {
        KybardPressServlet kps = new KybardPressServlet();

        byte[] b0 = kps.parseKeys("");
        assertEquals(0,b0.length);

        byte[] b1 = kps.parseKeys("k=A");
        assertEquals(1,b1.length);
        assertEquals(KybardCode.KB_A.getCode(),b1[0]);

        byte[] b2 = kps.parseKeys("k=A,F1,1,@,KP_ENTER,Comma,*,+");
        assertEquals(8,b2.length); // sender limited to 6, but parser doesn't care
        assertEquals(KybardCode.KB_A.getCode(),b2[0]);
        assertEquals(KybardCode.KB_F1.getCode(),b2[1]);
        assertEquals(KybardCode.KB_1.getCode(),b2[2]);
        assertEquals(KybardCode.KB_2.getCode(),b2[3]);
        assertEquals(KybardCode.KP_ENTER.getCode(),b2[4]);
        assertEquals(KybardCode.KB_COMMA.getCode(),b2[5]);
        assertEquals(KybardCode.KB_8.getCode(),b2[6]);
        assertEquals(KybardCode.KB_EQUAL.getCode(),b2[7]);

        byte[] b3 = kps.parseKeys("f=LEFT_SHIFT&k=a");
        assertEquals(1,b3.length);
        assertEquals(KybardCode.KB_A.getCode(),b3[0]);

        byte[] b4 = kps.parseKeys("k=x&F=LEFT_CTRL");
        assertEquals(1,b4.length);
        assertEquals(KybardCode.KB_X.getCode(),b4[0]);

        byte[] b5 = kps.parseKeys("k=this is not a key,y");
        assertEquals(2,b5.length);
        assertEquals(KybardCode.KB_Y.getCode(),b5[0]);
        assertEquals(0,b5[1]);

        byte[] b6 = kps.parseKeys("k=");
        assertEquals(1,b6.length);
        assertEquals(0,b6[0]);
    }

    @Test
    public void shouldFindModifierKeys()
    {
        KybardPressServlet kps = new KybardPressServlet();

        byte b = kps.parseFlags("");
        assertEquals(0,b);

        b = kps.parseFlags("f=");
        assertEquals(0,b);

        b = kps.parseFlags("f=LEFT_SHIFT");
        assertEquals(KybardFlag.LEFT_SHIFT.getBits(),b);

        b = kps.parseFlags("f=RIGHT_SHIFT&k=A");
        assertEquals(KybardFlag.RIGHT_SHIFT.getBits(),b);

        b = kps.parseFlags("k=B&f=RIGHT_CTRL");
        assertEquals(KybardFlag.RIGHT_CTRL.getBits(),b);

        b = kps.parseFlags("f=RIGHT_CTRL,LEFT_CTRL");
        assertEquals(0x11,b);

        b = kps.parseFlags("f=RIGHT_SHIFT,KB_A,LEFT_SHIFT");
        assertEquals(0x22,b);

        b = kps.parseFlags("f=RIGHT_ALT,this is not a key,LEFT_ALT");
        assertEquals(0x44,b);

        b = kps.parseFlags("f=RIGHT_GUI,LEFT_GUI&");
        assertEquals(0x88,b & 0xff); // assertEquals(int,int) extends high bit of b

        b = kps.parseFlags("f=RIGHT_CTRL,LEFT_CTRL,RIGHT_SHIFT,LEFT_SHIFT,RIGHT_GUI,RIGHT_ALT,LEFT_ALT,LEFT_GUI");
        assertEquals(0xFF,b & 0xff);

    }

    @Test
    public void shouldSendKeys() throws ServletException, IOException
    {
        byte expectedFlags = KybardFlag.LEFT_SHIFT.getBits();
        byte [] expectedKeys = { KybardCode.KB_B.getCode(), KybardCode.KB_X.getCode() };
        KybardSender mockSender = mock(KybardSender.class);

        KybardPressServlet kps = new KybardPressServlet();
        kps.init();
        kps.setKybardSender(mockSender);

        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getQueryString()).thenReturn("f=LEFT_SHIFT,k=b,x");
        kps.doGet(req,resp);

        kps.destroy();

        verify(mockSender).sendKeys(expectedFlags,expectedKeys);
        verify(mockSender).shutdown();
    }
}
