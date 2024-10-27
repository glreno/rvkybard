<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.jsp.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"Spectrumish",11*5+2,4*5+3,null);
    kb.loadDefault("COPYRIGHTMESSAGE", "Sinclair Spectrum originally copyright/trademarks of Sinclair Research LTD 1982");
    kb.setDefaultCellSize(12,12,1,2);

    // Default key SVG and size
    String DA="atari/keys/";
    String DS="std/keys/";
    String DART="std/keys/art/";
    String DK="sinclair/spectrum/keys/";
    String BGC="#4F6361";
    String TXTC="#FCFCFC";
    String REDC="#ed7977"; // ed7977	HSV 1,50,93
    String GREENC="#6bd66b"; // 0CFF0C
    kb.setDefaultSvg(DK+"key.svgt",5,5,"FS=20","FSs=10","FSk=10","FSf=10","FSe=10","BGC="+BGC,"TXTC="+TXTC,"GREENC="+GREENC,"REDC="+REDC,"OffsetXs=0","OffsetYs=0","BP=1.2","BW=8","BH=8");
    String KC=DK+"keyColor.svgt";  // Color labels, not actually a key
    String KK=DK+"keyL.svgt";  // Standard key - Letter, command, red shift, func, shift func
    String KKi=DK+"keyLi.svgt";  // Standard key, graphics in place of red shift
    String KN=DK+"keyN.svgt"; // 90 Number key with no graphic
    String KNi=DK+"keyNi.svgt"; // 1234 Number key with graphic
    String KNii=DK+"keyNii.svgt"; // 5678 - with graphic, and an arrow
    String KMENU=DK+"keyMenu.svgt"; // rrvkb menu

    kb.startHtml();
%>
<!-- custom styles go here -->
<style>
.kybard-container {
    background-color: rgb(34,33,38);
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

    // the Spectrum Stripes
    // Order of the stripes is RED YELLOW GREEN BLUE
    // These start at the same X as the start of the space key, at the bottom of the kb;
    // and slope upwards to about the top of the P key, at the right edge.
    // The space key is at grid-area: 18/49/span 5/span 7
    // the P key is at     grid-area:  8/50/span 5/span 5
    // and the keyboard grid is 57x23 12x12 squares with 2px gaps
    // Thus the stripes in grid-area:  8/14/span 16/span 9
    // and the svg size is 9*14 x 16*14 = 126x224
    // In my 1024x434 picture of a real spectrum, the red stripe is at X=888 to 902, Y=134 to 167
    // w=14px, 14/1024=1.3%; h=33px, 33/434=7.6%
    // 1.3% * (57*14) = 10.3px; 7.6% * (23*14) = 24.5px
    // ....and all that math is off because my keyboard is actually WIDER by a menu key.
    // Move everything up by 3 grid cells (42px)

%><div style="grid-area: 5/49/span 18/span 9;">
<svg x='0' y='0' width='128' height='268'>
<polygon style='fill:#F66254;' points=' 5,268 128,0  128,21 16,268'/>
<polygon style='fill:#FFC35E;' points='15,268 128,20 128,41 26,268'/>
<polygon style='fill:#619A49;' points='25,268 128,40 128,61 36,268'/>
<polygon style='fill:#4A8FDA;' points='35,268 128,60 128,81 46,268'/>
</svg></div><%

    //
    // Keyboard rows start here
    //

    // The rules of Spectrum typing:
    // K gets you a digit, or the white command on the key.
    // SYMBOL-SHIFT gets you the red thing on the key
    // E gets you the green function above the key - or set INK color
    // E+CAPS-SHIFT sets PAPER color
    // E+SYMBOL-SHIFT gets you the red function below the number key
    // E+SHIFT (either one) gets you the red function below the letter key
    // G gets you the graphic icon on a number key (and limits letter keys to upper case)
    // G+SHIFT (either one) gets you the inverse graphics char
    // L gets you a digit or lower case letter
    // L+CAPS-SHIFT gets you upper case letter or an white edit more above a number key

    // Color labels
    kb.startRow();
    kb.endRowThirds(1);
    kb.startRow();
    kb.spacer(2+0);
    kb.key("BLUE","1",5,5,null,null,"",KC,"FS=10","TXTC=#55F","BGC=none");
    kb.key("RED","2",5,5,null,null,"",KC,"FS=10","TXTC=#F00","BGC=none");
    kb.key("MAGENTA","3",5,5,null,null,"",KC,"FS=10","TXTC=#F0F","BGC=none");
    kb.key("GREEN","4",5,5,null,null,"",KC,"FS=10","TXTC=#0cff0c","BGC=none");
    kb.key("CYAN","5",5,5,null,null,"",KC,"FS=10","TXTC=#0FF","BGC=none");
    kb.key("YELLOW","6",5,5,null,null,"",KC,"FS=10","TXTC=#FF0","BGC=none");
    kb.key("WHITE","7",5,5,null,null,"",KC,"FS=10","TXTC=#FFF","BGC=none");
    kb.spacer(10);
    kb.key("BLACK","0",5,5,null,null,"",KC,"FS=10","TXTC=#000","BGC=#FFF");
    kb.endRowThirds(1);
    // 1-9 0 and menu
    kb.startRow();
    kb.spacer(2+0);
    kb.key("1","1",5,5,null,null,"",KNi,"FS=24","FSs=12","Ss=!","Sf=EDIT","Se=DEF FN","INC1="+DART+"filledbox/ine.svgt");
    kb.key("2","2",5,5,null,null,"",KNi,"FS=24","FSs=12","Ss=@","Sf=CAPS LOCK","Se=FN","INC1="+DART+"filledbox/inw.svgt");
    kb.key("3","3",5,5,null,null,"",KNi,"FS=24","FSs=12","Ss=#","Sf=TRUE VIDEO","Se=LINE","INC1="+DART+"filledbox/s.svgt");
    kb.key("4","4",5,5,null,null,"",KNi,"FS=24","FSs=12","Ss=$","Sf=INV. VIDEO","Se=OPEN#","INC1="+DART+"filledbox/ise.svgt");
    kb.key("5","5",5,5,null,null,"",KNii,"FS=24","FSs=12","Ss=%","Se=CLOSE#","SX=2","SY=-1","SHAFTLENGTH=7","SHAFTWIDTH=5","ARROWLENGTH=8","ARROWWIDTH=9","ARROWCOLOR="+TXTC,"ARROW=90","INC2="+DART+"arrowOutline.svgt","INC1="+DART+"filledbox/w.svgt");
    kb.key("6","6",5,5,null,null,"",KNii,"FS=24","FSs=12","Ss=&amp;","Se=MOVE","SX=0","SY=-1","SHAFTLENGTH=3","SHAFTWIDTH=10","ARROWLENGTH=4","ARROWWIDTH=20","ARROWCOLOR="+TXTC,"ARROW=0","INC2="+DART+"arrowOutline.svgt","INC1="+DART+"filledbox/slash.svgt");
    kb.key("7","7",5,5,null,null,"",KNii,"FS=24","FSs=12","Ss='","Se=ERASE","SX=0","SY=0","SHAFTLENGTH=3","SHAFTWIDTH=10","ARROWLENGTH=4","ARROWWIDTH=20","ARROWCOLOR="+TXTC,"ARROW=180","INC2="+DART+"arrowOutline.svgt","INC1="+DART+"filledbox/sw.svgt");
    kb.key("8","8",5,5,null,null,"",KNii,"FS=24","FSs=12","Ss=(","Se=POINT","SX=-1","SY=-1","SHAFTLENGTH=7","SHAFTWIDTH=5","ARROWLENGTH=8","ARROWWIDTH=9","ARROWCOLOR="+TXTC,"ARROW=270","INC2="+DART+"arrowOutline.svgt","INC1="+DART+"filledbox/iall.svgt");
    kb.key("9","9",5,5,null,null,"",KN,"FS=24","FSs=12","Ss=)","Sf=GRAPHICS","Se=CAT");
    kb.key("0","0",5,5,null,null,"",KN,"FS=24","FSs=18","Ss=_","Sf=DELETE","Se=FORMAT","OffsetYs=-12");
    kb.key("MENU","",5,5,"panic()",kb.MENU,"",KMENU,"FS=12");
    kb.endRowThirds(5);

    // qwertyuiop
    kb.startRow();
    kb.spacer(2+2);
    kb.key("Q","Q",5,5,null,null,"",KK,"Ss=<=","Sk=PLOT","Sf=SIN","Se=ASN","FSs=16");
    kb.key("W","W",5,5,null,null,"",KK,"Ss=<>","Sk=DRAW","Sf=COS","Se=ACS","FSs=16");
    kb.key("E","E",5,5,null,null,"",KK,"Ss=>=","Sk=REM","Sf=TAN","Se=ATN","FSs=16");
    kb.key("R","R",5,5,null,null,"",KK,"Ss=<","Sk=RUN","Sf=INT","Se=VERIFY","FSs=16");
    kb.key("T","T",5,5,null,null,"",KK,"Ss=>","Sk=RAND","Sf=RND","Se=MERGE","FSs=16");
    kb.key("Y","Y",5,5,null,null,"",KK,"Ss=AND","Sf=STR$","Sk=RETURN","Se=[");
    kb.key("U","U",5,5,null,null,"",KK,"Ss=OR","Sk=IF","Sf=CHR$","Se=]");
    kb.key("I","I",5,5,null,null,"",KK,"Ss=AT","Sk=INPUT","Sf=CODE","Se=IN");
    kb.key("O","O",5,5,null,null,"",KK,"Ss=;","Sk=POKE","Sf=PEEK","Se=OUT","FSs=20");
    kb.key("P","P",5,5,null,null,"",KK,"Ss=\"","Sk=PRINT","Sf=TAB","Se=&copy;","FSs=20","FSe=12");
    kb.endRowThirds(5);

    // asdfghjkl newline
    kb.startRow();
    kb.spacer(2+3);
    kb.key("A","A",5,5,null,null,"",KK,"Ss=STOP","Sk=NEW","Sf=READ","Se=~","FSe=14");
    kb.key("S","S",5,5,null,null,"",KK,"Ss=NOT","Sk=SAVE","Sf=RESTORE","Se=|");
    kb.key("D","D",5,5,null,null,"",KK,"Ss=STEP","Sk=DIM","Sf=DATA","Se=\\");
    kb.key("F","F",5,5,null,null,"",KK,"Ss=TO","Sk=FOR","Sf=SGN","Se={");
    kb.key("G","G",5,5,null,null,"",KK,"Ss=THEN","Sk=GOTO","Sf=ABS","Se=}");
    kb.key("H","H",5,5,null,null,"",KKi,"Sk=GOSUB","Sf=SQR","Se=CIRCLE","SX=0","SY=0","ARROWLENGTH=10","ARROWWIDTH=10","ARROWCOLOR="+REDC,"ARROW=180","INC1="+DART+"arrow2.svgt");
    kb.key("J","J",5,5,null,null,"",KK,"Ss=-","FSs=24","Sk=LOAD","Sf=VAL","Se=VAL$","OffsetYs=-4");
    kb.key("K","K",5,5,null,null,"",KK,"Ss=+","FSs=20","Sk=LIST","Sf=LEN","Se=SCREEN$");
    kb.key("L","L",5,5,null,null,"",KK,"Ss==","Sk=LET","Sf=USR","FSs=16","Se=ATTR","OffsetYs=-2");
    kb.key("ENTER","KB_ENTER",5,5,null,null,"",null,"FS=10");
    kb.endRowThirds(5);

    // shift zxcvbnm . spacebarn
    kb.startRow();
    kb.spacer(2);
    kb.key("CAPS","LEFT_SHIFT",6,5,kb.SHIFT,kb.SHIFT,"",null,"FS=14","FSk=14","Sk=SHIFT");
    kb.key("Z","Z",5,5,null,null,"",KK,"Ss=:","Sk=COPY","Sf=LN","FSs=20","Se=BEEP");
    kb.key("X","X",5,5,null,null,"",KK,"Ss=&pound;","Sk=CLEAR","Sf=EXP","Sk=CLEAR","Se=INK","FSs=14");
    kb.key("C","C",5,5,null,null,"",KK,"Ss=?","Sk=CONT","Sf=LPRINT","FSs=16","Se=PAPER");
    kb.key("V","V",5,5,null,null,"",KK,"Ss=/","Sk=CLS","Sf=LLIST","FSs=16","Se=FLASH");
    kb.key("B","B",5,5,null,null,"",KK,"Ss=*","Sk=BORDER","Sf=BIN","FSs=18","Se=BRIGHT");
    kb.key("N","N",5,5,null,null,"",KK,"Ss=,","Sk=NEXT","Sf=INKEY$","FSs=20","Se=OVER","OffsetYs=-7");
    kb.key("M","M",5,5,null,null,"",KK,"Ss=.","Sk=PAUSE","Sf=PI","FSs=20","Se=INVERSE","OffsetYs=-6");
    kb.key("SYMBOL","LEFT_CTRL",5,5,kb.SHIFT,kb.SHIFT,"",null,"FS=10","Sk=SHIFT","TXTC="+REDC);
    kb.key("BREAK","KB_SPACE",7,5,null,null,"",null,"FS=10","FSk=14","Sk=SPACE");
    kb.endRowThirds(5);

    //
    // Keyboard is finished
    //
    kb.endKeyboard();

    // Define the popup menu
    kb.menu();

    kb.endHtml();
%>
