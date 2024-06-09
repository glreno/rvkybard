package com.rfacad.rvkybard.util;

import java.util.Collection;
import java.util.HashMap;
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
public class ParamMap<V> implements Map<String,V>
{
    private Map<String, V> defaultValues;
    private Map<String, V> values;
    private Map<String, V> runtimeValues;
    private V emptyValue;

    public ParamMap(Map<String,V> defaultValues, Map<String,V> values, V emptyValue)
    {
        this.defaultValues=defaultValues;
        this.values=values;
        this.emptyValue=emptyValue;
        this.runtimeValues=new HashMap<>();
    }

    @Override
    public int size()
    {
        return keySet().size();
    }

    @Override
    public boolean isEmpty()
    {
        return runtimeValues.isEmpty() && defaultValues.isEmpty() && values.isEmpty();
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
    public V get(Object key)
    {
        V ret = runtimeValues.get(key);
        if ( ret != null )
        {
            return ret;
        }
        ret = values.get(key);
        if ( ret != null )
        {
            return ret;
        }
        ret = defaultValues.get(key);
        if ( ret != null )
        {
            return ret;
        }
        return emptyValue;
    }

    @Override
    public V put(String key, V value)
    {
        return runtimeValues.put(key,value);
    }

    @Override
    public V remove(Object key)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> m)
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
        ret.addAll(runtimeValues.keySet());
        ret.addAll(defaultValues.keySet());
        ret.addAll(values.keySet());
        return ret;
    }

    @Override
    public Collection<V> values()
    {
        // This set is SUPPOSED to be backed by the original Map. It isn't.
        return valueSet();
    }

    private Set<V> valueSet()
    {
        Set<V> ret=new TreeSet<>();
        Set<String> keys=keySet();
        for(String k : keys )
        {
            ret.add(get(k));
        }
        return ret;
    }

    @Override
    public Set<Entry<String, V>> entrySet()
    {
        // This set is SUPPOSED to be backed by the original Map. It isn't.
        Map<String, V> ret=new TreeMap<>();
        Set<String> keys=keySet();
        for(String k : keys )
        {
            ret.put(k,get(k));
        }
        return ret.entrySet();
    }
}
