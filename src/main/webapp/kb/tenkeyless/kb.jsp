<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"Tenkeyless",20*3,6*3,null);
    // Default key SVG and size
    kb.setDefaultSvg("tenkeyless/keys/key.svgt",3,3,"FS=48","BORD=4","BORDC=#222","BGC=#eec","TXTC=#000");
    String KN="tenkeyless/keys/key2.svgt";
    String KW="tenkeyless/keys/keywide.svgt";

    kb.startHtml();
%>
<!-- custom styles go here -->
<style>
</style>
<script type="text/javascript" language="javascript">
    // custom javascript goes here
</script>
</head>
<body>
<%
    kb.startKeyboard();

    // Keys are 3 columns, 22px each.
    // Standard key  = 66
    // 4-col key     = 89  (88+1)
    // 5-col key     = 110 (110+2)
    // Double key    = 135 (132+3)
    // Triple key    = 202 (198+4)
    // Quad key      = 273 (264+7)
    // space bar (8) = 549 (528+21)
    // For keywide.svg define WW=key width, W=standard key width

    //
    // Keyboard rows start here
    //

    // ESC blank F1-F4 BLANK F5-F8 BLANK F9-F12 printscreen scrolllock pauebreak
    kb.startRow();
    kb.key("Esc","KB_ESCAPE",3,3,null,null,"",null,"FS=18","TXTC=#400");
    kb.spacer(2);
    kb.key("F1","KB_F1",3,3,null,null,"",null,"FS=18");
    kb.key("F2","KB_F2",3,3,null,null,"",null,"FS=18");
    kb.key("F3","KB_F3",3,3,null,null,"",null,"FS=18");
    kb.key("F4","KB_F4",3,3,null,null,"",null,"FS=18");
    kb.spacer(1);
    kb.key("F5","KB_F5",3,3,null,null,"",null,"FS=18");
    kb.key("F6","KB_F6",3,3,null,null,"",null,"FS=18");
    kb.key("F7","KB_F7",3,3,null,null,"",null,"FS=18");
    kb.key("F8","KB_F8",3,3,null,null,"",null,"FS=18");
    kb.spacer(1);
    kb.key("F9","KB_F9",3,3,null,null,"",null,"FS=18");
    kb.key("F10","KB_F10",3,3,null,null,"",null,"FS=18");
    kb.key("F11","KB_F11",3,3,null,null,"",null,"FS=18");
    kb.key("F12","KB_F12",3,3,null,null,"",null,"FS=18");
    kb.spacer(2);
    kb.key("Screen","KB_PRTSCR",3,3,null,null,"",KN,"FS=24","TXTC=#400","S=Print");
    kb.key("Lock","KB_SCROLLLOCK",3,3,null,null,"",KN,"FS=24","TXTC=#400","S=Scroll");
    kb.key("Break","KB_PAUSE",3,3,null,null,"",KN,"FS=24","TXTC=#400","S=Pause");
    kb.endRow();

    // tilde 1-9 0 - = backspace space insert home pgup
    kb.startRow();
    kb.key("`","KB_BACKQUOTE",3,3,null,null,"",KN,"S=~");
    kb.key("1","1",3,3,null,null,"",KN,"S=!");
    kb.key("2","2",3,3,null,null,"",KN,"S=@");
    kb.key("3","3",3,3,null,null,"",KN,"S=#");
    kb.key("4","4",3,3,null,null,"",KN,"S=$");
    kb.key("5","5",3,3,null,null,"",KN,"S=%");
    kb.key("6","6",3,3,null,null,"",KN,"S=^");
    kb.key("7","7",3,3,null,null,"",KN,"S=&");
    kb.key("8","8",3,3,null,null,"",KN,"S=*");
    kb.key("9","9",3,3,null,null,"",KN,"S=(");
    kb.key("0","0",3,3,null,null,"",KN,"S=)");
    kb.key("-","-",3,3,null,null,"",KN,"S=_");
    kb.key("=","=",3,3,null,null,"",KN,"S=+");
    kb.key("BS","KB_BACKSPACE",4,3,null,null,"",KW,"TXTC=#400");
    kb.spacer(2);
    kb.key("Ins","KB_INSERT",3,3,null,null,"",null,"FS=18","TXTC=#400");
    kb.key("Home","KB_HOME",3,3,null,null,"",null,"FS=18","TXTC=#400");
    kb.key("Up","KB_PGUP",3,3,null,null,"",KN,"FS=24","TXTC=#400","S=Page");
    kb.endRow();

    // Tab qwertyuiop[]\ space del end pgdn
    kb.startRow();
    kb.key("Tab","KB_TAB",4,3,null,null,"",KW,"FS=18","TXTC=#400");
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
    kb.key("[","KB_OPEN_BRACKET",3,3,null,null,"",KN,"S={");
    kb.key("]","KB_CLOSE_BRACKET",3,3,null,null,"",KN,"S=}");
    kb.key("\\","KB_BACKSLASH",3,3,null,null,"",KN,"S=|");
    kb.spacer(2);
    kb.key("Del","KB_DELETE",3,3,null,null,"",null,"FS=18","TXTC=#400");
    kb.key("End","KB_END",3,3,null,null,"",null,"FS=18","TXTC=#400");
    kb.key("Down","KB_PGDN",3,3,null,null,"",KN,"FS=24","TXTC=#400","S=Page");
    kb.endRow();

    // Caps asdfghjkl;' enter
    kb.startRow();
    kb.key("Caps","KB_CAPSLOCK",5,3,null,null,"",KW,"FS=18","TXTC=#400");
    kb.key("A","A");
    kb.key("S","S");
    kb.key("D","D");
    kb.key("F","F");
    kb.key("G","G");
    kb.key("H","H");
    kb.key("J","J");
    kb.key("K","K");
    kb.key("L","L");
    kb.key(";",";",3,3,null,null,"",KN,"S=:");
    kb.key("'","KB_OPENQUOTE",3,3,null,null,"",KN,"S=&quot;");
    kb.key("Enter","KB_ENTER",5,3,null,null,"",KW,"FS=18");
    kb.endRow();

    // shift zxcvbnm,./ shift space uparrow
    kb.startRow();
    kb.key("Shift","LEFT_SHIFT",6,3,kb.SHIFT,kb.SHIFT,"",KW,"FS=18");
    kb.key("Z","Z");
    kb.key("X","X");
    kb.key("C","C");
    kb.key("V","V");
    kb.key("B","B");
    kb.key("N","N");
    kb.key("M","M");
    kb.key(",","KB_COMMA");
    kb.key(",","KB_COMMA",3,3,null,null,"",KN,"S=&lt;");
    kb.key(".",".",3,3,null,null,"",KN,"S=&gt;");
    kb.key("/","/",3,3,null,null,"",KN,"S=?");
    kb.key("Shift","RIGHT_SHIFT",4,3,kb.SHIFT,kb.SHIFT,"",KW,"FS=18");
    kb.spacer(5);
    kb.key("UP","KB_UPARROW",3,3,null,null,"",null,"FS=18");
    kb.endRow();

    // ctrl windows alt spacebarn alt windows ctrl space left down right
    kb.startRow();
    kb.key("Ctrl","LEFT_CTRL",4,3,kb.SHIFT,kb.SHIFT,"",KW,"FS=18","TXTC=#400");
    kb.key("Windows","LEFT_GUI",3,3,kb.SHIFT,kb.SHIFT,"",null,"FS=18","TXTC=#400");
    kb.key("Alt","LEFT_ALT",3,3,kb.SHIFT,kb.SHIFT,"",null,"FS=18","TXTC=#400");
    kb.key("","KB_SPACE",24,3,null,null,"",KW);
    kb.key("Alt","RIGHT_ALT",3,3,kb.SHIFT,kb.SHIFT,"",null,"FS=18","TXTC=#400");
    kb.key("Menu","RIGHT_GUI",3,3,kb.SHIFT,kb.SHIFT,"",null,"FS=18","TXTC=#400");
    kb.key("Ctrl","RIGHT_CTRL",3,3,kb.SHIFT,kb.SHIFT,"",null,"FS=18","TXTC=#400");
    kb.spacer(2);
    kb.key("Left","KB_LEFTARROW",3,3,null,null,"",null,"FS=18");
    kb.key("Down","KB_DOWNARROW",3,3,null,null,"",null,"FS=18");
    kb.key("Right","KB_RIGHTARROW",3,3,null,null,"",null,"FS=18");
    kb.endRow();

    // crtl windoze alt space alt ctrl
    //
    // Keyboard is finished
    //
    kb.endKeyboard();
    kb.endHtml();
%>
