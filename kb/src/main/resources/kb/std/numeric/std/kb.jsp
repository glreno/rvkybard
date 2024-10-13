<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.jsp.KybardJspHelper" %>
<%
    // Page title, and keyboard size in cells. 4 keys * 3 cells wide, 5 keys * 3 + 1 cells high
    // (extra row for contact & numlock lights)
    KybardJspHelper kb=new KybardJspHelper(out,"Simple Keypad",4*3+2,5*3+2,null);
    // secret incantation to remove the keyboard title from the copyright message.
    kb.includeTitleInCopyright(false);

    // Default key SVG and size (in cell spans)
    String DS="std/keys/";
    String DA="atari/keys/";
    kb.setDefaultSvg(DS+"key.svgt",3,3,"FS=48");
    String KP=DS+"keypad.svgt";
    String KLED=DS+"led.svgt";
    String MK=DA+"key2.svgt";

    kb.startHtml();
%>
<!-- custom styles go here -->
<style>
.kybard-container {
  background-color: #888888;
}
.kybard-menu-container {
    background-color: rgb(98,48,48);
}
.NUMLOCK-LED-ON {
  fill: #64d743;
}
.NUMLOCK-LED-OFF {
  fill: #aaa;
}
.CONTACT-LED-ON {
  fill: #64d743;
}
.CONTACT-LED-OFF {
  fill: #aaa;
}
.USBLOST-LED-ON {
  fill: #aaa;
}
.USBLOST-LED-OFF {
  fill: #64d743;
}

</style>
<script type="text/javascript" language="javascript">
    // custom javascript goes here
</script>
</head>
<body>
<%
    kb.startKeyboard();
    kb.startRow();
    kb.endRowThirds(1);
    //
    // Keyboard rows start here
    //

    // Top Row
    kb.startRow();
    kb.spacer(1);
    kb.key("MENU","",3,3,"panic()",kb.MENU,"",KP,"name=","S=Menu");
    kb.key("/","KP_DIVIDE");
    kb.key("*","KP_MULTIPLY");
    kb.key("-","KP_MINUS");
    kb.endRow();

    // 7 8 9 + (+ is two rows)
    kb.startRow();
    kb.spacer(1);
    kb.key("7","KP_7",3,3,null,null,"",KP,"S=Home");
    kb.key("8","KP_8",3,3,null,null,"",KP,"S=&#x25B2;");
    kb.key("9","KP_9",3,3,null,null,"",KP,"S=Pg Up");
    kb.key("+","KP_ADD",3,6,null,null,"",null);
    kb.endRow();

    // 4 5 6 (+ is two rows)
    kb.startRow();
    kb.spacer(1);
    kb.key("4","KP_4",3,3,null,null,"",KP,"S=&#x25C0;");
    kb.key("5","KP_5",3,3,null,null,"",KP);
    kb.key("6","KP_6",3,3,null,null,"",KP,"S=&#x25B6;");
    kb.endRow();

    // 1 2 3 Enter (Enter is two rows)
    kb.startRow();
    kb.spacer(1);
    kb.key("1","KP_1",3,3,null,null,"",KP,"S=End");
    kb.key("2","KP_2",3,3,null,null,"",KP,"S=&#x25BC;");
    kb.key("3","KP_3",3,3,null,null,"",KP,"S=Pg Dn");
    kb.key("Enter","KP_ENTER",3,6,null,null,"",KP,"name=","S=Enter","H=132");
    kb.endRow();

    // 0 . (0 is two cols, Enter is two rows)
    kb.startRow();
    kb.spacer(1);
    kb.key("","",1,1,"doNothing()","doNothing()","",KLED,"CLS=CONTACT-LED");
    kb.key("0","KP_0",5,3,null,null,"",KP,"S=Insert");
    kb.key(".","KP_DOT",3,3,null,null,"",KP,"S=Del");
    kb.endRowThirds(1);
    kb.startRow();
    kb.spacer(1);
    kb.key("","",1,1,"doNothing()","doNothing()","",KLED,"CLS=USBLOST-LED");
    kb.endRowThirds(1);
    kb.startRow();
    kb.spacer(1);
    kb.key("","",1,1,"doNothing()","doNothing()","",KLED,"CLS=NUMLOCK-LED");
    kb.endRowThirds(1);

    //
    // Keyboard is finished
    //
    kb.endKeyboard();

    // Define a slightly modified version of the popup menu
    String ATARISHIFT="SHFBGC=#CF8710"; // 207,135,16
    String ATARIKEY="BGC=#776047"; // 119,96,71
    kb.startMenu();
    kb.menuClose("atari/keys/key2.svgt",ATARIKEY,ATARISHIFT);

    kb.startRow();
    kb.spacer(1);
    kb.key("Numlock","KP_NUMLOCK",3,3,null,null,"",MK,"name=Lock","S=Num",ATARIKEY,ATARISHIFT);
    kb.endRow();
    kb.menuReturnToMain("atari/keys/key2.svgt","std/keys/led.svgt",ATARIKEY,ATARISHIFT);
    kb.menuLogout("atari/keys/key2.svgt","std/keys/led.svgt",ATARIKEY,ATARISHIFT);
    kb.endKeyboard();

    kb.endHtml();
%>
