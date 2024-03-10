package com.rfacad.rvkybard.util;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import java.util.Map.Entry;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
public class ParamMapTest
{
    private static final String FOO="foo"; // only in primary
    private static final String BAR="bar"; // in both
    private static final String BAZ="baz"; // only in secondary
    private static final String QUUX="quux"; // in neither
    private static final String FOO_VALUE="Value of Foo";
    private static final String BAR_VALUE="Value of Bar";
    private static final String BAZ_VALUE="Value of Baz";
    private static final String OVERRIDDEN="You never see this value!";
    private Map<String,String> primary;
    private Map<String,String> secondary;
    private ParamMap combined;
    private ParamMap empty;

    @Before
    public void setup()
    {
        primary = new HashMap<>();
        secondary = new HashMap<>();
        combined=new ParamMap(primary,secondary);
        empty=new ParamMap(Collections.emptyMap(),Collections.emptyMap());
        primary.put(FOO, FOO_VALUE);
        primary.put(BAR, OVERRIDDEN);
        secondary.put(BAR, BAR_VALUE);
        secondary.put(BAZ, BAZ_VALUE);
    }

    @Test
    public void testSize()
    {
        assertEquals(2,primary.size());
        assertEquals(2,secondary.size());

        assertEquals(3,combined.size()); // foo,bar,baz; only one bar; no quux
        assertEquals(0,empty.size());
    }

    @Test
    public void testIsEmpty()
    {
        assertTrue(empty.isEmpty());
        assertFalse(combined.isEmpty());
    }

    @Test
    public void testContainsKey()
    {
        assertTrue(combined.containsKey(FOO));
        assertTrue(combined.containsKey(BAR));
        assertTrue(combined.containsKey(BAZ));
        assertTrue(combined.containsKey(QUUX));
        assertTrue(empty.containsKey(FOO));
        assertTrue(empty.containsKey(BAR));
        assertTrue(empty.containsKey(BAZ));
        assertTrue(empty.containsKey(QUUX));
    }

    @Test
    public void testContainsValue()
    {
        assertTrue(combined.containsValue(FOO_VALUE));
        assertTrue(combined.containsValue(BAR_VALUE));
        assertTrue(combined.containsValue(BAZ_VALUE));
        assertFalse(combined.containsValue(OVERRIDDEN));
        assertFalse(empty.containsValue(FOO_VALUE));
        assertFalse(empty.containsValue(BAR_VALUE));
        assertFalse(empty.containsValue(BAZ_VALUE));
        assertFalse(empty.containsValue(OVERRIDDEN));
    }

    @Test
    public void testGet()
    {
        assertEquals(FOO_VALUE,combined.get(FOO));
        assertEquals(BAR_VALUE,combined.get(BAR));
        assertEquals(BAZ_VALUE,combined.get(BAZ));
        assertEquals("",combined.get(QUUX));
    }

    @Test
    public void testPut()
    {
        try {
            combined.put(FOO,"x");
            fail();
        }
        catch (UnsupportedOperationException e)
        {
        }
        // if you add a default, that will still be masked
        primary.put("A", "a");
        secondary.put("B", "b");
        primary.put(BAZ, OVERRIDDEN);
        secondary.put(FOO, "new foo");
        assertEquals("new foo",combined.get(FOO));
        assertEquals(BAZ_VALUE,combined.get(BAZ));
        assertEquals("a",combined.get("A"));
        assertEquals("b",combined.get("B"));
    }

    @Test
    public void testRemove()
    {
        try {
            combined.remove(FOO);
            fail();
        }
        catch (UnsupportedOperationException e)
        {
        }
        // If you remove an override, you should get the default
        assertEquals(BAR_VALUE,combined.get(BAR));
        secondary.remove(BAR);
        assertEquals(OVERRIDDEN,combined.get(BAR));
        // If you remove a value entirely, you should get ""
        primary.remove(BAR);
        assertEquals("",combined.get(BAR));
    }

    @Test
    public void testPutAll()
    {
        Map<String,String> replacement = new HashMap<>();
        try {
            combined.putAll(replacement);
            fail();
        }
        catch (UnsupportedOperationException e)
        {
        }
    }

    @Test
    public void testclear()
    {
        try {
            combined.clear();
            fail();
        }
        catch (UnsupportedOperationException e)
        {
        }
    }

    @Test
    public void testKeySet()
    {
        Set<String> keys=combined.keySet();
        assertEquals(3,keys.size());
        assertTrue(keys.contains(FOO));
        assertTrue(keys.contains(BAR));
        assertTrue(keys.contains(BAZ));
    }

    @Test
    public void testValues()
    {
        Collection<String> values = combined.values();
        assertEquals(3,values.size());
        assertTrue(values.contains(FOO_VALUE));
        assertTrue(values.contains(BAR_VALUE));
        assertTrue(values.contains(BAZ_VALUE));
    }

    @Test
    public void testEntrySet()
    {
        int n=0;
        Map<String,String> result=new HashMap<>();
        for(Entry<String,String> e : combined.entrySet() )
        {
            ++n;
            result.put(e.getKey(), e.getValue());
        }
        assertEquals(3,n);
        assertEquals(3,result.size());
        assertTrue(result.containsValue(FOO_VALUE));
        assertTrue(result.containsValue(BAR_VALUE));
        assertTrue(result.containsValue(BAZ_VALUE));
        assertFalse(result.containsValue(OVERRIDDEN));
    }

}
