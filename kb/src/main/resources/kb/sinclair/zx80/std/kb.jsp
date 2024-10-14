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
    kb.setDefaultSvg(DK+"key.svgt",5,5,"FS=32","BORD=4","BGC=#033D91","TXTC=#D6D2CE","GOLDC=#E5A005");
    String KN=DK+"key.svgt";  // 123490 - large digit, with a gold word above the key
    String KNi=DK+"key.svgt"; // 5678 - large digit, with a gold arrow above the key
    String KL=DK+"key.svgt";  // LZMP. - Letter, with a gold shifted char
    String KK=DK+"key.svgt";  // YUIO HJK ZXCVN - Letter, gold shifted char, white command above
    String KKi=DK+"key.svgt"; // QWERT ASDFG - Letter, gold icon, white command above
    String KNL=DK+"key.svgt"; // New Line - two-line label, gold command above
    String KSP=DK+"key.svgt"; // space - word space, gold pound sign, BLUE command above

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
    kb.key("1","1",5,5,null,null,"",KN,"S=NOT");
    kb.key("2","2",5,5,null,null,"",KN,"S=AND");
    kb.key("3","3",5,5,null,null,"",KN,"S=THEN");
    kb.key("4","4",5,5,null,null,"",KN,"S=TO");
    kb.key("5","5",5,5,null,null,"",KNi,"S=left");
    kb.key("6","6",5,5,null,null,"",KNi,"S=down");
    kb.key("7","7",5,5,null,null,"",KNi,"S=up");
    kb.key("8","8",5,5,null,null,"",KNi,"S=right");
    kb.key("9","9",5,5,null,null,"",KN,"S=HOME");
    kb.key("0","0",5,5,null,null,"",KN,"S=RUBOUT");
    kb.key("Menu","",3,3,"panic()",kb.MENU,"",null,"FS=10");
    kb.endRowThirds(5);

    // qwertyuiop
    kb.startRow();
    kb.spacer(2);
    kb.key("Q","Q");
    kb.key("W","W",5,5,null,null,"",KKi);
    kb.key("E","E");
    kb.key("R","R");
    kb.key("T","T");
    kb.key("Y","Y");
    kb.key("U","U");
    kb.key("I","I");
    kb.key("O","O");
    kb.key("P","P");
    kb.endRowThirds(5);

    // asdfghjkl newline
    kb.startRow();
    kb.spacer(3);
    kb.key("A","A");
    kb.key("S","S");
    kb.key("D","D");
    kb.key("F","F");
    kb.key("G","G");
    kb.key("H","H");
    kb.key("J","J");
    kb.key("K","K");
    kb.key("L","L");
    kb.key("NEW LINE","KB_ENTER",5,5,null,null,"",KNL,"FS=16");
    kb.endRowThirds(5);

    // shift zxcvbnm . spacebarn
    kb.startRow();
    kb.spacer(1);
    kb.key("SHIFT","LEFT_SHIFT",5,5,kb.SHIFT,kb.SHIFT,"",null,"FS=16");
    kb.key("Z","Z");
    kb.key("X","X");
    kb.key("C","C");
    kb.key("V","V");
    kb.key("B","B");
    kb.key("N","N");
    kb.key("M","M",5,5,null,null,"",KL);
    kb.key(".",".",5,5,null,null,"",KL,"S=,");
    kb.key("SPACE","KB_SPACE",5,5,null,null,"",KSP);
    kb.endRowThirds(5);

    //
    // Keyboard is finished
    //
    kb.endKeyboard();

    // Define the popup menu
    kb.menu();

    kb.endHtml();
%>
