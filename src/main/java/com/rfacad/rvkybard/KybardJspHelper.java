package com.rfacad.rvkybard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;

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
            e.printStackTrace();
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
    }

    /** The end of the javascript and header HTML, and the beginning of the keyboard.
     * Basically </script></head><body><table>
     */
    public void startKeyboard()
    {
    }

    /** Start a row for the keyboard, i.e., <tr> */
    public void startRow()
    {
    }

    public void key(String keycode)
    {
    }

    /** Start a row for the keyboard, i.e., </tr> */
    public void endRow()
    {
    }

    /** The end of the keyboard, i.e. </table> */
    public void endKeyboard()
    {
    }

    /** The end of the html, i.e. </body></html> */
    public void endHtml()
    {
        copyResource("/endPage.htmlt");
    }

}
