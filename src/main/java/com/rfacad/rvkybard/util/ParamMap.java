package com.rfacad.rvkybard.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
/**
 * A Map that is backed by two other maps, and returns "" when neither has a value.
 * This doesn't technically meet the contract for Map, but all mvel needs is
 * containsKey() and get() --- and mvel throws an exception if it ever gets a null from the map.
 */
public class ParamMap implements Map<String,String>
{
    private Map<String, String> defaultValues;
    private Map<String, String> values;

    public ParamMap(Map<String,String> defaultValues, Map<String,String> values)
    {
        this.defaultValues=defaultValues;
        this.values=values;
    }

    @Override
    public int size()
    {
        return keySet().size();
    }

    @Override
    public boolean isEmpty()
    {
        return defaultValues.isEmpty() && values.isEmpty();
    }

    @Override
    public boolean containsKey(Object key)
    {
        return true;
    }

    @Override
    public boolean containsValue(Object value)
    {
        return valueSet().contains(value);
    }

    @Override
    public String get(Object key)
    {
        String ret = values.get(key);
        if ( ret != null )
        {
            return ret;
        }
        ret = defaultValues.get(key);
        if ( ret != null )
        {
            return ret;
        }
        return "";
    }

    @Override
    public String put(String key, String value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(Object key)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> keySet()
    {
        // This set is SUPPOSED to be backed by the original Map. It isn't.
        Set<String> ret=new TreeSet<>();
        ret.addAll(defaultValues.keySet());
        ret.addAll(values.keySet());
        return ret;
    }

    @Override
    public Collection<String> values()
    {
        // This set is SUPPOSED to be backed by the original Map. It isn't.
        return valueSet();
    }

    private Set<String> valueSet()
    {
        Set<String> ret=new TreeSet<>();
        Set<String> keys=keySet();
        for(String k : keys )
        {
            ret.add(get(k));
        }
        return ret;
    }

    @Override
    public Set<Entry<String, String>> entrySet()
    {
        // This set is SUPPOSED to be backed by the original Map. It isn't.
        Map<String, String> ret=new TreeMap<>();
        Set<String> keys=keySet();
        for(String k : keys )
        {
            ret.put(k,get(k));
        }
        return ret.entrySet();
    }
}
