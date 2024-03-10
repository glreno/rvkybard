<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="java.io.*" %>
<%@ page import="org.apache.commons.io.FileUtils" %>
<%@ page import="java.nio.charset.Charset" %>
<!DOCTYPE html>
<html>
<body>
<table>
<%
    // List all directories under /kb
    // Each dir should contain a desc.html and a kb.jsp
    // Verify that, and add it to the table.
    File sub=new File("webapps/ROOT/kb");
    File [] dirs = sub.listFiles();
    for(File d: dirs)
    {
        if ( d.isDirectory())
        {
            File name=new File(d,"name.txt");
            File desc=new File(d,"desc.html");
            File kbd=new File(d,"kb.jsp");
            if ( desc.exists() && kbd.exists() )
            {
                String n = d.getName();
                String descContent="";
                try
                {
                    descContent=FileUtils.readFileToString(desc, Charset.defaultCharset());
                }
                catch(IOException e) {}
                String nameContent="";
                try
                {
                    nameContent=FileUtils.readFileToString(name, Charset.defaultCharset());
                }
                catch(IOException e) {}
                
%>
<tr>
<td><a href="/kb/<%=n%>/kb.jsp"><%=nameContent%></a><td>
<td><%=descContent%></td>
</tr>
<%
            }
        }
    }
%>
</table>
</body>
</html>
