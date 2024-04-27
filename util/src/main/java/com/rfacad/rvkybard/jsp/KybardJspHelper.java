package com.rfacad.rvkybard.jsp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rfacad.rvkybard.util.TemplateProcessor;

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

    public static final String SHIFT="shifty!";

    private Writer out;

    private File top;
    private boolean mouseMode=false;

    private String templateFn=null;
    private int keyColSpan=3;
    private int keyRowSpan=3;
    private int cellW=20;
    private int cellH=20;
    private double pixelSize=1;
    private int gridGap=2;
    private int gridCols;
    private int gridRows;
    private int menuCols;
    private int menuRows;

    private int x=1;
    private int y=1;
    TemplateProcessor templateProcessor = new TemplateProcessor();

    public KybardJspHelper(Writer out, String title, String favIcoFn)
    {
        this(out,title,14,5,favIcoFn);
    }
    // tricky: you spcecify the size in grid cells, NOT keys. setDefaultSvg sets the key size in grid cells (default 3x3)
    public KybardJspHelper(Writer out, String title,int gridCols, int gridRows, String favIcoFn)
    {
        this.out = out;
        this.gridCols=gridCols;
        this.gridRows=gridRows;
        this.menuCols=5*3;
        this.menuRows=gridRows-1;
        top = new File("webapps/ROOT/kb"); // TODO we are assuming that we are running under Tomcat as the ROOT webapp!
        templateProcessor.loadDefault("TITLE", title);
        calculateDefaultSizes();
    }


    public void setTop(File top)
    {
        this.top=top;
    }

    public void setMouseMode(boolean mouseMode)
    {
        this.mouseMode = mouseMode;
    }

    private void println(String s) throws IOException
    {
        out.write(s);
        // Yes, HTTP specifies that this should be \r\n. HTTP is wrong.
        out.write('\n');
        out.flush();
    }

    protected void handleException(Exception e)
    {
        LOG.error("Exception generating keyboard",e);
        // TODO the page should throw a 500 at this point!
    }

    private void copyResource(String resourceName, Map<String, Object> params)
    {
        URL resource = this.getClass().getResource(resourceName);
        try (InputStream in = resource.openStream())
        {
            templateProcessor.processStream(resourceName, in, out, params);
        }
        catch(IOException e)
        {
            handleException(e);
        }
    }

    public void setDefaultSvg(String templateFn, int keyWidthPixels, int keyHeightPixels, int keyColSpan, int keyRowSpan, String ... svgParams)
    {
        this.templateFn = templateFn;
        this.keyColSpan = keyColSpan;
        this.keyRowSpan = keyRowSpan;
        templateProcessor.loadDefault("W",""+keyWidthPixels);
        templateProcessor.loadDefault("H",""+keyHeightPixels);
        calculateDefaultSizes();
        templateProcessor.loadDefaults(svgParams);
    }
    public void setDefaultSvg(String templateFn, int keyColSpan, int keyRowSpan, String ... svgParams)
    {
        this.templateFn = templateFn;
        this.keyColSpan = keyColSpan;
        this.keyRowSpan = keyRowSpan;
        calculateDefaultSizes();
        templateProcessor.loadDefaults(svgParams);
    }

    public void setDefaultCellSize(int cellW,int cellH,double pixelSize,int gridGap)
    {
        this.cellW=cellW;
        this.cellH=cellH;
        this.gridGap=gridGap;
        this.pixelSize=pixelSize;
        calculateDefaultSizes();
    }

    private void calculateDefaultSizes()
    {
        templateProcessor.loadDefault("cellW",cellW);
        templateProcessor.loadDefault("cellH",cellH);
        templateProcessor.loadDefault("P",pixelSize);
        templateProcessor.loadDefault("gridRows",gridRows);
        templateProcessor.loadDefault("gridCols",gridCols);
        templateProcessor.loadDefault("menuRows",menuRows);
        templateProcessor.loadDefault("menuCols",menuCols);
        templateProcessor.loadDefault("gridGap",gridGap);
        templateProcessor.loadDefault("stdW",(cellW+gridGap)*keyColSpan - gridGap);
        templateProcessor.loadDefault("stdH",(cellH+gridGap)*keyRowSpan - gridGap);
        // The kbd pixel sizes are two gridGaps larger than they should be, to allow some overflows from the buttons
        templateProcessor.loadDefault("kbdW",(cellW+gridGap)*gridCols + gridGap);
        templateProcessor.loadDefault("kbdH",(cellH+gridGap)*gridRows + gridGap);
        templateProcessor.loadDefault("menuW",(cellW+gridGap)*menuCols + gridGap);
        templateProcessor.loadDefault("menuH",(cellH+gridGap)*menuRows + gridGap);
        templateProcessor.loadDefault("stdColSpan",keyColSpan);
        templateProcessor.loadDefault("stdRowSpan",keyRowSpan);
    }

    public void loadDefault(String key,Object value)
    {
        templateProcessor.loadDefault(key, value);
    }

    public int getX()
    {
        return x;
    }


    public void setX(int x)
    {
        this.x = x;
    }


    public int getY()
    {
        return y;
    }


    public void setY(int y)
    {
        this.y = y;
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
        Map<String, Object> params = Collections.emptyMap();
        // Copy initial HTML from htmlt file
        copyResource("/startPage.htmlt",params);
    }

    /** The end of the header HTML, and the beginning of the keyboard.
     * Basically <table>
     */
    public void startKeyboard()
    {
        startKeyboard("kybard-container","kybard-main");
    }

    public void startKeyboard(String classes, String id)
    {
        x=1;
        y=1;
        try
        {
            println("<div class='"+classes+"' id='"+id+"' >");
        }
        catch (IOException e) {
            handleException(e);
        }
    }

    /** Start a row for the keyboard, i.e., <tr> */
    public void startRow()
    {
        x=1;
    }

    /**
     * Insert a spacer into the row.
     */
    public void spacer(int cols)
    {
        x+=cols;
    }

    /**
     * This is the easy version, so you can just do key("A");
     * @param letter to appear on the key
     */
    public void key(String keycode)
    {
        key(keycode,keycode,keyColSpan,keyRowSpan,null,null,"",null);
    }

    /**
     * This is the simple version, so you can just do key("/","KP_DIVIDE");
     * @param letter to appear on the key
     */
    public void key(String name,String keycode)
    {
        key(name,keycode,keyColSpan,keyRowSpan,null,null,"",null);
    }

    // A fully-detailed key would be:
    // kb.key("Tab","KB_TAB",5,3,"custTabDown('KB_Tab')","custTabUp('KB_Tab')","background='green' foreground='red'","kbtab.svgt","W=128");
    //      "Tab" -- name of key; default text in SVG, keycode unless otherwise specified
    //      "KB_TAB" -- Keycode to send, unless custom method used; this is sent as a String to the servlet, which will use KybardCode.valueOf to find the actual byte to e
    //      5,3 -- column span and height
    //      custom down and up methods
    //      "background='green' foreground='red'" css style overrides for the <button>
    //      kbtab.svgt -- image template to use if not default
    //      "W=128" -- image parameter overrides
    /**
     * 
     * @param keycode
     * @param svgText
     */
    public void key(String name,String keycode,int colspan,int rowspan,String custDown,String custUp,String cssClass,String svgTemplateFn,String ... svgParams)
    {
        String down=custDown;
        if ( down == null )
        {
            down =  "keyDown(this,'"+keycode+"')";
        }
        else if ( SHIFT.equals(custDown) )
        {
            down =  "flagDown(this,'"+keycode+"')";
        }

        String up=custUp;
        if ( up == null )
        {
            up =  "keyUp(this,'"+keycode+"')";
        }
        else if ( SHIFT.equals(custUp) )
        {
            up =  "flagUp(this,'"+keycode+"')";
        }

        Map<String,Object> keyParams = new HashMap<>();
        keyParams.put("name", name);
        keyParams.put("X", x);
        keyParams.put("Y", y);
        keyParams.put("colSpan", colspan);
        keyParams.put("rowSpan", rowspan);
        keyParams.put("W", (cellW+gridGap)*colspan-gridGap );
        keyParams.put("H", (cellH+gridGap)*rowspan-gridGap );
        try
        {
            StringBuilder buf = new StringBuilder();
            divstyle(buf, "key", colspan, rowspan);
            buf.append("<button type='button' class='kbbutton");
            if ( cssClass != null && !cssClass.isEmpty() )
            {
                buf.append(" ");
                buf.append(cssClass);
            }
            buf.append("'");
            buf.append(mouseMode?" onmousedown=":" ontouchstart=");
            buf.append('"');
            buf.append(down);
            buf.append('"');
            buf.append(mouseMode?" onmouseup=":" ontouchend=");
            buf.append('"');
            buf.append(up);
            buf.append('"');

            buf.append(' ');
            buf.append(">");
            out.write(buf.toString());

            embedSvg(name,svgTemplateFn,keyParams,svgParams);

            println("</button></div>");
        }
        catch (IOException e) {
            handleException(e);
        }
        x += colspan;
    }
    private void divstyle(StringBuilder buf, String classes, int colspan, int rowspan)
    {
        buf.append("<div class='");
        buf.append(classes);
        buf.append("' style='grid-area: "); // y/x/span r/span c;'>5</div>
        buf.append(y);
        buf.append("/");
        buf.append(x);
        buf.append("/span ");
        buf.append(rowspan);
        buf.append("/span ");
        buf.append(colspan);
        buf.append(";'>");
    }

    protected void embedSvg(String name, String svgTemplateFn, Map<String,Object> keyParams, String[] svgParams) throws IOException
    {
        String fn = (svgTemplateFn==null)?this.templateFn:svgTemplateFn;

        File f = new File(top,fn);
        try ( InputStream rsrc = new FileInputStream(f) )
        {
            Map<String, Object> params = new HashMap<>();
            params.putAll(keyParams);
            params.putAll(templateProcessor.parseParams(svgParams));
            templateProcessor.processStream(fn, rsrc, out, params);
        }
    }

    public void notKey(String cssClass,int colspan,int rowspan,String html)
    {
        try
        {
            StringBuilder buf = new StringBuilder();
            divstyle(buf, cssClass, colspan, rowspan);
            buf.append(html);
            buf.append("</div>");
            println(buf.toString());
        }
        catch (IOException e) {
            handleException(e);
        }
        x += colspan;

    }

    /** End a row for the keyboard, i.e., </tr> */
    public void endRow()
    {
        endRowThirds(3);
    }
    public void endRowThirds(int n)
    {
        y+=n;
    }

    /** The end of the keyboard, i.e. </table> */
    public void endKeyboard()
    {
        try
        {
            println("</div>");
        }
        catch (IOException e) {
            handleException(e);
        }
    }

    /** Standard Keyboard Popup Menu */
    public void menu()
    {
        String ATARISHIFT="SHFBGC=#CF8710"; // 207,135,16
        String ATARIKEY="BGC=#776047"; // 119,96,71
        startMenu();
        menuClose("atari/keys/key2.svgt",ATARIKEY,ATARISHIFT);
        startRow();
        // blank row
        endRow();
        menuReturnToMain("atari/keys/key2.svgt","std/keys/led.svgt",ATARIKEY,ATARISHIFT);
        menuLogout("atari/keys/key2.svgt","std/keys/led.svgt",ATARIKEY,ATARISHIFT);
        endKeyboard();
    }

    /** Menu is just another keyboard, but with a different class. Follow startMenu() with key definitions,
     * including the all important one calling closeMenu(), and then finally endKeyboard() */ 
    public void startMenu()
    {
        // this calculation has to be in the ctor to get the numbers into the top style
        calculateDefaultSizes();
        startKeyboard("kybard-menu-container","kybard-menu");
    }
    public void menuClose(String mk,String keybg,String keyhi)
    {
        startRow();
        endRowThirds(1);
        startRow();
        spacer(10);
        key("X","",4,3,"doNothing()","closeMenu()","",mk,"S=Close",keybg,keyhi);
        endRowThirds(1);
        startRow();
        spacer(1);
        notKey("CONTACT-STATUS-TEXT",10,1,"connection status pending");
        endRowThirds(2);

    }

    public void menuReturnToMain(String mk, String kled,String keybg,String keyhi)
    {
        startRow();
        spacer(1);
        key("M","",7,3,"doNothing()","mainMenu()","",mk,"S=Main Menu",keybg,keyhi);
        endRow();
    }

    public void menuLogout(String mk, String kled,String keybg,String keyhi)
    {
        String ledbg="BGC=#776047";
        startRow();
        spacer(1);
        key("L","",7,3,"doNothing()","doLogout()","",mk,"S=Logout",keybg,keyhi);
        spacer(3);
        notKey("",2,1,"CAPS");
        key("","",1,1,"doNothing()","doNothing()","",kled,"CLS=CAPSLOCK-LED",ledbg);
        endRowThirds(1);
        startRow();
        spacer(11);
        notKey("",2,1,"NUM");
        key("","",1,1,"doNothing()","doNothing()","",kled,"CLS=NUMLOCK-LED",ledbg);
        endRowThirds(1);
        startRow();
        spacer(11);
        notKey("",2,1,"SCRL");
        key("","",1,1,"doNothing()","doNothing()","",kled,"CLS=SCROLLLOCK-LED",ledbg);
        endRowThirds(1);
    }

    /** The end of the html, i.e. </body></html> */
    public void endHtml()
    {
        copyResource("/endPage.htmlt", Collections.emptyMap());
    }

}
