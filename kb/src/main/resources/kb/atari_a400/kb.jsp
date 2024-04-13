<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.jsp.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"Atari 400",17*3+1,5*3+2,null);
    kb.setMouseMode(false);

    // Standard colours
    String BACKCOLOR= "#434343"; // 67,67,67
    String KEYCOLOR=  "#776047"; // 119,96,71
    String SHIFTCOLOR="#CF8710"; // 207,135,16
    String CTRLCOLOR= "#FFC640"; // 255,198,64
    String TXTCOLOR=  "#D6D2CE"; // 214,210,206

    // Default key SVG and size
    kb.setDefaultSvg("atari/keys/key.svgt",3,3,"FS=48","BORD=4","BORDC="+TXTCOLOR,"BGC="+KEYCOLOR,"TXTC="+TXTCOLOR,"SHFBGC="+SHIFTCOLOR,"CTLBGC="+CTRLCOLOR,"FIXSY=0","FIXLY=0");
    String KN="atari/keys/key2.svgt";
    String K3="atari/keys/key3.svgt";
    String KA="atari/keys/keyAtari.svgt";
    String DA="atari/keys/";

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
.CONTACT-LED-ON {
  color: #CF8710;
}
.CONTACT-LED-OFF {
  color: #AAA;
}
.CAPSLOCK-LED-ON {
  color: #CF8710;
}
.CAPSLOCK-LED-OFF {
  color: #AAA;
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
    kb.key("1","1",3,3,null,null,"",KN,"S=!");
    kb.key("2","2",3,3,null,null,"",KN,"S=&quot;");
    kb.key("3","3",3,3,null,null,"",KN,"S=#");
    kb.key("4","4",3,3,null,null,"",KN,"S=$");
    kb.key("5","5",3,3,null,null,"",KN,"S=%");
    kb.key("6","6",3,3,"keyDownShiftDiff(this,'6','7')","keyUpShiftDiff(this,'6','&')","",KN,"S=&amp;");
    kb.key("7","7",3,3,"keyDownRemap(this,[],'7',[],'KB_APOSTROPHE',['LEFT_CTRL'],'7')","keyUpRemap(this,'7','KB_APOSTROPHE','7')","",KN,"S='");
    kb.key("8","8",3,3,"keyDownShiftDiff(this,'8','KB_APOSTROPHE')","keyUpShiftDiff(this,'8','KB_APOSTROPHE')","",KN,"S=@","FIXSY=-2");
    kb.key("9","9",3,3,null,null,"",KN,"S=(","FIXSY=-2");
    kb.key("0","0",3,3,null,null,"",KN,"S=)","FIXSY=-2");
    kb.key("&lt","<",3,3,"keyDownRemap(this,['LEFT_SHIFT'],'KB_COMMA',['LEFT_SHIFT'],'KB_HOME',['LEFT_CTRL'],'KB_HOME')","keyUpRemap(this,'KB_COMMA','KB_HOME','KB_HOME')","",KN,"S=CLEAR","FS=30");
    kb.key("&gt",">",3,3,"keyDownRemap(this,['LEFT_SHIFT'],'.',[],'KB_INSERT',['LEFT_CTRL'],'KB_INSERT')","keyUpRemap(this,'.','KB_INSERT','.')","",KN,"S=INSERT","FS=30");
    kb.key("BACK S","KB_BACKSPACE",3,3,null,null,"",KN,"S=DELETE","FS=30");
    kb.key("BREAK","KB_F8",3,3,null,null,"",null,"FS=16");
    kb.spacer(1);
    kb.key("MENU","x",4,3,"panic()","menu()","",null,"FS=16","BGC="+SHIFTCOLOR);
    kb.endRow();

    // Tab qwertyuiop - = Return
    kb.startRow();
    kb.spacer(1);
    kb.key("TAB","KB_TAB",4,3,null,null,"",K3,"FS=30","S=SET","C=CLR","ARROWCOLOR=none","ARROW=0");
    kb.key("Q","Q");
    kb.key("W","W");
    kb.key("E","E");
    kb.key("R","R");
    kb.key("T","T");
    kb.key("Y","Y");
    kb.key("U","U");
    kb.key("I","I");
    kb.key("O","O");
    kb.key("P","P");
    kb.key("-","-",3,3,"keyDownRemap(this,[],'-',['LEFT_SHIFT'],'-',[],'KB_UPARROW')","keyUpRemap(this,'-','-','KB_UPARROW')","",K3,"S=_","ARROWCOLOR=#000","ARROW=180");
    kb.key("=","=",3,3,"keyDownRemap(this,[],'=',['LEFT_SHIFT'],'KB_BACKSLASH',[],'KB_DOWNARROW')","keyUpRemap(this,'=','KB_BACKSLASH','KB_DOWNARROW')","",K3,"S=|","ARROWCOLOR=#000","ARROW=0","FIXSY=4");
    kb.key("RETURN","KB_ENTER",5,3,null,null,"",null,"FS=16");
    kb.spacer(1);
    kb.key("OPTION","KB_F5",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // Ctrl asdfghjkl ; + * LOCK
    kb.startRow();
    kb.spacer(2);
    kb.key("CTRL","LEFT_CTRL",4,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.key("A","A");
    kb.key("S","S");
    kb.key("D","D");
    kb.key("F","F");
    kb.key("G","G");
    kb.key("H","H");
    kb.key("J","J");
    kb.key("K","K");
    kb.key("L","L");
    kb.key(";",";",3,3,null,null,"",KN,"S=:","FIXLY=-2");
    kb.key("+","+",3,3,"keyDownRemap(this,['LEFT_SHIFT'],'=',[],'KB_BACKSLASH',[],'KB_LEFTARROW')","keyUpRemap(this,'=','KB_BACKSLASH','KB_LEFTARROW')","",K3,"S=\\","ARROWCOLOR=#000","ARROW=90","FIXSY=6");
    kb.key("*","*",3,3,"keyDownRemap(this,['LEFT_SHIFT'],'8',['LEFT_SHIFT'],'6',[],'KB_RIGHTARROW')","keyUpRemap(this,'8','6','KB_RIGHTARROW')","",K3,"S=^","ARROWCOLOR=#000","ARROW=270","FIXSY=6");
    kb.key("LOCK","KB_CAPSLOCK",3,3,null,null,"",KN,"FS=30","S=CAPS");
    kb.spacer(2);
    kb.key("SELECT","KB_F6",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // shift zxcvbnm,./ atari shift
    kb.startRow();
    kb.spacer(2);
    kb.key("SHIFT","LEFT_SHIFT",6,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","BGC=#D68554");
    kb.key("Z","Z");
    kb.key("X","X");
    kb.key("C","C");
    kb.key("V","V");
    kb.key("B","B");
    kb.key("N","N");
    kb.key("M","M");
    kb.key(",","KB_COMMA",3,3,"keyDownRemap(this,[],'KB_COMMA',[],'KB_OPEN_BRACKET',['LEFT_CTRL'],'KB_COMMA')","keyUpRemap(this,'KB_COMMA','KB_OPEN_BRACKET','KB_COMMA')","",KN,"S=[","FIXSY=-2","FIXLY=-4");
    kb.key(".",".",3,3,"keyDownRemap(this,[],'.',[],'KB_CLOSE_BRACKET',['LEFT_CTRL'],'.')","keyUpRemap(this,'.','KB_CLOSE_BRACKET','.')","",KN,"S=]","FIXSY=-2","FIXLY=-4");
    kb.key("/","/",3,3,null,null,"",KN,"S=?");
    kb.key("atari","KB_END",3,3,null,null,"",KA);
    kb.key("SHIFT","RIGHT_SHIFT",4,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","BGC="+SHIFTCOLOR);
    kb.spacer(2);
    kb.key("START","F7",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // spacebarn
    kb.startRow();
    kb.spacer(10);
    kb.key("","KB_SPACE",26,3,null,null,"",null);
    kb.key("CTRL","RIGHT_CTRL",4,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.notKey("CAPSLOCK-LED",3,1,"caps");
    kb.spacer(3);
    kb.notKey("CONTACT-LED",3,1,"contact");

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
    kb.key("X","x",4,3,"doNothing()","closeMenu()","",DA+"key2.svgt","S=Close");
    kb.endRow();
    kb.startRow();
    // blank row
    kb.endRow();
    kb.startRow();
    kb.spacer(1);
    kb.key("M","m",7,3,"doNothing()","mainMenu()","",DA+"key2.svgt","S=Main Menu");
    kb.endRow();
    kb.startRow();
    kb.spacer(1);
    kb.key("L","l",7,3,"doNothing()","doLogout()","",DA+"key2.svgt","S=Logout");
    kb.endRow();
    kb.endKeyboard();

    kb.endHtml();
%>
