<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="java.util.Date" %>
<%
    KybardJspHelper kb=new KybardJspHelper();
    // Default key SVG and size
    kb.setDefaultSvg("key.svgt",66,66,3,3,"FS=48");
    // Custom style settings go here
    kb.addStyle("table","background","green");

    out.println(kb.startHtml());
%>
<!-- custom javascript goes here -->
</script>
<%
    out.println(kb.startKeyboard());
    out.println(kb.startRow());
    out.println(kb.key("a"));
    out.println(kb.endRow());
    out.println(kb.endKeyboard());
    out.println(kb.endHtml());
%>