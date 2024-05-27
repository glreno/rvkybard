<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.jsp.KybardJspHelper"%>
<%@ page import="com.rfacad.rvkybard.interfaces.MouseMode"%>
<%@ page import="com.rfacad.rvkybard.index.IndexHelper"%>
<%@ page import="com.rfacad.rvkybard.index.Theme"%>
<%@ page import="com.rfacad.rvkybard.index.Keyboard"%>
<%@ page import="com.rfacad.rvkybard.index.Protocol"%>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"rvkybard",8+12+6,3,null);
    kb.setMouseMode(MouseMode.CLICK);
    kb.setDefaultSvg("artdeco/keys/keywide.svgt",3,3,"BORD=4","BORDC=#CD7F32","BGC=#eec","TXTC=#000","FS=16");
    kb.setDefaultCellSize(10,10,1,2);

	kb.startHtml();
%>
<style>
.logo {
  display: block;
  margin-left: auto;
  margin-right: auto;
}
.kybardidx-menu-title {
  display: grid;
  grid-template-columns: 30px auto;
  grid-template-rows: 30px;
  gap: 2px;
  width: 100%;
  z-index: 0;
}
.kybardidx-menu-body {
  display: grid;
  grid-template-columns: 40px 350px auto;
  gap: 2px;
  width: 100%;
  height: 0px;
  z-index: 0;
  visibility: collapse;
}
.kybard-menu-container {
    background-color: rgb(98,48,48);
}
.footer {
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
        button.src='closed21.svg';
    }
    else
    {
        //console.log('opening '+menuid);
        menu.style.visibility='visible';
        menu.style.height='auto';
        button.src='open21.svg';
    }
}
</script>
</head>
<body style="background-color: #D6D2CE;">
<img class='logo' alt='rvkybard, the retro remote virtual keyboard' src='rvkybard_logo.png' />
<%
    IndexHelper ih=new IndexHelper("webapps/ROOT/kb");
    int themeid=0;
    for(Theme theme : ih.getThemes() )
    {
        ++themeid;
        int kbid=0;
%>
<h3><%=theme.getName()%></h3>
<%
        for(Keyboard keyboard : theme.getKeyboards() )
        {
            ++kbid;
            String id=themeid+"-"+kbid;
            int nprotocols=keyboard.getProtocols().size();
            // The snapshot is 100px high. Set number of 30px grid rows to at least 3.
            int nrow=(nprotocols<3)?3:nprotocols;
%>
<div class='kybardidx-menu-title'>
  <div style='grid-area: 1/1/span 1/span 1;'><img src='closed21.svg' onclick="menuOpenClose(this,'kybard-menu-body-<%=id%>')"/></div>
  <div style='grid-area: 1/2/span 1/span 2;'><%=keyboard.getName()%></div>
</div>
<div class='kybardidx-menu-body' id='kybard-menu-body-<%=id%>' style='grid-template-rows: auto repeat(<%=nrow%>,30px);' >
  <div style='grid-area: 1/3/span 4/span 1;'><%=keyboard.getSnapshotOrBlank()%></div>
  <div style='grid-area: 1/2/span 1/span 1;'><%=keyboard.getDescription()%></div>
<%
            int pid=1;
            for(Protocol p : keyboard.getProtocols())
            {
                ++pid;
%>
  <div style='grid-area: <%=pid%>/2/span 1/span 1;'><a href="/kb/<%=p.getLink()%>"><%=p.getName()%></a></div>
<%
            }
%>
</div>
<%
        }
    }
%>
<br>
<br>
<div class='footer'>
<%
    // a very small keyboard that is just links to other places
    kb.startKeyboard();
    kb.key("Settings","",8,3,"doNothing()","linkTo('/config.jsp')","",null);
    kb.key("Documentation","",12,3,"doNothing()","window.open('https://github.com/glreno/rvkybard/wiki','_blank').focus()","",null);
    kb.key("Source","",6,3,"doNothing()","window.open('https://github.com/glreno/rvkybard','_blank').focus()","",null);
    kb.endKeyboard();
    //kb.endHtml(); that includes endPage.htmlt, which includes the copyright notice for the keyboard. Don't want that here.
%>

<script type="text/javascript" language="javascript">
    panic(); // clear all keys and set LEDs
</script>
<footer>
<br>
rvkybard version ${project.version}
<br>
Copyright &copy; 2024 Gerald Reno, Jr.
</footer>
</body>
</html>

