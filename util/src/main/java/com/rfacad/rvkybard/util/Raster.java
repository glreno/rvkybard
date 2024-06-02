package com.rfacad.rvkybard.util;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
public class Raster
{
    private static final Class<?> [] SIG = new Class[] {Integer.class,Integer.class,Integer.class,String.class};
    public static String getFunctionName() { return "raster";}
    public static Class<?>[] getFunctionSignature() { return SIG;}

    public static String raster(Integer pxsz, Integer x, Integer y, String raster)
    {
            return "This is a raster?";
    }

}
