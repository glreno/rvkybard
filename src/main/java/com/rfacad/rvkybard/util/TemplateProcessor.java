package com.rfacad.rvkybard.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class TemplateProcessor
{

    protected Map<String,String> defaultParams;

    public TemplateProcessor()
    {
        defaultParams=new HashMap<>();
    }

    public void loadDefault(String key,String value)
    {
        defaultParams.put(key, value);
    }
    public void loadDefaults(String [] descArray)
    {
        defaultParams.putAll(parseParams(descArray));
    }

    public Map<String,String> parseParams(String[] descArray)
    {
        Map<String,String> ret = new HashMap<>();
        for(String s : descArray)
        {
            int eq=s.indexOf("=");
            if ( eq > 0 )
            {
                ret.put(s.substring(0,eq), s.substring(eq+1));
            }
        }
        return ret;
    }

    public void processStream(BufferedReader in, Writer out, Map<String,String> params) throws IOException
    {
        // Read the file entirely into a buffer
        StringBuilder buf=new StringBuilder();
        String s;
        while((s=in.readLine()) != null)
        {
            buf.append(s);
            buf.append("\r\n");
        }
        // Process the expressions
        String output = processLine(buf.toString(), params);
        // Write the buffer out
        out.write(output);
    }

    protected String processLine(String s, Map<String,String> params)
    {
        StringBuilder b = new StringBuilder(s.length());
        int x=0;
        int end=s.length();
        while ( x < end )
        {
            int open = s.indexOf("@{",x);
            if ( open < 0 )
            {
                b.append(s.substring(x));
                x=end;
            }
            else
            {
                b.append(s.substring(x,open));
    
                int close = s.indexOf('}',open);
                if ( close < 0 )
                {
                    b.append(s.substring(open));
                    x=end;
                }
                else
                {
                    String k=s.substring(open+2,close);
                    String v = params.get(k);
                    if ( v != null )
                    {
                        b.append(v);
                    }
                    else
                    {
                        v = defaultParams.get(k);
                        if ( v != null )
                        {
                            b.append(v);
                        }
                    }
                    x=close+1;
                }
            }
        }
        return b.toString();
    }

}