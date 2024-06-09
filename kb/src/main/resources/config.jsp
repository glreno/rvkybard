<html>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.rfacad.rvkybard.util.RvKybardConfig"%>
<%@ page import="com.rfacad.rvkybard.interfaces.MouseMode"%>
<%
    RvKybardConfig cfg=RvKybardConfig.getConfig();
%>
<head>
<link
    rel="icon"
    type="image/png"
    href="/icon32.svg"
/>
<link
      rel="stylesheet"
      href="/css/logo.css"
/>
</head>
<body class='logopage'>
<style>
.copyrightfooter {
  position: fixed;
  left: 12px;
  bottom: 12px;
}
</style>
<img class='logo' alt='rrvkb, the retro remote virtual keyboard' src='rrvkb_logo.png' />
<h2>RvKybard Configuration</h2>
<form action="/config" method='POST'>
    <hr>
    <h3>USABILITY</h3>
    <h3>Key click sensitivity:</h3>
    <input type="radio" name='MouseMode' value='MOUSE' <%=(MouseMode.MOUSE==cfg.getMouseMode())?"CHECKED":""%>>
    <label for="MOUSE">Mouse</label>
    <br>
    <input type="radio" name='MouseMode' value='TOUCH' <%=(MouseMode.TOUCH==cfg.getMouseMode())?"CHECKED":""%>>
    <label for="TOUCH">Touchscreen</label>
    <br>
    Touchscreen mode does not work in desktop web browsers.
    <p>
    Mouse mode does not work well with touchscreens; it causes dropped and stuck keys.
    <hr>
    <h3>SECURITY</h3>
    <a href="setpin.html">Set PIN</a>
    <hr>
    <button type="SUBMIT">OK</button>
</form>
<a href="/">Cancel</a>
<footer class='copyrightfooter'>
<br>
rrvkb Copyright &copy; 2024 Gerald Reno, Jr.
</footer>
</body>
</html>
