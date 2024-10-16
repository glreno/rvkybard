<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.jsp.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"Round",20*3,6*3,null);
    kb.loadDefault("COPYRIGHTMESSAGE", "Diplomata font Copyright &copy; 2011 The Diplomata Project");

    // Default key SVG and size
    String DA="atari/keys/";
    String DS="std/keys/";
    String DART="std/keys/art/";
    String DK="artdeco/keys/";
    kb.setDefaultSvg(DK+"key.svgt",3,3,"FS=32","BORD=4","BORDC=#CD7F32","BGC=#eec","TXTC=#000");
    String K2=DK+"key2.svgt";
    String KI=DK+"keyi.svgt";
    String KW=DK+"keywide.svgt";
    String KWI=DK+"keywidei.svgt";
    String KLED=DS+"led2state.svgt";
    String KCAPSLOCK=DK+"capslock.svgt";
    String MK=DA+"key2.svgt";

    kb.startHtml();
%>
<!-- custom styles go here -->
<style>
.kybard-menu-container {
    background-color: rgb(98,48,48);
}
.kbbuttonDown .depress {
  fill: #888 !important;
}
.CAPSLOCK-LED-ON {
    stroke: #b00;
    fill: #b00;
}
.CAPSLOCK-LED-OFF {
    stroke: #eec;
    fill: #eec;
}
.CONTACTLOST-LED-ON {
  fill: #b00;
}
.CONTACTLOST-LED-OFF {
  fill: none;
}
.USBLOST-LED-ON {
  fill: #e80;
}
.USBLOST-LED-OFF {
  fill: #aaa;
}
@font-face {
  font-family: Diplomata;
  src: url(/fonts/Diplomata-Regular.ttf);
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

    // ESC blank F1-F4 BLANK F5-F8 BLANK F9-F12 printscreen scrolllock pauebreak
    kb.startRow();
    kb.key("Esc","KB_ESCAPE",3,3,null,null,"",null,"FS=16","TXTC=#400");
    kb.spacer(2);
    kb.key("F1","KB_F1",3,3,null,null,"",null,"FS=16");
    kb.key("F2","KB_F2",3,3,null,null,"",null,"FS=16");
    kb.key("F3","KB_F3",3,3,null,null,"",null,"FS=16");
    kb.key("F4","KB_F4",3,3,null,null,"",null,"FS=16");
    kb.spacer(1);
    kb.key("F5","KB_F5",3,3,null,null,"",null,"FS=16");
    kb.key("F6","KB_F6",3,3,null,null,"",null,"FS=16");
    kb.key("F7","KB_F7",3,3,null,null,"",null,"FS=16");
    kb.key("F8","KB_F8",3,3,null,null,"",null,"FS=16");
    kb.spacer(1);
    kb.key("F9","KB_F9",3,3,null,null,"",null,"FS=16");
    kb.key("F10","KB_F10",3,3,null,null,"",null,"FS=16");
    kb.key("F11","KB_F11",3,3,null,null,"",null,"FS=16");
    kb.key("F12","KB_F12",3,3,null,null,"",null,"FS=16");
    kb.spacer(2);
    kb.key("Screen","KB_PRTSCR",3,3,null,null,"",K2,"FS=16","TXTC=#400","S=Print");
    kb.key("Lock","KB_SCROLLLOCK",3,3,null,null,"",K2,"FS=16","TXTC=#400","S=Scroll");
    kb.key("Break","KB_PAUSE",3,3,null,null,"",K2,"FS=16","TXTC=#400","S=Pause");
    kb.endRow();

    // tilde 1-9 0 - = backspace space insert home pgup
    kb.startRow();
    kb.key("`","KB_BACKQUOTE",3,3,null,null,"",K2,"S=~");
    kb.key("1","1",3,3,null,null,"",K2,"S=!");
    kb.key("2","2",3,3,null,null,"",K2,"S=@");
    kb.key("3","3",3,3,null,null,"",K2,"S=#");
    kb.key("4","4",3,3,null,null,"",K2,"S=$");
    kb.key("5","5",3,3,null,null,"",K2,"S=%");
    kb.key("6","6",3,3,null,null,"",K2,"S=^");
    kb.key("7","7",3,3,null,null,"",K2,"S=&");
    kb.key("8","8",3,3,null,null,"",K2,"S=*");
    kb.key("9","9",3,3,null,null,"",K2,"S=(");
    kb.key("0","0",3,3,null,null,"",K2,"S=)");
    kb.key("-","-",3,3,null,null,"",K2,"S=_");
    kb.key("=","=",3,3,null,null,"",K2,"S=+");
    kb.key("BS","KB_BACKSPACE",4,3,null,null,"",KWI,"ARROWCOLOR=#000","ARROW=90","ARROWLENGTH=32","ARROWWIDTH=8","INC1="+DART+"arrow2.svgt");
    kb.spacer(2);
    kb.key("Ins","KB_INSERT",3,3,null,null,"",null,"FS=12","TXTC=#400");
    kb.key("Home","KB_HOME",3,3,null,null,"",null,"FS=10","TXTC=#400");
    kb.key("Up","KB_PGUP",3,3,null,null,"",K2,"FS=18","TXTC=#400","S=Page");
    kb.endRow();

    // Tab qwertyuiop[]\ space del end pgdn
    kb.startRow();
    kb.key("Tab","KB_TAB",4,3,null,null,"",KW,"FS=16","TXTC=#400");
    kb.key("Q","Q");
    kb.key("W","W",3,3,null,null,"",DK+"keyW.svgt");
    kb.key("E","E");
    kb.key("R","R");
    kb.key("T","T");
    kb.key("Y","Y");
    kb.key("U","U");
    kb.key("I","I");
    kb.key("O","O");
    kb.key("P","P");
    kb.key("[","KB_OPEN_BRACKET",3,3,null,null,"",K2,"S={");
    kb.key("]","KB_CLOSE_BRACKET",3,3,null,null,"",K2,"S=}");
    kb.key("\\","KB_BACKSLASH",3,3,null,null,"",K2,"S=|");
    kb.spacer(2);
    kb.key("Del","KB_DELETE",3,3,null,null,"",null,"FS=12","TXTC=#400");
    kb.key("End","KB_END",3,3,null,null,"",null,"FS=12","TXTC=#400");
    kb.key("Down","KB_PGDN",3,3,null,null,"",K2,"FS=18","TXTC=#400","S=Page");
    kb.endRow();

    // Caps asdfghjkl;' enter
    kb.startRow();
    kb.key("Caps","KB_CAPSLOCK",5,3,null,null,"",KCAPSLOCK,"FS=16","TXTC=#400");
    kb.key("A","A");
    kb.key("S","S");
    kb.key("D","D");
    kb.key("F","F");
    kb.key("G","G");
    kb.key("H","H");
    kb.key("J","J");
    kb.key("K","K");
    kb.key("L","L");
    kb.key(";",";",3,3,null,null,"",K2,"S=:");
    kb.key("'","KB_APOSTROPHE",3,3,null,null,"",K2,"S=&quot;");
    kb.key("Enter","KB_ENTER",5,3,null,null,"",KW,"FS=16");
    kb.spacer(6);
    kb.key("","",1,3,"doNothing()","doNothing()","",KLED,"CLS1=USBLOST-LED","CLS2=CONTACTLOST-LED");
    kb.spacer(1);
    kb.key("Menu","",3,3,"panic()",kb.MENU,"",null,"FS=10");
    kb.endRow();

    // shift zxcvbnm,./ shift space uparrow
    kb.startRow();
    kb.key("Shift","LEFT_SHIFT",6,3,kb.SHIFT,kb.SHIFT,"",KW,"FS=16");
    kb.key("Z","Z");
    kb.key("X","X");
    kb.key("C","C");
    kb.key("V","V");
    kb.key("B","B");
    kb.key("N","N");
    kb.key("M","M",3,3,null,null,"",DK+"keyW.svgt");
    kb.key(",","KB_COMMA",3,3,null,null,"",K2,"S=&lt;");
    kb.key(".",".",3,3,null,null,"",K2,"S=&gt;");
    kb.key("/","/",3,3,null,null,"",K2,"S=?");
    kb.key("Shift","RIGHT_SHIFT",6,3,kb.SHIFT,kb.SHIFT,"",KW,"FS=16");
    kb.spacer(6);
    kb.key("UP","KB_UPARROW",3,3,null,null,"",KI,"FS=16","ARROWCOLOR=#000","ARROW=180","INC1="+DART+"arrow.svgt");
    kb.endRow();

    // ctrl windows alt spacebarn alt windows ctrl space left down right
    kb.startRow();
    kb.key("Ctrl","LEFT_CTRL",4,3,kb.SHIFT,kb.SHIFT,"",KW,"FS=16","TXTC=#400");
    kb.key("Windows","LEFT_GUI",3,3,kb.SHIFT,kb.SHIFT,"",KI,"FS=16","TXTC=#400","colour=#505050","pxsz=2","INC1="+DART+"cmd8bit.svgt");
    kb.key("Alt","LEFT_ALT",3,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","TXTC=#400");
    kb.key("","KB_SPACE",24,3,null,null,"",KW);
    kb.key("Alt","RIGHT_ALT",3,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","TXTC=#400");
    kb.key("Ctrl","RIGHT_CTRL",4,3,kb.SHIFT,kb.SHIFT,"",KW,"FS=16","TXTC=#400");
    kb.spacer(4);
    kb.key("Left","KB_LEFTARROW",3,3,null,null,"",KI,"FS=16","ARROWCOLOR=#000","ARROW=90","INC1="+DART+"arrow.svgt");
    kb.key("Down","KB_DOWNARROW",3,3,null,null,"",KI,"FS=16","ARROWCOLOR=#000","ARROW=0","INC1="+DART+"arrow.svgt");
    kb.key("Right","KB_RIGHTARROW",3,3,null,null,"",KI,"FS=16","ARROWCOLOR=#000","ARROW=270","INC1="+DART+"arrow.svgt");
    kb.endRow();

    // crtl windoze alt space alt ctrl
    //
    // Keyboard is finished
    //
    kb.endKeyboard();

    // Define the popup menu
    kb.menu();

    kb.endHtml();
%>
