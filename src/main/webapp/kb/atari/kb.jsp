<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"Atari 400",17*3+1,5*3+2,null);
    kb.setMouseMode(false);
    // Default key SVG and size
    String SHIFTCOLOR="#D68554";
    String CTRLCOLOR="#F9C05D";
    kb.setDefaultSvg("atari/keys/key.svgt",3,3,"FS=48","BORD=4","BORDC=#F5F0EA","BGC=#736C55","TXTC=#F5F0EA","SHFBGC="+SHIFTCOLOR,"CTLBGC="+CTRLCOLOR,"FIXSY=0","FIXLY=0");
    String KN="atari/keys/key2.svgt";
    String K3="atari/keys/key3.svgt";
    String KA="atari/keys/keyAtari.svgt";

    kb.startHtml();
%>
<!-- custom styles go here -->
<style>
.kybard-container {
    background-color: rgb(72,75,77);
};
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
    kb.key("2","2",3,3,"keyDownShiftDiff(this,'2','KB_APOSTROPHE')","keyUpShiftDiff(this,'2','KB_APOSTROPHE')","",KN,"S=&quot;");
    kb.key("3","3",3,3,null,null,"",KN,"S=#");
    kb.key("4","4",3,3,null,null,"",KN,"S=$");
    kb.key("5","5",3,3,null,null,"",KN,"S=%");
    kb.key("6","6",3,3,"keyDownShiftDiff(this,'6','7')","keyUpShiftDiff(this,'6','&')","",KN,"S=&amp;");
    kb.key("7","7",3,3,null,null,"",KN,"S='");
    kb.key("8","8",3,3,"keyDownShiftDiff(this,'8','2')","keyUpShiftDiff(this,'8','2')","",KN,"S=@","FIXSY=-2");
    kb.key("9","9",3,3,null,null,"",KN,"S=(","FIXSY=-2");
    kb.key("0","0",3,3,null,null,"",KN,"S=)","FIXSY=-2");
    kb.key("&lt;","KB_LESS_THAN",3,3,null,null,"",KN,"S=CLEAR","FS=30");
    kb.key("&gt;","KB_GREATER_THAN",3,3,null,null,"",KN,"S=INSERT","FS=30");
    kb.key("BACK S","KB_BACKSPACE",3,3,null,null,"",KN,"S=DELETE","FS=30");
    kb.key("BREAK","KB_BREAK",3,3,null,null,"",null,"FS=16");
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
    kb.key("-","-",3,3,null,null,"",K3,"S=_","ARROWCOLOR=#000","ARROW=180");
    kb.key("=","=",3,3,null,null,"",K3,"S=|","ARROWCOLOR=#000","ARROW=0","FIXSY=4");
    kb.key("RETURN","KB_ENTER",5,3,null,null,"",null,"FS=16");
    kb.spacer(1);
    kb.key("OPTION","KB_F4",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
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
    kb.key("+","+",3,3,null,null,"",K3,"S=\\","ARROWCOLOR=#000","ARROW=90","FIXSY=6");
    kb.key("*","*",3,3,null,null,"",K3,"S=^","ARROWCOLOR=#000","ARROW=270","FIXSY=6");
    kb.key("LOCK","KB_CAPSLOCK",3,3,null,null,"",KN,"FS=30","S=CAPS");
    kb.spacer(2);
    kb.key("SELECT","KB_F3",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
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
    kb.key(",","KB_COMMA",3,3,"keyDownShiftDiff(this,'KB_COMMA','KB_OPEN_BRACKET')","keyUpShiftDiff(this,'KB_COMMA','KB_OPEN_BRACKET')","",KN,"S=[","FIXSY=-2","FIXLY=-4");
    kb.key(".",".",3,3,"keyDownShiftDiff(this,'.','KB_CLOSE_BRACKET')","keyUpShiftDiff(this,'.','KB_CLOSE_BRACKET')","",KN,"S=]","FIXSY=-2","FIXLY=-4");
    kb.key("/","/",3,3,null,null,"",KN,"S=?");
    kb.key("atari","KB_END",3,3,null,null,"",KA);
    kb.key("SHIFT","RIGHT_SHIFT",4,3,kb.SHIFT,kb.SHIFT,"",null,"FS=16","BGC="+SHIFTCOLOR);
    kb.spacer(2);
    kb.key("START","F2",4,3,null,null,"",null,"FS=16","BGC="+CTRLCOLOR,"TXTC=#000");
    kb.endRow();

    // spacebarn
    kb.startRow();
    kb.spacer(10);
    kb.key("","KB_SPACE",26,3,null,null,"",null);
    kb.endRow();

    //
    // Keyboard is finished
    //
    kb.endKeyboard();
    kb.endHtml();
%>
