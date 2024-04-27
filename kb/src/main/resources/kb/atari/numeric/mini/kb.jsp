<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.jsp.KybardJspHelper" %>
<%
    // Page title, and keyboard size in cells. 5 keys * 3 cells wide, 5 keys * 3 cells high
    KybardJspHelper kb=new KybardJspHelper(out,"Atari Keypad",5*3+3,4*3+2,null);
    kb.loadDefault("COPYRIGHTMESSAGE", "ATARI and the ATARI logo are trademarks of Atari Interactive Inc.");
    kb.setMouseMode(false);
    // Standard colours
    String BACKCOLOR= "#434343"; // 67,67,67
    String KEYCOLOR=  "#776047"; // 119,96,71
    String SHIFTCOLOR="#CF8710"; // 207,135,16
    String CTRLCOLOR= "#FFC640"; // 255,198,64
    String TXTCOLOR=  "#D6D2CE"; // 214,210,206

    // Default key SVG and size (in cell spans)
    String DA="atari/keys/";
    String DS="std/keys/";
    kb.setDefaultSvg(DA+"key3.svgt",3,3,"FS=48","BORD=4","BORDC="+TXTCOLOR,"BGC="+KEYCOLOR,"TXTC="+TXTCOLOR,"SHFBGC="+SHIFTCOLOR,"CTLBGC="+CTRLCOLOR,"FIXSY=0","FIXLY=0");
    String K2=DA+"key2.svgt";
    String K3=DA+"key3.svgt";
    String K=DA+"key.svgt";
    String KM=DA+"keyMenu.svgt";
    String KLED=DS+"led.svgt";
    String MK=DA+"key2.svgt";

    kb.startHtml();
%>
<!-- custom styles go here -->
<style>
.kybard-container {
  background-color: rgb(67,67,67);
}
.kybard-menu-container {
    background-color: rgb(98,48,48);
}
.CONTACTLOST-LED-ON {
  fill: #DB1049;
}
.CONTACTLOST-LED-OFF {
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
    kb.endRowThirds(1);
    //
    // Keyboard rows start here
    //

    // Top Row 7 8 9 lock menu
    kb.startRow();
    kb.spacer(1);
    kb.key("7","KP_7",3,3,null,null,"",K2);
    kb.key("8","KP_8",3,3,null,null,"",K3,"ARROWCOLOR=#000","ARROW=180");
    kb.key("9","KP_9",3,3,null,null,"",K2);
    kb.key("LOCK","KP_NUMLOCK",3,3,null,null,"",K2,"FS=30","S=NUM");
    kb.key("","",1,3,"doNothing()","doNothing()","",KLED,"CLS=NUMLOCK-LED");
    kb.key("MENU","",3,3,"panic()","menu()","",K,"FS=16","BGC="+SHIFTCOLOR);
    kb.endRow();

    // 4 5 6 ret option
    kb.startRow();
    kb.spacer(1);
    kb.key("4","KP_4",3,3,null,null,"",K3,"ARROWCOLOR=#000","ARROW=90");
    kb.key("5","KP_5",3,3,null,null,"",K2);
    kb.key("6","KP_6",3,3,null,null,"",K3,"ARROWCOLOR=#000","ARROW=270");
    kb.key("RET","KP_ENTER",3,6,null,null,"",K,"FS=16");
    kb.key("OPTION","KB_F5",4,3,null,null,"",K,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // 1 2 3 select
    kb.startRow();
    kb.spacer(1);
    kb.key("1","KP_1",3,3,null,null,"",K2);
    kb.key("2","KP_2",3,3,null,null,"",K3,"ARROWCOLOR=#000","ARROW=0");
    kb.key("3","KP_3",3,3,null,null,"",K2);
    kb.spacer(3);
    kb.key("SELECT","KB_F6",4,3,null,null,"",K,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // 0 space start (0 is two cols)
    kb.startRow();
    kb.spacer(1);
    kb.key("0","KP_0",6,3,null,null,"",K2);
    kb.key(".","KP_DOT",3,3,null,null,"",K);
    kb.key(" ","KB_SPACE",3,3,null,null,"",K);
    kb.key("START","KB_F7",4,3,null,null,"",K,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    //
    // Keyboard is finished
    //
    kb.endKeyboard();

    // Now the popup menu
    kb.startMenu();
    kb.startRow();
    kb.endRowThirds(1);
    kb.startRow();
    kb.spacer(10);
    kb.key("X","",4,3,"doNothing()","closeMenu()","",MK,"S=Close");
    kb.endRowThirds(1);
    kb.startRow();
    kb.spacer(1);
    kb.notKey("CONTACT-STATUS-TEXT",10,1,"connection status pending");
    kb.endRowThirds(2);

    kb.startRow();
    // blank row
    kb.endRow();

    kb.startRow();
    kb.spacer(1);
    kb.key("M","",7,3,"doNothing()","mainMenu()","",MK,"S=Main Menu");
    kb.spacer(3);
    kb.notKey("",2,1,"CAPS");
    kb.key("","",1,1,"doNothing()","doNothing()","",KLED,"CLS=CAPSLOCK-LED");
    kb.endRowThirds(1);
    kb.startRow();
    kb.spacer(11);
    kb.notKey("",2,1,"NUM");
    kb.key("","",1,1,"doNothing()","doNothing()","",KLED,"CLS=NUMLOCK-LED");
    kb.endRowThirds(1);
    kb.startRow();
    kb.spacer(11);
    kb.notKey("",2,1,"SCRL");
    kb.key("","",1,1,"doNothing()","doNothing()","",KLED,"CLS=SCROLLLOCK-LED");
    kb.endRowThirds(1);

    kb.startRow();
    kb.spacer(1);
    kb.key("L","",7,3,"doNothing()","doLogout()","",MK,"S=Logout");
    kb.endRow();
    kb.endKeyboard();

    kb.endHtml();
%>