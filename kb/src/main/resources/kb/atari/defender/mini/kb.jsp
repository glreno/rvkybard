<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.jsp.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"Defender Copilot",6*3+2,4*3+2,null);
    kb.loadDefault("COPYRIGHTMESSAGE", "ATARI, Defender, and the ATARI logo are trademarks of Atari Interactive Inc.");

    // Standard colours
    String BACKCOLOR= "#434343"; // 67,67,67
    String KEYCOLOR=  "#776047"; // 119,96,71
    String SHIFTCOLOR="#CF8710"; // 207,135,16
    String CTRLCOLOR= "#FFC640"; // 255,198,64
    String TXTCOLOR=  "#D6D2CE"; // 214,210,206
    String CTRLCOLORD="255,198,64";

    // Default key SVG and size
    String DA="atari/keys/";
    String DS="std/keys/";
    String D2="atari/raiders/keys/";
    kb.setDefaultSvg(DA+"key.svgt",3,3,"FS=48","FS2=16","BORD=4","BORDC="+TXTCOLOR,"BGC="+KEYCOLOR,"TXTC="+TXTCOLOR,"SHFBGC="+SHIFTCOLOR,"CTLBGC="+CTRLCOLOR,"CTRLCOLORD="+CTRLCOLORD,"FIXSY=0","FIXLY=0");
    String KM=DA+"keyMenu.svgt";
    String KLED=DS+"led.svgt";
    String MK=DA+"key2.svgt";

    kb.startHtml();
%>
<style>
.kybard-container {
    background-color: rgb(67,67,67);
}
.kybard-menu-container {
    background-color: rgb(98,48,48);
}
.kbbuttonDown .depress {
  fill: #21d5db !important;
}
.CONTACTLOST-LED-ON {
  fill: #DB1049;
}
.CONTACTLOST-LED-OFF {
  fill: none;
}
.USBLOST-LED-ON {
  fill: #FFC640;
}
.USBLOST-LED-OFF {
  fill: #D6D2CE;
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

    // ESC(pause) H(hyperspace) Option
    kb.startRow();
    kb.spacer(1);
    kb.key("H","KB_H",6,6,null,null,"",D2+"keyH.svgt","S2=HYPERSPACE");
    kb.key("ESC","KB_ESCAPE",6,6,null,null,"", D2+"keyPause.svgt","S2=PAUSE");
    kb.spacer(2);
    kb.key("OPTION","KB_F5",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // Select
    kb.startRow();
    kb.spacer(15);
    kb.key("SELECT","KB_F6",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // Space   Start
    kb.startRow();
    kb.spacer(1);
    kb.key(" ","KB_SPACE",6,6,null,null,"",D2+"keyB.svgt","S2=SMART BOMB");
    kb.spacer(8);
    kb.key("START","KB_F7",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // Menu
    kb.startRow();
    kb.spacer(15);
    kb.key("MENU","",4,3,"panic()",kb.MENU,"",KM,"FS=16","BGC="+SHIFTCOLOR);
    kb.endRow();

    //
    // Keyboard is finished
    //
    kb.endKeyboard();

    // Define the popup menu
    kb.menu();

    kb.endHtml();
%>
