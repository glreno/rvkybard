package com.rfacad.rvkybard;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
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
        KybardJspHelper h = new KybardJspHelper(out, "My Title Here", "");
        h.startHtml();
        String s = out.toString();
        assertTrue(s,s.startsWith("<!DOCTYPE html>\n<html lang='en'>\n"));
        assertTrue(s,s.contains("<title>My Title Here</title>"));
    }

    @Test
    public void shouldWriteStartKeyboard()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.startKeyboard();
        String s = out.toString();
        assertTrue(s,s.equals("<table cellspacing=0 cellpadding=0 >\n"));
    }

    @Test
    public void shouldWriteStartRow()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.startRow();
        String s = out.toString();
        assertTrue(s,s.startsWith("<tr>\n"));
    }

    @Test
    public void shouldWriteEasyKey()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.setTop(new File("src/main/webapp/kb"));
        h.setDefaultSvg("numeric/keys/key.svgt", 66, 66, 3, 3);
        h.key("a");
        String s = out.toString();
        assertTrue(s,s.startsWith("<td colspan=3 rowspan=3><button"));
        assertTrue(s,s.contains("keyDown('a')"));
        assertTrue(s,s.contains("keyUp('a')"));
        assertTrue(s,s.contains(">a</text>"));
        assertTrue(s,s.endsWith("</button></td>\n"));
    }

    @Test
    public void shouldWriteSimpleKey()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.setTop(new File("src/main/webapp/kb"));
        h.setDefaultSvg("numeric/keys/key.svgt", 66, 66, 3, 3);
        h.key("/","KP_DIVIDE");
        String s = out.toString();
        assertTrue(s,s.startsWith("<td colspan=3 rowspan=3><button"));
        assertTrue(s,s.contains("keyDown('KP_DIVIDE')"));
        assertTrue(s,s.contains("keyUp('KP_DIVIDE')"));
        assertTrue(s,s.contains(">/</text>"));
        assertTrue(s,s.endsWith("</button></td>\n"));
    }

    @Test
    public void shouldWriteSpacer()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.setTop(new File("src/main/webapp/kb"));
        h.setDefaultSvg("numeric/keys/key.svgt", 66, 66, 3, 3);
        h.spacer(5);
        String s = out.toString();
        assertEquals("<td colspan='5' rowspan='3'><svg width=110 height=66 ></svg></td>\n",s);
    }
    @Test
    public void shouldWriteEndRow()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.endRow();
        String s = out.toString();
        assertTrue(s,s.endsWith("</tr>\n"));
    }

    @Test
    public void shouldWriteEndKeyboard()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.endKeyboard();
        String s = out.toString();
        assertEquals("</table>\n",s);
    }

    @Test
    public void shouldWriteEndPage()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.endHtml();
        String s = out.toString();
        assertEquals("</body>\n</html>\n",s);
    }

    @Test
    public void shouldWriteSvg() throws IOException
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.setTop(new File("src/main/webapp/kb"));
        h.setDefaultSvg("numeric/keys/key.svgt", 66, 66, 3, 3);
        h.embedSvg("foo", null, new String [] {"W-16=bar", "H-16=baz"});
        String s = out.toString();
        assertTrue(s,s.contains(">foo</text>"));
    }

    @Test
    public void shouldWriteSvgOverride() throws IOException
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.setTop(new File("src/main/webapp/kb"));
        h.setDefaultSvg("numeric/keys/key.svgt", 66, 66, 3, 3);
        h.embedSvg("foo", "numeric/keys/keypad.svgt", new String [] {"L=","S=bletch","W-16=bar", "H-16=baz"});
        String s = out.toString();
        assertFalse(s,s.contains(">foo</text>"));
        assertTrue(s,s.contains(">bletch</text>"));
    }
}
