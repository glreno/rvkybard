<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.jsp.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"rrvkb logo",5*3+2,1*3+2,null);

    // Standard colours
    String BACKCOLOR= "#434343"; // 67,67,67
    String KEYCOLOR=  "#776047"; // 119,96,71
    String SHIFTCOLOR="#CF8710"; // 207,135,16
    String TXTCOLOR=  "#D6D2CE"; // 214,210,206

    // Default key SVG and size
    String DA="atari/keys/";
    String DS="std/keys/";
    String DART="std/keys/art/";
    kb.setDefaultSvg(DA+"key2f.svgt",3,3,"FS=48","BORD=4","BORDC="+TXTCOLOR,"BGC="+KEYCOLOR,"TXTC="+TXTCOLOR,"SHFBGC="+SHIFTCOLOR,"FS2=30");

    kb.startHtml();
%>
<!-- custom styles go here -->
<!-- note body background matches logo.css .logopage for ease of screenshotting -->
<style>
body {
    background-color: #D6D2CE;
}
.kybard-container {
    background-color: rgb(67,67,67);
    border-radius: 10px;
}
.kybard-menu-container {
    background-color: rgb(98,48,48);
}
</style>
<script type="text/javascript" language="javascript">
    // custom javascript goes here
</script>
</head>
<body>
<%
    kb.startKeyboard();

    //
    // Keyboard rows start here
    //
    kb.startRow();
    kb.endRowThirds(1);

    //  ESC  1..9 0 < > DEL BRK         RESET
    kb.startRow();
    kb.spacer(1);
    kb.key("R","1",3,3,null,null,"",null,"S=remote");
    kb.key("R","2",3,3,null,null,"",null,"S=retro");
    kb.key("V","3",3,3,null,null,"",null,"S=virtual");
    kb.key("K","4",3,3,null,null,"",null,"S=key");
    kb.key("B","5",3,3,null,null,"",null,"S=board");
    kb.spacer(1);
    kb.endRow();

    //
    // Keyboard is finished
    //
    kb.endKeyboard();

    kb.endHtml();
%>
