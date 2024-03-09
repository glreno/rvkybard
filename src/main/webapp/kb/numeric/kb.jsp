<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.KybardJspHelper" %>
<%
    KybardJspHelper kb=new KybardJspHelper(out,"A Keypad",null);
    // Default key SVG and size
    kb.setDefaultSvg("key.svgt",66,66,3,3,"FS=48");
    // Custom style settings go here
    kb.addStyle("table","background","green");

    kb.startHtml();
%>
<script type="text/javascript" language="javascript">
    // custom javascript goes here
</script>
<%
    kb.startKeyboard();
    //
    // Keyboard rows start here
    //

    // Top Row
    kb.startRow();
    kb.key("Numlock","KP_NUMLOCK");
    kb.key("/","KP_DIVIDE");
    kb.key("*","KP_MULTIPLY");
    kb.key("-","KP_MINUS");
    kb.endRow();

    // 7 8 9 + (+ is two rows)
    kb.startRow();
    kb.key("7","KP_7");
    kb.key("8","KP_8");
    kb.key("9","KP_9");
    kb.key("+","KP_ADD",6,3,null,null,"",null,"H=128");
    kb.endRow();

    // 4 5 6 (+ is two rows)
    kb.startRow();
    kb.key("4","KP_4");
    kb.key("5","KP_5");
    kb.key("6","KP_6");
    kb.endRow();

    // 1 2 3 Enter (Enter is two rows)
    kb.startRow();
    kb.key("1","KP_1");
    kb.key("2","KP_2");
    kb.key("3","KP_3");
    kb.key("Enter","KP_ENTER",6,3,null,null,"",null,"H=128");
    kb.endRow();

    // 0 . (0 is two cols, Enter is two rows)
    kb.startRow();
    kb.key("0","KP_0");
    kb.key(".","KP_DOT",3,6,null,null,"",null,"W=128");
    kb.endRow();

    //
    // Keyboard is finished
    //
    kb.endKeyboard();
    kb.endHtml();
%>