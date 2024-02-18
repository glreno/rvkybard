package com.rfacad.rvkybard;

import java.io.*;

//
// Copyright (c) 2024 Gerald Reno, Jr.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//

public class Sample
{
    public static void main(String [] args) throws IOException
    {
        byte [] allUp = new byte[] { 0,0,0,0, 0,0,0,0 };
        byte [] buf = new byte[8];
        String dest = "/dev/hidg0";
        if ( args.length > 0 )
        {
            dest = args[0];
        }
        try ( OutputStream out = new FileOutputStream(dest) )
        {
            buf[0]=0; buf[3]=4; // a
            out.write(buf);
            out.write(allUp);
            buf[0]=32; buf[3]=5; // B
            out.write(buf);
            out.write(allUp);
        }
    }
}
