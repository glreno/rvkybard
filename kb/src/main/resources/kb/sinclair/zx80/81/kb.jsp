<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.jsp.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"ZX80ish",20*5,6*5,null);
    kb.setDefaultCellSize(12,12,1,2);

    // Default key SVG and size
    String DA="atari/keys/";
    String DS="std/keys/";
    String DART="std/keys/art/";
    String DK="sinclair/zx80/keys/";
    //String BGC="#033d91";
    String BGC="#10499f";
    String TXTC="#D6D2CE";
    String GOLDC="#E5A005";
    kb.setDefaultSvg(DK+"key.svgt",5,5,"FS=18","FSs=12","FSk=12","BGC="+BGC,"TXTC=#D6D2CE","GOLDC=#E5A005","OffsetYs=0");
    String KN=DK+"keyN.svgt";  // 123490 - large digit, with a gold word above the key
    String KNi=DK+"keyN.svgt"; // 5678 - large digit, with a gold arrow above the key
    String KL=DK+"keyL.svgt";  // LZMP - Letter, with a gold shifted char
    String KDOT=DK+"keyDot.svgt";  // . - Letter, with a gold shifted char
    String KK=DK+"keyL.svgt";  // YUIO HJK ZXCVN - Letter, gold shifted char, white command above
    String KKi=DK+"keyLi.svgt"; // QWERT ASDFG - Letter, gold icon, white command above
    String KNL=DK+"keyNL.svgt"; // New Line - two-line label, gold command above
    String KSP=DK+"keySp.svgt"; // space - word space, gold pound sign, BLUE command above

    kb.startHtml();
%>
<!-- custom styles go here -->
<style>
.kybard-container {
    background-color: rgb(48,48,48);
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
  fill: #aaa;
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

    // The rules of ZX80 typing:
    // K gets you a digit, or the white command above the key.
    // L gets you a digit or letter
    // SHIFT+KEY gets you the gold thing, in any mode, whether it is on the key or above it.

    // 1-9 0 and menu
    kb.startRow();
    kb.spacer(2+0);
    kb.key("1","1",5,5,null,null,"",KN,"FS=24","Ss=NOT");
    kb.key("2","2",5,5,null,null,"",KN,"FS=24","Ss=AND");
    kb.key("3","3",5,5,null,null,"",KN,"FS=24","Ss=THEN");
    kb.key("4","4",5,5,null,null,"",KN,"FS=24","Ss=TO");
    kb.key("5","5",5,5,null,null,"",KNi,"FS=24","Ss=left");
    kb.key("6","6",5,5,null,null,"",KNi,"FS=24","Ss=down");
    kb.key("7","7",5,5,null,null,"",KNi,"FS=24","Ss=up");
    kb.key("8","8",5,5,null,null,"",KNi,"FS=24","Ss=right");
    kb.key("9","9",5,5,null,null,"",KN,"FS=24","Ss=HOME");
    kb.key("0","0",5,5,null,null,"",KN,"FS=24","Ss=RUBOUT");
    kb.key("Menu","",3,5,"panic()",kb.MENU,"",null,"FS=10");
    kb.endRowThirds(5);

    // qwertyuiop
    kb.startRow();
    kb.spacer(2+2);
    kb.key("Q","Q",5,5,null,null,"",KKi,"Sk=NEW","BP=1.6","BW=8","BH=8","INC1="+DART+"filledbox/w.svgt");
    kb.key("W","W",5,5,null,null,"",KKi,"Sk=LOAD","BP=1.6","BW=8","BH=8","INC1="+DART+"filledbox/s.svgt");
    kb.key("E","E",5,5,null,null,"",KKi,"Sk=SAVE","BP=1.6","BW=8","BH=8","INC1="+DART+"filledbox/nw.svgt");
    kb.key("R","R",5,5,null,null,"",KKi,"Sk=RUN","BP=1.6","BW=8","BH=8","INC1="+DART+"filledbox/ne.svgt");
    kb.key("T","T",5,5,null,null,"",KKi,"Sk=CONT","BP=1.6","BW=8","BH=8","INC1="+DART+"halftonebox/s.svgt");
    kb.key("Y","Y",5,5,null,null,"",KK,"Ss=\"","FSs=20","Sk=REM","OffsetYs=6");
    kb.key("U","U",5,5,null,null,"",KK,"Ss=$","Sk=IF");
    kb.key("I","I",5,5,null,null,"",KK,"Ss=(","Sk=INPUT");
    kb.key("O","O",5,5,null,null,"",KK,"Ss=)","Sk=PRINT");
    kb.key("P","P",5,5,null,null,"",KL,"Ss=*","FSs=20","OffsetYs=6");
    kb.endRowThirds(5);

    // asdfghjkl newline
    kb.startRow();
    kb.spacer(2+3);
    kb.key("A","A",5,5,null,null,"",KKi,"Sk=LIST","BP=1.6","BW=8","BH=8","INC1="+DART+"halftonebox/all.svgt");
    kb.key("S","S",5,5,null,null,"",KKi,"Sk=STOP","BP=1.6","BW=8","BH=8","INC1="+DART+"filledbox/slash.svgt");
    kb.key("D","D",5,5,null,null,"",KKi,"Sk=DIM","BP=1.6","BW=8","BH=8","INC1="+DART+"filledbox/sw.svgt");
    kb.key("F","F",5,5,null,null,"",KKi,"Sk=FOR","BP=1.6","BW=8","BH=8","INC1="+DART+"filledbox/se.svgt");
    kb.key("G","G",5,5,null,null,"",KKi,"Sk=GOTO","BP=1.6","BW=8","BH=8","INC1="+DART+"halftonebox/n.svgt");
    kb.key("H","H",5,5,null,null,"",KK,"Ss=**","Sk=POKE");
    kb.key("J","J",5,5,null,null,"",KK,"Ss=-","FSs=20","Sk=RAND");
    kb.key("K","K",5,5,null,null,"",KK,"Ss=+","FSs=20","Sk=LET");
    kb.key("L","L",5,5,null,null,"",KL,"Ss==");
    kb.key("NEW LINE","KB_ENTER",5,5,null,null,"",KNL);
    kb.endRowThirds(5);

    // shift zxcvbnm . spacebarn
    kb.startRow();
    kb.spacer(2+1);
    kb.key("SHIFT","LEFT_SHIFT",5,5,kb.SHIFT,kb.SHIFT,"",null,"FS=12","TXTC="+GOLDC);
    kb.key("Z","Z",5,5,null,null,"",KL,"Ss=:","FSs=20");
    kb.key("X","X",5,5,null,null,"",KK,"Ss=;","FSs=20","Sk=CLEAR");
    kb.key("C","C",5,5,null,null,"",KK,"Ss=?","Sk=CLS");
    kb.key("V","V",5,5,null,null,"",KK,"Ss=/","Sk=GOSUB");
    kb.key("B","B",5,5,null,null,"",KK,"Ss=OR","Sk=RET");
    kb.key("N","N",5,5,null,null,"",KK,"Ss=<","Sk=NEXT");
    kb.key("M","M",5,5,null,null,"",KL,"Ss=>");
    kb.key(".",".",5,5,null,null,"",KDOT,"Ss=,","FS=28","FSs=24");
    kb.key("SPACE","KB_SPACE",5,5,null,null,"",KSP,"FS=12","Ss=&pound;","Sk=BREAK");
    kb.endRowThirds(5);

    //
    // Keyboard is finished
    //
    kb.endKeyboard();

    // Define the popup menu
    kb.menu();

    kb.endHtml();
%>
