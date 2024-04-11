package com.rfacad.rvkybard.sender;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

public class KybardReaderTest
{
    @Test(timeout=5000)
    public void shouldCloseInputStream() throws IOException
    {
        // Need to bypass init and use our own InputStream for this
        KybardReader kr = new KybardReader("");
        InputStream in = mock(InputStream.class);
        kr.in=in;
        assertEquals(-1,kr.getLastRead());
        kr.shutdown();
        assertEquals(-1,kr.getLastRead());
        verify(in,times(1)).close();
        // run() should NOT block here!
        kr.run();
        assertEquals(-1,kr.getLastRead());
    }

    @Test(timeout=5000)
    public void shouldCloseInputStreamEx() throws IOException
    {
        // Same thing, but close throws an exception
        KybardReader kr = new KybardReader("");
        InputStream in = mock(InputStream.class);
        doThrow(new IOException("xx")).when(in).close();
        kr.in=in;
        assertEquals(-1,kr.getLastRead());
        kr.shutdown();
        assertEquals(-1,kr.getLastRead());
        verify(in,times(1)).close();
        // run() should NOT block here!
        kr.run();
        assertEquals(-1,kr.getLastRead());
    }

    @Test(timeout=5000)
    public void shouldStopReadingAfterError() throws IOException
    {
        // Note that this tests an IOException, but closing a FileInputStream does not cause
        // read() to throw an exception. And read() doesn't respond to InterruptedException in real life.
        // This test therefore does NOT reflect a normal shutdown.

        // Create an InputStream that will block read() until a Latch is released.
        CountDownLatch cd = new CountDownLatch(1);
        KybardReader kr = new KybardReader("");
        InputStream in = new InputStream() {
            @Override
            public int read() throws IOException
            {
                try
                {
                    cd.await();
                } catch (InterruptedException e)
                {
                    throw new IOException("interrupted");
                }
                return 65;
            }
        };
        kr.in=in;
        assertEquals(-1,kr.getLastRead());
        kr.startThread(); // this should immediately wait on the latch
        pause();
        kr.shutdown(); // this should close the inputstream with an IOException, causing it to return with lastread=-2
        pause();
        assertEquals(-2,kr.getLastRead());
        cd.countDown();
        pause();
        assertEquals(-2,kr.getLastRead());
        // At no point does it actually read that value
    }

    @Test(timeout=5000)
    public void shouldReadFile() throws IOException
    {
        // Same thing, but close throws an exception
        KybardReader kr = new KybardReader();
        kr.setDev("src/test/resources/flags1.txt");
        assertEquals(-1,kr.getLastRead());
        kr.init();
        pause();
        assertEquals(65,kr.getLastRead());
        kr.shutdown();
        // that sets in=null, causing it to either exit with the lastRead unchanged
        // or triggering a StreamClosed exception and returning with a -2
        pause();
    }

    private void pause()
    {
        try
        {
            Thread.sleep(100);
        }
        catch(InterruptedException e)
        {
        }
    }
}
