package com.rfacad.rvkybard.sender;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public void run()
    {
        LOG.info("Thread starting");
        while ( in != null )
        {
            try
            {
                int b = in.read();
                if ( b >= 0)
                {
                    lastRead=b;
                    LOG.info("Read byte {}",lastRead);
                }
            }
            catch (IOException e)
            {
                LOG.error("Read failed.", e);
                lastRead=-2;// a flag indicating that there was a failure
            }
        }
        LOG.info("Thread exting");
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
            LOG.info("Starting");
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
        LOG.info("Shutting down");
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
