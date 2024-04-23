package com.rfacad.rvkybard.jsp;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    private static final File TOP = new File("../kb/src/main/resources/kb");

    @Test
    public void shouldCalculateDefaultSizes() throws IOException
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "My Title Here", 15,4, "");
        assertEquals("My Title Here",runMvel(h,"@{TITLE}"));
        assertEquals("x20",runMvel(h,"x@{cellW}"));
        assertEquals("x20",runMvel(h,"x@{cellH}"));
        assertEquals("x1.0",runMvel(h,"x@{P}"));
        assertEquals("x2",runMvel(h,"x@{gridGap}"));
        assertEquals("x15",runMvel(h,"x@{gridCols}"));
        assertEquals("x4",runMvel(h,"x@{gridRows}"));
        assertEquals("x3",runMvel(h,"x@{stdColSpan}"));
        assertEquals("x3",runMvel(h,"x@{stdRowSpan}"));
        assertEquals("x64",runMvel(h,"x@{stdW}")); // 3*22-2
        assertEquals("x64",runMvel(h,"x@{stdH}")); // 3*22-2
        assertEquals("x332",runMvel(h,"x@{kbdW}")); // 15*22+2
        assertEquals("x90",runMvel(h,"x@{kbdH}")); // 4*22+2
        assertEquals("x",runMvel(h,"x@{H}"));
        assertEquals("x",runMvel(h,"x@{W}"));
        h.setDefaultSvg("", 0,0, 5, 4);
        assertEquals("x20",runMvel(h,"x@{cellW}"));
        assertEquals("x20",runMvel(h,"x@{cellH}"));
        assertEquals("x1.0",runMvel(h,"x@{P}"));
        assertEquals("x2",runMvel(h,"x@{gridGap}"));
        assertEquals("x15",runMvel(h,"x@{gridCols}"));
        assertEquals("x4",runMvel(h,"x@{gridRows}"));
        assertEquals("x5",runMvel(h,"x@{stdColSpan}"));
        assertEquals("x4",runMvel(h,"x@{stdRowSpan}"));
        assertEquals("x108",runMvel(h,"x@{stdW}")); // 5*22-2
        assertEquals("x86",runMvel(h,"x@{stdH}"));  // 4*22-2
        assertEquals("x332",runMvel(h,"x@{kbdW}")); // 15*22+2
        assertEquals("x90",runMvel(h,"x@{kbdH}")); // 4*22+2
        h.setDefaultCellSize(15,17,1.5,3);
        assertEquals("x15",runMvel(h,"x@{cellW}"));
        assertEquals("x17",runMvel(h,"x@{cellH}"));
        assertEquals("x1.5",runMvel(h,"x@{P}"));
        assertEquals("x3",runMvel(h,"x@{gridGap}"));
        assertEquals("x15",runMvel(h,"x@{gridCols}"));
        assertEquals("x4",runMvel(h,"x@{gridRows}"));
        assertEquals("x5",runMvel(h,"x@{stdColSpan}"));
        assertEquals("x4",runMvel(h,"x@{stdRowSpan}"));
        assertEquals("x87",runMvel(h,"x@{stdW}")); // 5*18-3
        assertEquals("x77",runMvel(h,"x@{stdH}")); // 4*20-3
        assertEquals("x273",runMvel(h,"x@{kbdW}")); // 15*18+3
        assertEquals("x83",runMvel(h,"x@{kbdH}")); // 4*20+3
    }
    private String runMvel(KybardJspHelper h,String s) throws IOException
    {
        StringWriter out = new StringWriter();
        InputStream in = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
        h.templateProcessor.processStream("", in, out, Collections.emptyMap());
        return out.toString();
    }
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
        assertEquals("<div class='kybard-container' id='kybard-main' >\n",s);
    }

    @Test
    public void shouldWriteStartMenu()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.startMenu();
        String s = out.toString();
        assertEquals("<div class='kybard-menu-container' id='kybard-menu' >\n",s);
    }

    @Test
    public void shouldStartRow()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        assertEquals(1,h.getX());
        h.setX(5);
        assertEquals(5,h.getX());
        h.startRow();
        String s = out.toString();
        assertEquals("",s);
        assertEquals(1,h.getX());
    }

    @Test
    public void shouldWriteNonKey()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.setTop(TOP);
        h.setDefaultSvg("numeric/keys/key.svgt", 6, 2);
        h.setX(5);
        h.setY(4);
        h.notKey("foo", 6, 2, "bar");
        String s = out.toString();
        System.err.println(s);
        assertEquals("<div class='foo' style='grid-area: 4/5/span 2/span 6;'>bar</div>",s.substring(0, 64));
        assertFalse(s,s.contains("ontouchstart="));
        assertFalse(s,s.contains("ontouchend="));
    }

    @Test
    public void shouldWriteEasyKey()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.setTop(TOP);
        h.setDefaultSvg("numeric/keys/key.svgt", 6, 2);
        h.setX(5);
        h.setY(4);
        h.key("a");
        String s = out.toString();
        System.err.println(s);
        assertEquals("<div class='key' style='grid-area: 4/5/span 2/span 6;'><button",s.substring(0, 62));
        assertTrue(s,s.contains("ontouchstart=\"keyDown(this,'a')"));
        assertTrue(s,s.contains("ontouchend=\"keyUp(this,'a')"));
        assertTrue(s,s.contains(">a</text>"));
        // validate some of the variables that are in key.svgt, especially W and H which are calculated in key()
        assertTrue(s,s.contains("<svg width=\"130\" height=\"42\">")); // W=6*22-2=130 H=2*22-2
        assertTrue(s,s.endsWith("</button></div>\n"));
    }

    @Test
    public void shouldWriteEasyKeyForMouse()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.setMouseMode(true);
        h.setTop(TOP);
        h.setDefaultSvg("numeric/keys/key.svgt", 6, 2);
        h.setX(5);
        h.setY(4);
        h.key("a");
        String s = out.toString();
        System.err.println(s);
        assertEquals("<div class='key' style='grid-area: 4/5/span 2/span 6;'><button",s.substring(0, 62));
        assertTrue(s,s.contains("onmousedown=\"keyDown(this,'a')"));
        assertTrue(s,s.contains("onmouseup=\"keyUp(this,'a')"));
        assertTrue(s,s.contains(">a</text>"));
        // validate some of the variables that are in key.svgt, especially W and H which are calculated in key()
        assertTrue(s,s.contains("<svg width=\"130\" height=\"42\">")); // W=6*22-2=130 H=2*22-2
        assertTrue(s,s.endsWith("</button></div>\n"));
    }

    @Test
    public void shouldWriteSimpleKey()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.setTop(TOP);
        h.setDefaultSvg("numeric/keys/key.svgt", 66, 66, 3, 3);
        h.setX(5);
        h.setY(4);
        h.key("/","KP_DIVIDE");
        String s = out.toString();
        assertEquals("<div class='key' style='grid-area: 4/5/span 3/span 3;'><button",s.substring(0, 62));
        assertTrue(s,s.contains("keyDown(this,'KP_DIVIDE')"));
        assertTrue(s,s.contains("keyUp(this,'KP_DIVIDE')"));
        assertTrue(s,s.contains(">/</text>"));
        assertTrue(s,s.endsWith("</button></div>\n"));
    }

    @Test
    public void shouldWriteShiftKey()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.setTop(TOP);
        h.setDefaultSvg("numeric/keys/key.svgt", 66, 66, 3, 3);
        h.setX(5);
        h.setY(4);
        h.key("Shift","LEFT_SHIFT",6,3,h.SHIFT,h.SHIFT,"",null);
        String s = out.toString();
        assertEquals("<div class='key' style='grid-area: 4/5/span 3/span 6;'><button",s.substring(0, 62));
        assertTrue(s,s.contains("flagDown(this,'LEFT_SHIFT')"));
        assertTrue(s,s.contains("flagUp(this,'LEFT_SHIFT')"));
        assertTrue(s,s.contains(">Shift</text>"));
        assertTrue(s,s.endsWith("</button></div>\n"));
    }

    @Test
    public void shouldWriteSpacer()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.setTop(TOP);
        h.setDefaultSvg("numeric/keys/key.svgt", 66, 66, 3, 3);
        assertEquals(1,h.getX());
        h.setX(4);
        assertEquals(4,h.getX());
        h.spacer(5);
        assertEquals(9,h.getX());
        String s = out.toString();
        assertEquals("",s);
    }
    @Test
    public void shouldWriteEndRow()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        assertEquals(1,h.getY());
        h.setY(9);
        assertEquals(9,h.getY());
        h.endRow();
        String s = out.toString();
        assertEquals("",s);
        assertEquals(12,h.getY());
    }

    @Test
    public void shouldWriteEndKeyboard()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.endKeyboard();
        String s = out.toString();
        assertEquals("</div>\n",s);
    }

    @Test
    public void shouldWriteEndPage()
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "MyTitle", "");
        h.loadDefault("COPYRIGHTMESSAGE", "Additional copyright message");
        h.endHtml();
        String s = out.toString();
        assertTrue(s,s.endsWith("</body>\n</html>\n"));
        assertTrue(s,s.contains("panic();"));
        assertTrue(s,s.contains("rvkybard and the \"MyTitle\" keyboard Copyright &copy;"));
        assertTrue(s,s.contains("Additional copyright message"));
    }

    @Test
    public void shouldWriteSvg() throws IOException
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.setTop(TOP);
        h.setDefaultSvg("numeric/keys/key.svgt", 66, 66, 3, 3);
        Map<String,Object> keyParams=new HashMap<>();
        keyParams.put("name", "foo");
        h.embedSvg("foo", null, keyParams, new String [0]);
        String s = out.toString();
        assertTrue(s,s.contains(">foo</text>"));
    }

    @Test
    public void shouldWriteSvgOverride() throws IOException
    {
        StringWriter out = new StringWriter();
        KybardJspHelper h = new KybardJspHelper(out, "", "");
        h.setTop(TOP);
        h.setDefaultSvg("numeric/keys/key.svgt", 66, 66, 3, 3);
        Map<String,Object> keyParams=new HashMap<>();
        keyParams.put("name", "foo");
        h.embedSvg("foo", "numeric/keys/keypad.svgt", keyParams, new String [] {"name=","S=bletch"});
        String s = out.toString();
        assertFalse(s,s.contains(">foo</text>"));
        assertTrue(s,s.contains(">bletch</text>"));
    }
}
