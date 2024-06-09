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
        ret = Raster.raster("#FFF", .2, null, null, null);
        assertEquals("",ret);
        ret = Raster.raster("#FFF", .2, .3, null, null);
        assertEquals("",ret);
        ret = Raster.raster("#FFF", .2, .3, .4, null);
        assertEquals("",ret);
        ret = Raster.raster("#FFF", .2, .3, .4, "");
        assertEquals("",ret);
        ret = Raster.raster("#FFF", .2, .3, .4, "        ");
        assertEquals("",ret);
    }

    @Test
    public void shouldGenerateDot()
    {
        String ret = Raster.raster("#CCC", 1d, 0d, 0d, "X");
        assertEquals("<!-- [X] -->\n<rect fill='#CCC' x='0.0' y='0.0' width='1.0' height='1.0' />\n",ret);
    }

    @Test
    public void shouldGenerateLine()
    {
        String ret = Raster.raster("#CCC", 2.0, -1.0, 3.0, "---");
        assertEquals(
                "<!-- [---] -->\n"+
                "<rect fill='#CCC' x='-2.0' y='6.0' width='2.0' height='2.0' />\n"+
                "<rect fill='#CCC' x='0.0' y='6.0' width='2.0' height='2.0' />\n"+
                "<rect fill='#CCC' x='2.0' y='6.0' width='2.0' height='2.0' />\n",
                ret);
    }


    @Test
    public void shouldGenerateBars()
    {
        String ret = Raster.raster("#CCC", 3.0, -.5, .25, "| |");
        assertEquals(
                "<!-- [| |] -->\n"+
                "<rect fill='#CCC' x='-1.5' y='0.75' width='3.0' height='3.0' />\n"+
                "<rect fill='#CCC' x='4.5' y='0.75' width='3.0' height='3.0' />\n",
                ret);
    }
}
