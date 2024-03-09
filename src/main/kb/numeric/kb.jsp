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
    kb.key("a");
    kb.endRow();

    //
    // Keyboard is finished
    //
    kb.endKeyboard();
    kb.endHtml();
%>