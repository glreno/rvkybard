<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"Star Raiders",null);
    // Default key SVG and size
    kb.setDefaultSvg("numeric/keys/key.svgt",66,66,3,3,"FS=48");
    String KP="numeric/keys/keypad.svgt";
    kb.startHtml();
%>
<!-- custom styles go here -->
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

    // Top Row
    kb.startRow();
    kb.key("1","KB_1");
    kb.key("2","KB_2");
    kb.key("3","KB_3");
    kb.key("4","KB_4");
    kb.key("5","KB_5");
    kb.key("6","KB_6");
    kb.key("7","KB_7");
    kb.key("8","KB_8");
    kb.key("9","KB_9");
    kb.key(" ","KB_SPACE");
    kb.key("0","KB_0");
    kb.endRow();

    // F C T G H Option
    kb.startRow();
    kb.key("F","KB_F",6,6,null,null,"",KP,"S=Forward View","W=132","H=132");
    kb.key("C","KB_C",6,6,null,null,"",KP,"S=Attack Computer","W=132","H=132");
    kb.key("T","KB_T",6,6,null,null,"",KP,"S=Targeting Computer","W=132","H=132");
    kb.key("G","KB_G",6,6,null,null,"",KP,"S=Galactic Chart","W=132","H=132");
    kb.key("H","KB_H",6,6,null,null,"",KP,"S=Hyperwarp","W=132","H=132");
    kb.key("Option","KB_F4");
    kb.endRow();

    // Select
    kb.startRow();
    kb.key("Select","KB_F3");
    kb.endRow();

    // A S M L   Start
    kb.startRow();
    kb.key("A","KB_A",6,6,null,null,"",KP,"S=Aft View","W=132","H=132");
    kb.key("S","KB_S",6,6,null,null,"",KP,"S=Shields","W=132","H=132");
    kb.key("M","KB_M",6,6,null,null,"",KP,"S=Manual Target Select","W=132","H=132");
    kb.key("L","KB_L",6,6,null,null,"",KP,"S=Long Range Scan","W=132","H=132");
    kb.key(" ","KB_SPACE",6,6,null,null,"",KP,"W=132","H=132");
    kb.key("Start","KB_F2");
    kb.endRow();

    // P
    kb.startRow();
    kb.key("Pause","KB_P");
    kb.endRow();

    //
    // Keyboard is finished
    //
    kb.endKeyboard();
    kb.endHtml();
%>