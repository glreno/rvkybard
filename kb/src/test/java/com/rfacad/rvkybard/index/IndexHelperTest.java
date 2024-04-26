package com.rfacad.rvkybard.index;

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

public class IndexHelperTest
{
    private static final String TOP = "../kb/src/main/resources/kb";

    // Scan the shipped keyboards, and verify that all the correct files exist.
    @Test
    public void scanner()
    {
        IndexHelper ih=new IndexHelper(TOP);
        int total = 0;
        for(Theme theme : ih.getThemes() )
        {
            String themeName = theme.getName();
            assertNotNull(themeName);
            System.err.println("THEME: "+themeName);
            for(Keyboard keyboard : theme.getKeyboards() )
            {
                String keyboardName = keyboard.getName();
                assertNotNull(keyboardName);
                System.err.println("\tKeyboard: "+keyboardName);
                String keyboardDesc = keyboard.getDescription();
                assertNotNull(keyboardDesc);
                String keyboardSnap = keyboard.getSnapshotOrBlank();
                assertNotNull(keyboardSnap);
                for(Protocol p : keyboard.getProtocols())
                {
                    String protocolName = p.getName();
                    assertNotNull(protocolName);
                    String url = p.getLink();
                    assertNotNull(url);
                    System.err.println("\t\t"+protocolName+" => "+url);
                    assertTrue(url.endsWith("/kb.jsp"));
                    total++;
                }
            }
        }
        assertTrue( total > 0 );
        System.err.println("Total keyboard count: "+total);
    }
}
