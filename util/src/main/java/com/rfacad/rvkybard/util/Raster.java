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
    private static final Class<?> [] SIG = new Class[] {String.class, Double.class,Double.class,Double.class,String.class};
    public static String getFunctionName() { return "raster";}
    public static Class<?>[] getFunctionSignature() { return SIG;}
    private Raster() {}
    /**
     * Convert a String representation of a one-row bitmap into a bunch of SVG.
     * Every non-space in the String will be drawn as a rectangle. So
     * raster("#FFF", 2, -3, 5, "# #");
     * will generate this SVG code:
     *
     * <rect fill='#FFF' x='-6' y='10' width='2' height='2'/>
     * <rect fill='#FFF' x='-2' y='10' width='2' height='2'/>
     *
     * @param colour colour of the pixels, e.g. #FFC640
     * @param pxsz pixel size -- this will be used to scale the (x,y) coords and set the width and height of each rectangle.
     * @param x starting x coord of the line, in pixels
     * @param y top y coord of the line, in pixels
     * @param raster String representing the raster
     * @return
     */
    public static String raster(String colour, Double pxsz, Double x, Double y, String raster)
    {
            if ( colour==null || pxsz==null || x==null || y==null || raster==null || raster.trim().length()==0 )
            {
                return "";
            }
            StringBuilder ret = new StringBuilder();
            ret.append("<!-- [");
            ret.append(raster);
            ret.append("] -->\n");
            int len=raster.length();
            for(int i=0;i<len;i++)
            {
                if ( raster.charAt(i) != ' ' )
                {
                    ret.append("<rect fill='");
                    ret.append(colour);
                    ret.append("' x='");
                    ret.append(x*pxsz+i*pxsz);
                    ret.append("' y='");
                    ret.append(y*pxsz);
                    ret.append("' width='");
                    ret.append(pxsz);
                    ret.append("' height='");
                    ret.append(pxsz);
                    ret.append("' />\n");
                }
            }
            return ret.toString();
    }

}
