<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.jsp.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"ZX81ish",11*5+2,4*5+1,null);
    kb.loadDefault("COPYRIGHTMESSAGE", "Sinclair ZX81 originally copyright/trademarks of Sinclair Research LTD 1981");
    kb.setDefaultCellSize(12,12,1,2);

    // Default key SVG and size
    String DA="atari/keys/";
    String DS="std/keys/";
    String DART="std/keys/art/";
    String DK="sinclair/zx81/keys/";
    String BGC="#FFFFFF";
    String TXTC="#000";
    String REDC="#FF0C0C";
    kb.setDefaultSvg(DK+"key.svgt",5,5,"FS=20","FSs=10","FSk=10","BGC="+BGC,"TXTC="+TXTC,"REDC="+REDC,"OffsetXs=0","OffsetYs=0","BP=1.6","BW=8","BH=8");
    String KK=DK+"keyL.svgt";  // Standard key, no graphic - Letter, command, red shift, func
    String KKi=DK+"keyLi.svgt"; // Standard key with graphic
    String KNi=DK+"keyNi.svgt"; // 5678 - digit, with graphic, with a red arrow for shift
    String KM=DK+"keyM.svgt";  // M - Standard key, but with different font for func
    String KDOT=DK+"keyDot.svgt";  // . and ,
    String KNL=DK+"keyNL.svgt"; // New Line - two-line label, and red shift
    String KSP=DK+"keySp.svgt"; // space - word space, red pound sign, command above
    String KMENU=DK+"keyMenu.svgt"; // rrvkb menu

    kb.startHtml();
%>
<!-- custom styles go here -->
<style>
.kybard-container {
    background-color: rgb(0,0,0);
}
.kybard-menu-container {
    background-color: rgb(98,48,48);
}
.kbbuttonDown .depress {
  fill: #888 !important;
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
  fill: <%=BGC%>;
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

    // The rules of ZX81 typing:
    // K gets you a digit, or the white command above the key.
    // F gets you the white function under the key
    // G gets you the graphic icon
    // L gets you a digit or letter
    // SHIFT+KEY gets you the red thing, in any mode

    // 1-9 0 and menu
    kb.startRow();
    kb.spacer(2+0);
    kb.key("1","1",5,5,null,null,"",KKi,"FS=24","Ss=EDIT","INC1="+DART+"filledbox/nw.svgt");
    kb.key("2","2",5,5,null,null,"",KKi,"FS=24","Ss=AND","INC1="+DART+"filledbox/ne.svgt");
    kb.key("3","3",5,5,null,null,"",KKi,"FS=24","Ss=THEN","INC1="+DART+"filledbox/se.svgt");
    kb.key("4","4",5,5,null,null,"",KKi,"FS=24","Ss=TO","INC1="+DART+"filledbox/sw.svgt");
    kb.key("5","5",5,5,null,null,"",KNi,"FS=24","SX=5","SY=-1","SHAFTLENGTH=6","SHAFTWIDTH=6","ARROWLENGTH=14","ARROWWIDTH=12","ARROWCOLOR="+REDC,"ARROW=90","INC2="+DART+"arrowOutline.svgt","INC1="+DART+"filledbox/w.svgt");
    kb.key("6","6",5,5,null,null,"",KNi,"FS=24","SX=0","SY=-3","SHAFTLENGTH=4","SHAFTWIDTH=10","ARROWLENGTH=6","ARROWWIDTH=20","ARROWCOLOR="+REDC,"ARROW=0","INC2="+DART+"arrowOutline.svgt","INC1="+DART+"filledbox/s.svgt");
    kb.key("7","7",5,5,null,null,"",KNi,"FS=24","SX=0","SY=-1","SHAFTLENGTH=4","SHAFTWIDTH=10","ARROWLENGTH=6","ARROWWIDTH=20","ARROWCOLOR="+REDC,"ARROW=180","INC2="+DART+"arrowOutline.svgt","INC1="+DART+"filledbox/n.svgt");
    kb.key("8","8",5,5,null,null,"",KNi,"FS=24","SX=-3","SY=-1","SHAFTLENGTH=6","SHAFTWIDTH=6","ARROWLENGTH=14","ARROWWIDTH=12","ARROWCOLOR="+REDC,"ARROW=270","INC2="+DART+"arrowOutline.svgt","INC1="+DART+"filledbox/e.svgt");
    kb.key("9","9",5,5,null,null,"",KK,"FS=24","Ss=GRAPHICS","OffsetXs=5");
    kb.key("0","0",5,5,null,null,"",KK,"FS=24","Ss=RUBOUT");
    kb.key("MENU","",5,5,"panic()",kb.MENU,"",KMENU,"FS=12");
    kb.endRowThirds(5);

    // qwertyuiop
    kb.startRow();
    kb.spacer(2+2);
    kb.key("Q","Q",5,5,null,null,"",KKi,"Ss=\"\"","Sk=PLOT","Sf=SIN","FSs=24","OffsetYs=6","INC1="+DART+"filledbox/inw.svgt");
    kb.key("W","W",5,5,null,null,"",KKi,"Ss=OR","Sk=UNPLOT","Sf=COS","INC1="+DART+"filledbox/ine.svgt");
    kb.key("E","E",5,5,null,null,"",KKi,"Ss=STEP","Sk=REM","Sf=TAN","INC1="+DART+"filledbox/ise.svgt");
    kb.key("R","R",5,5,null,null,"",KKi,"Ss=<=","Sk=RUN","Sf=INT","FSs=16","INC1="+DART+"filledbox/isw.svgt");
    kb.key("T","T",5,5,null,null,"",KKi,"Ss=<>","Sk=RAND","Sf=RND","FSs=16","INC1="+DART+"filledbox/slash.svgt");
    kb.key("Y","Y",5,5,null,null,"",KKi,"Ss=>=","Sf=STR$","Sk=RETURN","FSs=16","INC1="+DART+"filledbox/backslash.svgt");
    kb.key("U","U",5,5,null,null,"",KK,"Ss=$","Sk=IF","Sf=CHR$","FSs=16");
    kb.key("I","I",5,5,null,null,"",KK,"Ss=(","Sk=INPUT","Sf=CODE","FSs=16");
    kb.key("O","O",5,5,null,null,"",KK,"Ss=)","Sk=POKE","Sf=PEEK","FSs=16");
    kb.key("P","P",5,5,null,null,"",KK,"Ss=\"","Sk=PRINT","Sf=TAB","FSs=24","OffsetYs=6");
    kb.endRowThirds(5);

    // asdfghjkl newline
    kb.startRow();
    kb.spacer(2+3);
    kb.key("A","A",5,5,null,null,"",KKi,"Ss=STOP","Sk=NEW","Sf=ARCSIN","INC1="+DART+"halftonebox/all.svgt");
    kb.key("S","S",5,5,null,null,"",KKi,"Ss=LPRINT","Sk=SAVE","Sf=ARCCOS","INC1="+DART+"halftonebox/n.svgt");
    kb.key("D","D",5,5,null,null,"",KKi,"Ss=SLOW","Sk=DIM","Sf=ARCTAN","INC1="+DART+"halftonebox/s.svgt");
    kb.key("F","F",5,5,null,null,"",KKi,"Ss=FAST","Sk=FOR","Sf=SGN","INC1="+DART+"halftonebox/is.svgt");
    kb.key("G","G",5,5,null,null,"",KKi,"Ss=LLIST","Sk=GOTO","Sf=ABS","INC1="+DART+"halftonebox/in.svgt");
    kb.key("H","H",5,5,null,null,"",KKi,"Ss=**","Sk=GOSUB","Sf=SQR","FSs=16","INC1="+DART+"halftonebox/iall.svgt");
    kb.key("J","J",5,5,null,null,"",KK,"Ss=-","FSs=20","Sk=LOAD","Sf=VAL");
    kb.key("K","K",5,5,null,null,"",KK,"Ss=+","FSs=20","Sk=LIST","Sf=LEN");
    kb.key("L","L",5,5,null,null,"",KK,"Ss==","Sk=LET","Sf=USR","FSs=16");
    kb.key("NEW LINE","KB_ENTER",5,5,null,null,"",KNL,"Ss=FUNCTION");
    kb.endRowThirds(5);

    // shift zxcvbnm . spacebarn
    kb.startRow();
    kb.spacer(2+1);
    kb.key("SHIFT","LEFT_SHIFT",5,5,kb.SHIFT,kb.SHIFT,"",null,"FS=12","TXTC="+REDC);
    kb.key("Z","Z",5,5,null,null,"",KK,"Ss=:","Sk=COPY","Sf=LN","FSs=20");
    kb.key("X","X",5,5,null,null,"",KK,"Ss=;","Sk=CLEAR","Sf=EXP","FSs=20","Sk=CLEAR");
    kb.key("C","C",5,5,null,null,"",KK,"Ss=?","Sk=CONT","Sf=AT","FSs=16");
    kb.key("V","V",5,5,null,null,"",KK,"Ss=/","Sk=CLS","FSs=16");
    kb.key("B","B",5,5,null,null,"",KK,"Ss=*","Sk=SCROLL","Sf=INKEY$","FSs=16");
    kb.key("N","N",5,5,null,null,"",KK,"Ss=<","Sk=NEXT","Sf=NOT","FSs=16");
    kb.key("M","M",5,5,null,null,"",KM,"Ss=>","Sk=PAUSE","Sf=&pi;","FSs=16");
    //  keyDownRemap(elem,flags,key,shiftedflags,shifted,ctrlflags,ctrl)
    kb.key(".",".",5,5,
        "keyDownRemap(this,[],'.',[],'KB_COMMA',[],'.')",
        "keyUpRemap(this,'.','KB_COMMA','.')",
        "",KDOT,"Ss=,","FS=28","FSs=24");
    kb.key("SPACE","KB_SPACE",5,5,null,null,"",KSP,"FS=10","Ss=&pound;","Sk=BREAK","FSs=12");
    kb.endRowThirds(5);

    //
    // Keyboard is finished
    //
    kb.endKeyboard();

    // Define the popup menu
    kb.menu();

    kb.endHtml();
%>
