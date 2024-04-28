<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.util.RvKybardConfig"%>
<%@ page import="com.rfacad.rvkybard.interfaces.MouseMode"%>
<%
    RvKybardConfig cfg=RvKybardConfig.getConfig();
%>
<html><body><h3>RvKybard Configuration</h3>
<form action="/config" method='POST'>
    <hr>
    <h3>Key click sensitivity:</h3>
    <input type="radio" name='MouseMode' value='MOUSE' <%=(MouseMode.MOUSE==cfg.getMouseMode())?"CHECKED":""%>>
    <label for="MOUSE">Mouse</label>
    <br>
    <input type="radio" name='MouseMode' value='TOUCH' <%=(MouseMode.TOUCH==cfg.getMouseMode())?"CHECKED":""%>>
    <label for="TOUCH">Touchscreen</label>
    <br>
    Touchscreen mode does not work in desktop web browsers.
    Mouse mode does not work well with touchscreens; it causes dropped and stuck keys.
    <hr>
    <button type="SUBMIT">OK</button>
</form>
<a href="/">Cancel</a>
</body></html>
