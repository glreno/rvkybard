package com.rfacad.rvkybard.interfaces;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MouseModeTest
{
    @Test
    public void testMouseModes()
    {
        assertEquals("ontouchstart",MouseMode.TOUCH.getDown());
        assertEquals("ontouchend",MouseMode.TOUCH.getUp());
    }
}
