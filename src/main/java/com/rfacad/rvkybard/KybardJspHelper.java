package com.rfacad.rvkybard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
public class KybardJspHelper
{
    private static Logger LOG = LoggerFactory.getLogger(KybardJspHelper.class);

    private Writer out;

    public KybardJspHelper(Writer out, String title,String favIcoFn)
    {
        this.out = out;
    }

    private void println(String s) throws IOException
    {
        out.write(s);
        out.write("\r\n");
        out.flush();
    }

    protected void handleException(Exception e)
    {
        LOG.error("Exception generating keyboard",e);
        // TODO the page should throw a 500 at this point!
    }

    private void copyResource(String resourceName)
    {
        URL resource = this.getClass().getResource(resourceName);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(resource.openStream())))
        {
            String s;
            while( (s=in.readLine()) != null )
            {
                println(s);
            }
        }
        catch(IOException e)
        {
            handleException(e);
        }
    }

    public void setDefaultSvg(String templateFn, int keyWidthPixels, int keyHeightPixels, int keySpanCols, int keySpanRows, String ... defaultParams)
    {
    }

    public void addStyle(String cls, String key, String value)
    {
    }

    /** The top of the page. Includes <html><head> and start of <body,
     * including <meta> <style> and the start of the <script> section,
     * including all of the standard function definitions for sending keycodes,
     * and keyUp() and keyDown().
     * To set additional style settings, call addStyle() first.
     * To add more javascript, just write it in the .jsp between startHtml() and startKeyboard().
     */
    public void startHtml()
    {
        // Copy initial HTML from htmlt file
        copyResource("/startPage.htmlt");
        // TODO Generate the <title> tag
        // TODO Generate the favicon
        // TODO Generate the style stuff
        // TODO that's in the htmlt right now
        // TODO Import of a .js file with all the hard work in it
        // TODO that's in the htmlt right now
    }

    /** The end of the header HTML, and the beginning of the keyboard.
     * Basically </head><body><table>
     */
    public void startKeyboard()
    {
        try
        {
            println("</head>");
            println("<body>");
            println("<table cellspacing=0 cellpadding=0 border=1>");
        }
        catch (IOException e) {
            handleException(e);
        }
    }

    /** Start a row for the keyboard, i.e., <tr> */
    public void startRow()
    {
        try
        {
            println("<tr>");
            // Start each key row with a padding column;
            // a key takes up three table rows, and this height
            // is approximately 1/3 the height of the key.
            println("<td width=8 height=21 ></td>");
        }
        catch (IOException e) {
            handleException(e);
        }
    }

    public void key(String keycode)
    {
        try
        {
            println("<td colspan=3 rowspan=3>");
            println("<button ontouchstart=\"keyDown('Divide')\" ontouchend=\"keyUp('Divide')\" ><img src='keys/KP_DIVIDE.svg' /></button>");
            println("</td>");
        }
        catch (IOException e) {
            handleException(e);
        }
    }

    /** Start a row for the keyboard, i.e., </tr> */
    public void endRow()
    {
        try
        {
            println("</tr>");
            // print two more rows containing a single padding column so that the 3x3 table layout works
            println("<tr><td height=21 ></td></tr>");
            println("<tr><td height=21 ></td></tr>");
        }
        catch (IOException e) {
            handleException(e);
        }
    }

    /** The end of the keyboard, i.e. </table> */
    public void endKeyboard()
    {
        try
        {
            println("</table>");
        }
        catch (IOException e) {
            handleException(e);
        }
    }

    /** The end of the html, i.e. </body></html> */
    public void endHtml()
    {
        copyResource("/endPage.htmlt");
    }

}
