package com.rfacad.rvkybard;

public class KybardJspHelper
{
    public KybardJspHelper(String title,String favIcoFn)
    {
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
    public String startHtml()
    {
        return "";
    }

    /** The end of the javascript and header HTML, and the beginning of the keyboard.
     * Basically </script></head><body><table>
     * @return
     */
    public String startKeyboard()
    {
        return "";
    }

    /** Start a row for the keyboard, i.e., <tr> */
    public String startRow()
    {
        return "";
    }

    public String key(String keycode)
    {
        return "";
    }

    /** Start a row for the keyboard, i.e., </tr> */
    public String endRow()
    {
        return "";
    }

    /** The end of the keyboard, i.e. </table> */
    public String endKeyboard()
    {
        return "";
    }

    /** The end of the html, i.e. </body></html> */
    public String endHtml()
    {
        return "";
    }

}
