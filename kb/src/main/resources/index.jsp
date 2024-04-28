<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.jsp.KybardJspHelper"%>
<%@ page import="com.rfacad.rvkybard.interfaces.MouseMode"%>
<%@ page import="com.rfacad.rvkybard.index.IndexHelper"%>
<%@ page import="com.rfacad.rvkybard.index.Theme"%>
<%@ page import="com.rfacad.rvkybard.index.Keyboard"%>
<%@ page import="com.rfacad.rvkybard.index.Protocol"%>
<%
    String BACKCOLOR= "#434343"; // 67,67,67
    String KEYCOLOR=  "#776047"; // 119,96,71
    String SHIFTCOLOR="#CF8710"; // 207,135,16
    String CTRLCOLOR= "#FFC640"; // 255,198,64
    String TXTCOLOR=  "#D6D2CE"; // 214,210,206

    KybardJspHelper kb=new KybardJspHelper(out,"rvkybard",8*3+2,3+2,null);
    kb.setMouseMode(MouseMode.CLICK);
    kb.setMenuRows(2*3+2);
    kb.setDefaultSvg("atari/keys/key2.svgt",3,5+3,"FS=48","BORD=4","BORDC="+TXTCOLOR,"BGC="+KEYCOLOR,"TXTC="+TXTCOLOR,"SHFBGC="+SHIFTCOLOR);

	kb.startHtml();
%>
<style>
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
  <div style='grid-area: 1/1/span 1/span 1;'><img src='closed17.svg' onclick="menuOpenClose(this,'kybard-menu-body-<%=id%>')"/></div>
  <div style='grid-area: 1/2/span 1/span 2;'><%=keyboard.getName()%></div>
</div>
<div class='kybardidx-menu-body' id='kybard-menu-body-<%=id%>' style='grid-template-rows: auto repeat(<%=nrow%>,30px);' >
  <div style='grid-area: 1/2/span 4/span 1;'><%=keyboard.getSnapshotOrBlank()%></div>
  <div style='grid-area: 1/3/span 1/span 1;'><%=keyboard.getDescription()%></div>
<%
            int pid=1;
            for(Protocol p : keyboard.getProtocols())
            {
                ++pid;
%>
  <div style='grid-area: <%=pid%>/3/span 1/span 1;'><a href="/kb/<%=p.getLink()%>"><%=p.getName()%></a></div>
<%
            }
%>
</div>
<%
        }
    }

    // A little tiny kybard to pop up a settings menu
    kb.startKeyboard();
    kb.startRow();
    kb.key("MENU","",6,3,"panic()","menu()","",null,"name=","S=Settings");
    kb.endRow();
    kb.endKeyboard();

    String ATARISHIFT="SHFBGC=#CF8710"; // 207,135,16
    String ATARIKEY="BGC=#776047"; // 119,96,71
    kb.startMenu();
    kb.menuClose(null,ATARIKEY,ATARISHIFT);
    kb.menuLogout(null,"std/keys/led.svgt",ATARIKEY,ATARISHIFT);
    kb.endKeyboard();


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
<%
	kb.endHtml();
%>
