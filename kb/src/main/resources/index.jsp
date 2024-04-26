<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.index.IndexHelper"%>
<%@ page import="com.rfacad.rvkybard.index.Theme"%>
<%@ page import="com.rfacad.rvkybard.index.Keyboard"%>
<%@ page import="com.rfacad.rvkybard.index.Protocol"%>

<!DOCTYPE html>
<html lang='en'>
<head>
<title>rvkybard</title>
<link
    rel="icon"
    type="image/png"
    href="/icon32.svg"
/>
<style>
.kybard-menu-title {
  display: grid;
  grid-template-columns: 30px auto;
  grid-template-rows: 30px;
  gap: 2px;
  width: 100%;
  z-index: 0;
}
.kybard-menu-body {
  display: grid;
  grid-template-columns: 60px auto;
  gap: 2px;
  width: 100%;
  height: 0px;
  z-index: 0;
  visibility: collapse;
}
.footer {
  position: fixed;
  left: 0;
  bottom: 0;
  width: 100%;
}
</style>
<script type="text/javascript" language="javascript">
function menuOpenClose(button,menuid)
{
    const menu = document.getElementById(menuid);
    if ( menu.style.visibility=='visible' )
    {
        //console.log('closing '+menuid);
        menu.style.visibility='collapse';
        menu.style.height='0px';
        button.src='closed17.svg';
    }
    else
    {
        //console.log('opening '+menuid);
        menu.style.visibility='visible';
        menu.style.height='auto';
        button.src='open17.svg';
    }
}
</script>
</head>
<body>
<h3>rvkybard, the retro remote virtual keyboard</h3>
<%
    // List all directories under /kb
    // Each dir should contain a desc.html and a kb.jsp
    // Verify that, and add it to the table.
    IndexHelper ih=new IndexHelper("webapps/ROOT/kb");
    int id=0;
    for(Theme theme : ih.getThemes() )
    {
        for(Keyboard keyboard : theme.getKeyboards() )
        {
            for(Protocol p : keyboard.getProtocols())
            {
                ++id;
%>
<div class='kybard-menu-title'>
  <div style='grid-area: 1/1/span 1/span 1;'><img src='closed17.svg' onclick="menuOpenClose(this,'kybard-menu-body-<%=id%>')"/></div>
  <div style='grid-area: 1/2/span 1/span 2;'><%=theme.getName()%></div>
</div>
<div class='kybard-menu-body' id='kybard-menu-body-<%=id%>' style='grid-template-rows: repeat(2,30px);' >
  <div style='grid-area: 1/2/span 1/span 1;'><%=keyboard.getName()%></div>
  <div style='grid-area: 2/2/span 1/span 1;'><a href="/kb/<%=p.getLink()%>"><%=p.getName()%></a></div>
</div>
<%
            }
        }
    }
%>
<div class='footer'>
<table>
<tr>
<td><a href=https://github.com/glreno/rvkybard/wiki>Documentation</a></td>
<td></td>
<td><a href=https://github.com/glreno/rvkybard>Source on github</a></td>
</tr><tr><td colspan=3>
rvkybard Copyright &copy; 2024 Gerald Reno, Jr.
</td></tr></table>
</div>
</body>
</html>
