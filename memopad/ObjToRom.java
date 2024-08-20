import java.io.*;

/** Convert an Atari .OBJ file into raw binary.
* Usage: java ObjToRom fname.obj fname.rom
*/
public class ObjToRom
{
    public static void main(String [] args) throws IOException
    {
        File inf=new File(args[0]);
        File outf=new File(args[1]);
        ObjToRom o = new ObjToRom(inf,outf);
        o.read();
        o.write();
    }

    private File inf;
    private File outf;
    private int first;
    private int last;
    private byte [] buf;

    public ObjToRom(File inf,File outf)
    {
        this.inf = inf;
        this.outf = outf;
        this.buf = new byte[65536];
        this.first = 65535;
        this.last = 0;
    }

    public void read() throws IOException
    {
        try(InputStream in=new FileInputStream(inf))
        {
            // Read and ignore two-byte header 0xffff
            in.read();
            in.read();
            while (readblock(in))
                ;
        }
    }

    private boolean readblock(InputStream in) throws IOException
    {
        // Read start address
        int slo=in.read();
        if ( slo<0 ) return false;
        int shi=in.read();
        if ( shi<0 ) return false;
        int s = shi*256+slo;
        int elo=in.read();
        if ( elo<0 ) return false;
        int ehi=in.read();
        if ( ehi<0 ) return false;
        int e = ehi*256+elo;
        if ( e<s ) return false;
        if ( s<first )
        {
            first=s;
        }
        if ( e>last)
        {
            last=e;
        }
        return readblock(in,s,e);
    }

    private boolean readblock(InputStream in,int s,int e) throws IOException
    {
        System.err.println("Reading block "+Integer.toHexString(s)+" to "+Integer.toHexString(e));
        for(int i=s;i<=e;i++)
        {
            int c = in.read();
            if ( c<0 ) return false;
            buf[i]=(byte)(c&0xff);
        }
        return true;
    }

    public void write() throws IOException
    {
        if ( last < first )
        {
            System.err.println("Nothing to write!");
        }
        else
        {
            System.err.println("Writing block "+Integer.toHexString(first)+" to "+Integer.toHexString(last));
            try(OutputStream out=new FileOutputStream(outf))
            {
                out.write(buf,first,last-first+1);
            }
        }
    }
}
