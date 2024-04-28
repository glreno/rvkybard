package com.rfacad.rvkybard.interfaces;

public enum MouseMode
{
    TOUCH("ontouchstart","ontouchend"),
    MOUSE("onmousedown","onmouseup"),
    CLICK("onmousedown","onclick");

    private String down;
    private String up;

    MouseMode(String down, String up)
    {
        this.down=down;
        this.up=up;
    }

    public String getDown()
    {
        return down;
    }

    public String getUp()
    {
        return up;
    }
}
