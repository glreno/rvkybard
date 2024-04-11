package com.rfacad.rvkybard.sender;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rfacad.rvkybard.interfaces.KybardLed;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
public class KybardReader implements Runnable
{
    Logger LOG = LoggerFactory.getLogger(KybardReader.class);

    protected String dev=null;
    protected InputStream in; // very much NOT buffered
    protected Thread thr;
    protected int lastRead=-1;

    public void setDev(String dev)
    {
        this.dev=dev;
    }

    public int getLastRead()
    {
        return lastRead;
    }

    public String getCurrentFlags()
    {
        int b = lastRead;
        if ( b <= 0 )
        {
            return("{\"NUMLOCK\":false,\"CAPSLOCK\":false,\"SCROLLLOCK\":false,\"COMPOSE\":false,\"KANA\":false}");
        }
        StringBuilder buf=new StringBuilder();
        buf.append("{");
        boolean first = true;
        for (KybardLed k : KybardLed.values() )
        {
            if ( k != KybardLed.UNDEFINED )
            {
                if ( first )
                {
                    first=false;
                }
                else
                {
                    buf.append(',');
                }
                boolean on = ( (k.getBits() & b) != 0 );
                buf.append('"');
                buf.append(k.toString());
                buf.append("\":");
                buf.append(on);
            }
        }
        buf.append('}');
        return buf.toString();
    }

    public void run()
    {
        LOG.debug("Thread starting");
        while ( in != null )
        {
            try
            {
                int b = in.read();
                if ( b >= 0)
                {
                    lastRead=b;
                    LOG.debug("Read byte {}",lastRead);
                }
            }
            catch (IOException e)
            {
                LOG.error("Read failed.", e);
                lastRead=-2;// a flag indicating that there was a failure
            }
        }
        LOG.debug("Thread exting");
    }

    protected void doClose()
    {
        InputStream o = in;
        in = null;
        if ( o != null )
        {
            try
            {
                o.close();
            }
            catch (IOException e)
            {
                LOG.error("Failed to close stream",e);
            }
        }
    }

    public synchronized void init()
    {
        if ( dev == null )
        {
            LOG.info("Not starting -- dev not specified");
        }
        else
        {
            LOG.debug("Starting");
            try
            {
                in = new FileInputStream(dev);
                startThread();
            }
            catch (IOException e)
            {
                LOG.error("Failed to open stream",e);
            }
            LOG.info("Startup complete");
        }
    }

    public synchronized void startThread()
    {
        thr = new Thread(this);
        thr.start();
    }

    public synchronized void shutdown()
    {
        LOG.debug("Shutting down");
        doClose();
        // This doesn't actually work. FileInputStream.read() isn't interruptable.
        Thread t = thr;
        if ( t != null )
        {
            t.interrupt();
        }
        LOG.info("Shutdown complete");
    }

}
