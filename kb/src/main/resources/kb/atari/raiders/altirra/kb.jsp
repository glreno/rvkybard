<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.jsp.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"Raiders Copilot",11*3+3,5*3+2,null);
    kb.loadDefault("COPYRIGHTMESSAGE", "ATARI, Star Raiders, and the ATARI logo are trademarks of Atari Interactive Inc.");

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

    // Top Row
    kb.startRow();
    kb.spacer(1);
    kb.key("1","KB_1");
    kb.key("2","KB_2");
    kb.key("3","KB_3");
    kb.key("4","KB_4");
    kb.key("5","KB_5");
    kb.key("6","KB_6");
    kb.key("7","KB_7");
    kb.key("8","KB_8");
    kb.key("9","KB_9");
    kb.spacer(3);
    kb.key("0","KB_0",4,3,null,null,"",null,"BGC="+SHIFTCOLOR);
    kb.endRow();

    // T C F G H Option
    kb.startRow();
    kb.spacer(1);
    kb.key("T","KB_T",6,6,null,null,"",D2+"keyT.svgt","S1=Targeting","S2=Computer");
    kb.key("C","KB_C",6,6,null,null,"",D2+"keyC.svgt","S1=Attack","S2=Computer");
    kb.key("F","KB_F",6,6,null,null,"",D2+"keyF.svgt","S1=Forward","S2=View");
    kb.key("G","KB_G",6,6,null,null,"",D2+"keyG.svgt","S1=Galactic","S2=Chart");
    kb.key("H","KB_H",6,6,null,null,"",D2+"keyH.svgt","S1=Hyperwarp","S2=");
    kb.key("OPTION","KB_F4",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // Select
    kb.startRow();
    kb.spacer(31);
    kb.key("SELECT","KB_F3",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // M S A L   Start
    kb.startRow();
    kb.spacer(1);
    kb.key("M","KB_M",6,6,null,null,"",D2+"keyM.svgt","S1=Manual Target","S2=Select");
    kb.key("S","KB_S",6,6,null,null,"",D2+"keyS.svgt","S1=Shields","S2=","INC1="+D2+"spaceship.svgt","INC2="+D2+"spaceship.svgt");
    kb.key("A","KB_A",6,6,null,null,"",D2+"keyA.svgt","S1=Aft","S2=View");
    kb.key("L","KB_L",6,6,null,null,"",D2+"keyL.svgt","S1=Long Range","S2=Scanner");
    kb.spacer(2);
    kb.key("P","KB_P",4,3,null,null,"",D2+"keyPause.svgt","S2=PAUSE");
    kb.key("START","KB_F2",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // P
    kb.startRow();
    kb.spacer(25);
    kb.key(" ","KB_SPACE",6,3,null,null,"",null);
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
