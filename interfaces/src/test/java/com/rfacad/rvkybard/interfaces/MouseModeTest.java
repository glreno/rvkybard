package com.rfacad.rvkybard.interfaces;

import static org.junit.Assert.assertEquals;

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

public class MouseModeTest
{
    @Test
    public void testMouseModes()
    {
        assertEquals("ontouchstart",MouseMode.TOUCH.getDown());
        assertEquals("ontouchend",MouseMode.TOUCH.getUp());
    }
}
