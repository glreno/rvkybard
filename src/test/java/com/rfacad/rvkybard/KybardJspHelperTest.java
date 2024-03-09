package com.rfacad.rvkybard;

import static org.junit.Assert.*;

import java.io.StringWriter;

import org.junit.Test;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
public class KybardJspHelperTest
{
    @Test
    public void shouldWriteStartPage()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.startHtml();
        String s = out.toString();
        assertTrue(s.startsWith("<!DOCTYPE html>\r\n<html lang='en'>\r\n"));
    }

    @Test
    public void shouldWriteStartKeyboard()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.startKeyboard();
        String s = out.toString();
        assertTrue(s.startsWith("</head>\r\n<body>\r\n"));
        assertTrue(s.endsWith("<table cellspacing=0 cellpadding=0 border=1>\r\n"));
    }

    @Test
    public void shouldWriteStartRow()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.startRow();
        String s = out.toString();
        assertTrue(s.startsWith("<tr>\r\n"));
    }

    @Test
    public void shouldWriteSimpleKey()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.key("a");
        String s = out.toString();
        assertTrue(s.startsWith("<td colspan=3 rowspan=3>\r\n"));
        assertTrue(s.contains("<button"));
        assertTrue(s.endsWith("</td>\r\n"));
    }

    @Test
    public void shouldWriteEndRow()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.endRow();
        String s = out.toString();
        assertTrue(s.endsWith("</tr>\r\n"));
    }

    @Test
    public void shouldWriteEndKeyboard()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.endKeyboard();
        String s = out.toString();
        assertEquals("</table>\r\n",s);
    }

    @Test
    public void shouldWriteEndPage()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.endHtml();
        String s = out.toString();
        assertEquals("</body>\r\n</html>\r\n",s);
    }
}
