package com.rfacad.rvkybard.interfaces;

public class AuthS
{
    private static AuthS SINGLETON = new AuthS();

    private AuthI authi;

    private AuthS()
    {
        this.authi = null;
    }

    public static void setAuthI(AuthI authi)
    {
        SINGLETON.authi = authi;
    }

    public static AuthI getAuthI()
    {
        return SINGLETON.authi;
    }
}
