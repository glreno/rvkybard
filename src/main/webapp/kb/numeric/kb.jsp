<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"A Keypad",null);
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
    kb.key("Numlock","KP_NUMLOCK",3,3,null,null,"",KP,"L=","S=Num Lock");
    kb.key("/","KP_DIVIDE");
    kb.key("*","KP_MULTIPLY");
    kb.key("-","KP_MINUS");
    kb.endRow();

    // 7 8 9 + (+ is two rows)
    kb.startRow();
    kb.key("7","KP_7",3,3,null,null,"",KP,"S=Home");
    kb.key("8","KP_8",3,3,null,null,"",KP,"S=&#x25B2;");
    kb.key("9","KP_9",3,3,null,null,"",KP,"S=Pg Up");
    kb.key("+","KP_ADD",3,6,null,null,"",null,"H=132");
    kb.endRow();

    // 4 5 6 (+ is two rows)
    kb.startRow();
    kb.key("4","KP_4",3,3,null,null,"",KP,"S=&#x25C0;");
    kb.key("5","KP_5",3,3,null,null,"",KP);
    kb.key("6","KP_6",3,3,null,null,"",KP,"S=&#x25B6;");
    kb.endRow();

    // 1 2 3 Enter (Enter is two rows)
    kb.startRow();
    kb.key("1","KP_1",3,3,null,null,"",KP,"S=End");
    kb.key("2","KP_2",3,3,null,null,"",KP,"S=&#x25BC;");
    kb.key("3","KP_3",3,3,null,null,"",KP,"S=Pg Dn");
    kb.key("Enter","KP_ENTER",3,6,null,null,"",KP,"L=","S=Enter","H=132");
    kb.endRow();

    // 0 . (0 is two cols, Enter is two rows)
    kb.startRow();
    kb.key("0","KP_0",6,3,null,null,"",KP,"S=Insert","W=132");
    kb.key(".","KP_DOT",3,3,null,null,"",KP,"S=Del");
    kb.endRow();

    //
    // Keyboard is finished
    //
    kb.endKeyboard();
    kb.endHtml();
%>