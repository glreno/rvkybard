package com.rfacad.rvkybard.util;

import static org.junit.Assert.*;

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
public class RasterTest
{
    @Test
    public void sigTest()
    {
        assertEquals("raster",Raster.getFunctionName());
        Class<?>[] sig = Raster.getFunctionSignature();
        assertEquals(5,sig.length);
    }

    @Test
    public void nullcheck()
    {
        String ret = Raster.raster(null, null, null, null, null);
        assertEquals("",ret);
        ret = Raster.raster("#FFF", null, null, null, null);
        assertEquals("",ret);
        ret = Raster.raster("#FFF", 2, null, null, null);
        assertEquals("",ret);
        ret = Raster.raster("#FFF", 2, 3, null, null);
        assertEquals("",ret);
        ret = Raster.raster("#FFF", 2, 3, 4, null);
        assertEquals("",ret);
        ret = Raster.raster("#FFF", 2, 3, 4, "");
        assertEquals("",ret);
        ret = Raster.raster("#FFF", 2, 3, 4, "        ");
        assertEquals("",ret);
    }

    @Test
    public void shouldGenerateDot()
    {
        String ret = Raster.raster("#CCC", 1, 0, 0, "X");
        assertEquals("<rect fill='#CCC' x='0' y='0' width='1' height='1' />\n",ret);
    }

    @Test
    public void shouldGenerateLine()
    {
        String ret = Raster.raster("#CCC", 2, -1, 3, "---");
        assertEquals(
                "<rect fill='#CCC' x='-2' y='6' width='2' height='2' />\n"+
                "<rect fill='#CCC' x='0' y='6' width='2' height='2' />\n"+
                "<rect fill='#CCC' x='2' y='6' width='2' height='2' />\n",
                ret);
    }


    @Test
    public void shouldGenerateBars()
    {
        String ret = Raster.raster("#CCC", 3, -1, 3, "| |");
        assertEquals(
                "<rect fill='#CCC' x='-3' y='9' width='3' height='3' />\n"+
                "<rect fill='#CCC' x='3' y='9' width='3' height='3' />\n",
                ret);
    }
}
