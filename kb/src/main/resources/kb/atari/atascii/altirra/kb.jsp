<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.jsp.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"Atascii Graphics",17*3+1,5*3+2,null);
    kb.loadDefault("COPYRIGHTMESSAGE", "ATARI and the ATARI logo are trademarks of Atari Interactive Inc.");

    // Standard colours
    String BACKCOLOR= "#434343"; // 67,67,67
    String KEYCOLOR=  "#776047"; // 119,96,71
    String SHIFTCOLOR="#CF8710"; // 207,135,16
    String CTRLCOLOR= "#FFC640"; // 255,198,64
    String TXTCOLOR=  "#D6D2CE"; // 214,210,206

    // Default key SVG and size
    String DA="atari/keys/";
    String DS="std/keys/";
    kb.setDefaultSvg(DA+"key.svgt",3,3,"FS=48","BORD=4","BORDC="+TXTCOLOR,"BGC="+KEYCOLOR,"TXTC="+TXTCOLOR,"SHFBGC="+SHIFTCOLOR,"CTLBGC="+CTRLCOLOR,"FIXSY=0","FIXLY=0");
    String K2=DA+"key2.svgt";
    String KM=DA+"keyMenu.svgt";
    String K3=DA+"key3.svgt";
    String KLED=DS+"led.svgt";
    String KA=DA+"keyAtari.svgt";
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

    //
    // Keyboard rows start here
    //
    kb.startRow();
    kb.endRowThirds(1);

    //  ESC  1..9 0 < > DEL BRK         RESET
    kb.startRow();
    kb.spacer(1);
    kb.key("ESC","KB_ESCAPE",3,3,null,null,"",null,"FS=18");
    kb.key("1","1",3,3,null,null,"",K2,"S=!");
    kb.key("2","2",3,3,"keyDownShiftDiff(this,'2','KB_APOSTROPHE')","keyUpShiftDiff(this,'2','KB_APOSTROPHE')","",K2,"S=&quot;");
    kb.key("3","3",3,3,null,null,"",K2,"S=#");
    kb.key("4","4",3,3,null,null,"",K2,"S=$");
    kb.key("5","5",3,3,null,null,"",K2,"S=%");
    kb.key("6","6",3,3,"keyDownShiftDiff(this,'6','7')","keyUpShiftDiff(this,'6','&')","",K2,"S=&amp;");
    kb.key("7","7",3,3,"keyDownRemap(this,[],'7',[],'KB_APOSTROPHE',['LEFT_CTRL'],'7')","keyUpRemap(this,'7','KB_APOSTROPHE','7')","",K2,"S='");
    kb.key("8","8",3,3,"keyDownShiftDiff(this,'8','2')","keyUpShiftDiff(this,'8','2')","",K2,"S=@","FIXSY=-2");
    kb.key("9","9",3,3,null,null,"",K2,"S=(","FIXSY=-2");
    kb.key("0","0",3,3,null,null,"",K2,"S=)","FIXSY=-2");
    kb.key("&lt","<",3,3,"keyDownRemap(this,['LEFT_SHIFT'],'KB_COMMA',['LEFT_SHIFT'],'KB_HOME',['LEFT_CTRL'],'KB_HOME')","keyUpRemap(this,'KB_COMMA','KB_HOME','KB_HOME')","",K2,"S=CLEAR","FS=30");
    kb.key("&gt",">",3,3,"keyDownRemap(this,['LEFT_SHIFT'],'.',[],'KB_INSERT',['LEFT_CTRL'],'KB_INSERT')","keyUpRemap(this,'.','KB_INSERT','.')","",K2,"S=INSERT","FS=30");
    kb.key("BACK S","KB_BACKSPACE",3,3,"keyDownRemap(this,[],'KB_BACKSPACE',[],'KB_DELETE',['LEFT_CTRL'],'KB_BACKSPACE')","keyUpRemap(this,'KB_BACKSPACE','KB_DELETE','KB_BACKSPACE')","",K2,"S=DELETE","FS=30");
    kb.key("BREAK","KB_PAUSE",3,3,null,null,"",null,"FS=16");
    kb.spacer(1);
    kb.key("MENU","",4,3,"panic()",kb.MENU,"",KM,"FS=16","BGC="+SHIFTCOLOR);
    kb.endRow();

    // Tab qwertyuiop - = Return
    kb.startRow();
    kb.spacer(1);
    kb.key("TAB","KB_TAB",4,3,null,null,"",K3,"FS=30","S=SET","C=CLR","ARROWCOLOR=none","ARROW=0");
    kb.key("Q","Q",3,3,null,null,"",K2,"S=&#x250f;","FIXSY=-6");
    kb.key("W","W",3,3,null,null,"",K2,"S=&#x2533;","FIXSY=-6");
    kb.key("E","E",3,3,null,null,"",K2,"S=&#x2513;","FIXSY=-6");
    kb.key("R","R",3,3,null,null,"",K2,"S=&#x2501;","FIXSY=1");
    kb.key("T","T",3,3,null,null,"",K2,"S=&#x26aa;","FIXSY=1");
    kb.key("Y","Y",3,3,null,null,"",K2,"S=&#x25e7;","FIXSY=1");//25bc
    kb.key("U","U",3,3,null,null,"",K2,"S=&#x2584;","FIXSY=0");//2584
    kb.key("I","I",3,3,null,null,"",K2,"S=&#x25f2;","FIXSY=0");//2598
    kb.key("O","O",3,3,null,null,"",K2,"S=&#x25f1;","FIXSY=0");//2596
    kb.key("P","P",3,3,null,null,"",K2,"S=&#x2663;","FIXSY=0");
    kb.key("-","-",3,3,"keyDownRemap(this,[],'-',['LEFT_SHIFT'],'-',['LEFT_ALT'],'KB_UPARROW')","keyUpRemap(this,'-','-','KB_UPARROW')","",K3,"S=_","ARROWCOLOR=#000","ARROW=180");
    kb.key("=","=",3,3,"keyDownRemap(this,[],'=',['LEFT_SHIFT'],'KB_BACKSLASH',['LEFT_ALT'],'KB_DOWNARROW')","keyUpRemap(this,'=','KB_BACKSLASH','KB_DOWNARROW')","",K3,"S=|","ARROWCOLOR=#000","ARROW=0","FIXSY=4");
    kb.key("RETURN","KB_ENTER",5,3,null,null,"",null,"FS=16");
    kb.spacer(1);
    kb.key("OPTION","KB_F4",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // Ctrl asdfghjkl ; + * LOCK
    kb.startRow();
    kb.spacer(2);
    kb.key("CTRL","LEFT_CTRL",4,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.key("A","A",3,3,null,null,"",K2,"S=&#x2523;","FIXSY=0");
    kb.key("S","S",3,3,null,null,"",K2,"S=&#x254B;","FIXSY=0");
    kb.key("D","D",3,3,null,null,"",K2,"S=&#x252B;","FIXSY=0");
    kb.key("F","F",3,3,null,null,"",K2,"S=&#x2571;","FIXSY=0");
    kb.key("G","G",3,3,null,null,"",K2,"S=&#x2572;","FIXSY=0");
    kb.key("H","H",3,3,null,null,"",K2,"S=&#x25e2;","FIXSY=0");
    kb.key("J","J",3,3,null,null,"",K2,"S=&#x25e3;","FIXSY=0");
    kb.key("K","K",3,3,null,null,"",K2,"S=&#x25f3;","FIXSY=0");//259d
    kb.key("L","L",3,3,null,null,"",K2,"S=&#x25f0;","FIXSY=0");//2598
    kb.key(";",";",3,3,null,null,"",K2,"S=&#x2660; :","FIXLY=-2");
    kb.key("+","+",3,3,"keyDownRemap(this,['LEFT_SHIFT'],'=',[],'KB_BACKSLASH',['LEFT_ALT'],'KB_LEFTARROW')","keyUpRemap(this,'=','KB_BACKSLASH','KB_LEFTARROW')","",K3,"S=\\","ARROWCOLOR=#000","ARROW=90","FIXSY=6");
    kb.key("*","*",3,3,"keyDownRemap(this,['LEFT_SHIFT'],'8',['LEFT_SHIFT'],'6',['LEFT_ALT'],'KB_RIGHTARROW')","keyUpRemap(this,'8','6','KB_RIGHTARROW')","",K3,"S=^","ARROWCOLOR=#000","ARROW=270","FIXSY=6");
    kb.key("LOCK","KB_CAPSLOCK",3,3,null,null,"",K2,"FS=30","S=CAPS");
    kb.key("","",1,3,"doNothing()","doNothing()","",KLED,"CLS=CAPSLOCK-LED");
    kb.spacer(1);
    kb.key("SELECT","KB_F3",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // shift zxcvbnm,./ atari shift
    kb.startRow();
    kb.spacer(2);
    kb.key("SHIFT","LEFT_SHIFT",6,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","BGC=#D68554");
    kb.key("Z","Z",3,3,null,null,"",K2,"S=&#x2517;","FIXSY=6");
    kb.key("X","X",3,3,null,null,"",K2,"S=&#x253b;","FIXSY=6");
    kb.key("C","C",3,3,null,null,"",K2,"S=&#x251b;","FIXSY=6");
    kb.key("V","V",3,3,null,null,"",K2,"S=&#x258f;","FIXSY=0");
    kb.key("B","B",3,3,null,null,"",K2,"S=&#x2595;","FIXSY=0");
    kb.key("N","N",3,3,null,null,"",K2,"S=&#x2581;","FIXSY=0");
    kb.key("M","M",3,3,null,null,"",K2,"S=&#x2594;","FIXSY=0");
    kb.key(",","KB_COMMA",3,3,"keyDownRemap(this,[],'KB_COMMA',[],'KB_OPEN_BRACKET',['LEFT_CTRL'],'KB_COMMA')","keyUpRemap(this,'KB_COMMA','KB_OPEN_BRACKET','KB_COMMA')","",K2,"S=&#x2665; [","FIXSY=-2","FIXLY=-4");
    kb.key(".",".",3,3,"keyDownRemap(this,[],'.',[],'KB_CLOSE_BRACKET',['LEFT_CTRL'],'.')","keyUpRemap(this,'.','KB_CLOSE_BRACKET','.')","",K2,"S=&#x2666; ]","FIXSY=-2","FIXLY=-4");
    kb.key("/","/",3,3,null,null,"",K2,"S=?");
    kb.key("atari","KB_END",3,3,null,null,"",KA);
    kb.key("SHIFT","RIGHT_SHIFT",4,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","BGC="+SHIFTCOLOR);
    kb.spacer(2);
    kb.key("START","F2",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // spacebarn
    kb.startRow();
    kb.spacer(10);
    kb.key("","KB_SPACE",26,3,null,null,"",null);
    kb.key("CTRL","RIGHT_CTRL",4,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    //
    // Keyboard is finished
    //
    kb.endKeyboard();

    // Define the popup menu
    kb.menu();

    kb.endHtml();
%>