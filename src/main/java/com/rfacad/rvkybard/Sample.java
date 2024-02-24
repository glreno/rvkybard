package com.rfacad.rvkybard;

import java.io.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rfacad.rvkybard.interfaces.KybardFlag;

//
// Copyright (c) 2024 Gerald Reno, Jr.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//

public class Sample extends HttpServlet
{
    private static final long serialVersionUID = 1L;

	public static void main(String [] args) throws IOException
    {
        String dest = "/dev/hidg0";
        if ( args.length > 0 )
        {
            dest = args[0];
        }
        try ( OutputStream out = new FileOutputStream(dest) )
        {
        	KybardSender sender = new KybardSender(out);
        	pause();
        	sender.sendKey((byte)4); // a
        	pause();
        	sender.sendReleaseAllKeys();
        	pause();
        	sender.sendKey(KybardFlag.LEFT_SHIFT.getBits(),(byte)14); // J
        	sender.sendReleaseAllKeys();
        }
    }
    private static void pause()
    {
		try
		{
			Thread.sleep(1000);
		}
		catch(InterruptedException e)
		{
			
		}
		
	}
	@Override
    public void init(ServletConfig config) throws ServletException
    {
    
    }
    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	Sample.main(new String[0]);
    }
}
