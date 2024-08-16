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
    String DART="std/keys/art/";
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
    kb.key("2","2",3,3,null,null,"",K2,"S=&quot;");
    kb.key("3","3",3,3,null,null,"",K2,"S=#");
    kb.key("4","4",3,3,null,null,"",K2,"S=$");
    kb.key("5","5",3,3,null,null,"",K2,"S=%");
    kb.key("6","6",3,3,"keyDownShiftDiff(this,'6','7')","keyUpShiftDiff(this,'6','&')","",K2,"S=&amp;");
    kb.key("7","7",3,3,"keyDownRemap(this,[],'7',[],'KB_APOSTROPHE',['LEFT_CTRL'],'7')","keyUpRemap(this,'7','KB_APOSTROPHE','7')","",K2,"S='");
    kb.key("8","8",3,3,"keyDownShiftDiff(this,'8','KB_APOSTROPHE')","keyUpShiftDiff(this,'8','KB_APOSTROPHE')","",K2,"S=@","FIXSY=-2");
    kb.key("9","9",3,3,null,null,"",K2,"S=(","FIXSY=-2");
    kb.key("0","0",3,3,null,null,"",K2,"S=)","FIXSY=-2");
    kb.key("&lt","<",3,3,"keyDownRemap(this,['LEFT_SHIFT'],'KB_COMMA',['LEFT_SHIFT'],'KB_HOME',['LEFT_CTRL'],'KB_HOME')","keyUpRemap(this,'KB_COMMA','KB_HOME','KB_HOME')","",K2,"S=CLEAR","FS=30");
    kb.key("&gt",">",3,3,"keyDownRemap(this,['LEFT_SHIFT'],'.',[],'KB_INSERT',['LEFT_CTRL'],'KB_INSERT')","keyUpRemap(this,'.','KB_INSERT','.')","",K2,"S=INSERT","FS=30");
    kb.key("BACK S","KB_BACKSPACE",3,3,null,null,"",K2,"S=DELETE","FS=30");
    kb.key("BREAK","KB_F8",3,3,null,null,"",null,"FS=16");
    kb.spacer(1);
    kb.key("MENU","",4,3,"panic()",kb.MENU,"",KM,"FS=16","BGC="+SHIFTCOLOR);
    kb.endRow();

    // Tab qwertyuiop - = Return
    kb.startRow();
    kb.spacer(1);
    kb.key("TAB","KB_TAB",4,3,null,null,"",K3,"FS=30","S=SET","C=CLR","INC1="+DS+"empty.svgt");
    kb.key("Q","Q",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"box/nw.svgt");
    kb.key("W","W",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"box/nc.svgt");
    kb.key("E","E",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"box/ne.svgt");
    kb.key("R","R",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"box/hbar.svgt");
    kb.key("T","T",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"circle.svgt");
    kb.key("Y","Y",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"filledbox/w.svgt");
    kb.key("U","U",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"filledbox/s.svgt");
    kb.key("I","I",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"filledbox/nw.svgt");
    kb.key("O","O",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"filledbox/ne.svgt");
    kb.key("P","P",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"suits/club.svgt");
    kb.key("-","-",3,3,"keyDownRemap(this,[],'-',['LEFT_SHIFT'],'-',[],'KB_UPARROW')","keyUpRemap(this,'-','-','KB_UPARROW')","",K3,"S=_","ARROWCOLOR=#000","ARROW=180","INC1="+DART+"arrow.svgt");
    kb.key("=","=",3,3,"keyDownRemap(this,[],'=',['LEFT_SHIFT'],'KB_BACKSLASH',[],'KB_DOWNARROW')","keyUpRemap(this,'=','KB_BACKSLASH','KB_DOWNARROW')","",K3,"S=|","ARROWCOLOR=#000","ARROW=0","FIXSY=4","INC1="+DART+"arrow.svgt");
    kb.key("RETURN","KB_ENTER",5,3,null,null,"",null,"FS=16");
    kb.spacer(1);
    kb.key("OPTION","KB_F5",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // Ctrl asdfghjkl ; + * LOCK
    kb.startRow();
    kb.spacer(2);
    kb.key("CTRL","LEFT_CTRL",4,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.key("A","A",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"box/cw.svgt");
    kb.key("S","S",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"box/cc.svgt");
    kb.key("D","D",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"box/ce.svgt");
    kb.key("F","F",3,3,null,null,"",K3,"BC=#000","BP=1.8","BW=8","BH=8","INC1="+DART+"outerbox/slash.svgt");
    kb.key("G","G",3,3,null,null,"",K3,"BC=#000","BP=1.8","BW=8","BH=8","INC1="+DART+"outerbox/backslash.svgt");
    kb.key("H","H",3,3,null,null,"",K3,"BC=#000","BP=1.8","BW=8","BH=8","INC1="+DART+"outerbox/triangle.svgt");
    kb.key("J","J",3,3,null,null,"",K3,"BC=#000","BP=1.8","BW=8","BH=8","INC1="+DART+"outerbox/backtriangle.svgt");
    kb.key("K","K",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"filledbox/sw.svgt");
    kb.key("L","L",3,3,null,null,"",K3,"BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"filledbox/se.svgt");
    kb.key(";",";",3,3,null,null,"",K3,"S=:","FIXLY=-2","BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"suits/spade.svgt");
    kb.key("+","+",3,3,"keyDownRemap(this,['LEFT_SHIFT'],'=',[],'KB_BACKSLASH',[],'KB_LEFTARROW')","keyUpRemap(this,'=','KB_BACKSLASH','KB_LEFTARROW')","",K3,"S=\\","ARROWCOLOR=#000","ARROW=90","FIXSY=6","INC1="+DART+"arrow.svgt");
    kb.key("*","*",3,3,"keyDownRemap(this,['LEFT_SHIFT'],'8',['LEFT_SHIFT'],'6',[],'KB_RIGHTARROW')","keyUpRemap(this,'8','6','KB_RIGHTARROW')","",K3,"S=^","ARROWCOLOR=#000","ARROW=270","FIXSY=6","INC1="+DART+"arrow.svgt");
    kb.key("LOCK","KB_CAPSLOCK",3,3,null,null,"",K2,"FS=30","S=CAPS");
    kb.key("","",1,3,"doNothing()","doNothing()","",KLED,"CLS=CAPSLOCK-LED");
    kb.spacer(1);
    kb.key("SELECT","KB_F6",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // shift zxcvbnm,./ atari shift
    kb.startRow();
    kb.spacer(2);
    kb.key("SHIFT","LEFT_SHIFT",6,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","BGC=#D68554");
    kb.key("Z","Z",3,3,null,null,"",K3,"BC=#000","BP=1.8","BW=8","BH=8","INC1="+DART+"box/sw.svgt");
    kb.key("X","X",3,3,null,null,"",K3,"BC=#000","BP=1.8","BW=8","BH=8","INC1="+DART+"box/sc.svgt");
    kb.key("C","C",3,3,null,null,"",K3,"BC=#000","BP=1.8","BW=8","BH=8","INC1="+DART+"box/se.svgt");
    kb.key("V","V",3,3,null,null,"",K3,"BC=#000","BP=1.8","BW=8","BH=8","INC1="+DART+"outerbox/w.svgt");
    kb.key("B","B",3,3,null,null,"",K3,"BC=#000","BP=1.8","BW=8","BH=8","INC1="+DART+"outerbox/e.svgt");
    kb.key("N","N",3,3,null,null,"",K3,"BC=#000","BP=1.8","BW=8","BH=8","INC1="+DART+"outerbox/s.svgt");
    kb.key("M","M",3,3,null,null,"",K3,"BC=#000","BP=1.8","BW=8","BH=8","INC1="+DART+"outerbox/n.svgt");
    kb.key(",","KB_COMMA",3,3,"keyDownRemap(this,[],'KB_COMMA',[],'KB_OPEN_BRACKET',['LEFT_CTRL'],'KB_COMMA')","keyUpRemap(this,'KB_COMMA','KB_OPEN_BRACKET','KB_COMMA')","",K3,"S=[","FIXSY=5","FIXLY=-6","BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"suits/heart.svgt");
    kb.key(".",".",3,3,"keyDownRemap(this,[],'.',[],'KB_CLOSE_BRACKET',['LEFT_CTRL'],'.')","keyUpRemap(this,'.','KB_CLOSE_BRACKET','.')","",K3,"S=]","FIXSY=5","FIXLY=-6","BC=#000","BP=1.6","BW=8","BH=8","INC1="+DART+"suits/diamond.svgt");
    kb.key("/","/",3,3,null,null,"",K2,"S=?");
    kb.key("atari","KB_F10",3,3,null,null,"",KA);
    kb.key("SHIFT","RIGHT_SHIFT",4,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","BGC="+SHIFTCOLOR);
    kb.spacer(2);
    kb.key("START","F7",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
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
