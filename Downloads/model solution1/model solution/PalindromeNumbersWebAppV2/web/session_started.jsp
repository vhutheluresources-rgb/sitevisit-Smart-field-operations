<%-- 
    Document   : session_started
    Created on : 03 Mar 2024, 5:35:42 PM
    Author     : MemaniV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Session Started Page</title>
    </head>
    <body>
        <h1>Session started</h1>
        <%
            String computerName = (String)pageContext.getServletContext().getInitParameter("computer_name");
            String name = (String)session.getAttribute("name");
        %>
        <p>
            Hi <b><%=name%></b>. My name is <b><%=computerName%></b>. Your session has started. Click <a href="number_entry.html">here</a> to enter a 3-digit number.
        </p>
    </body>
</html>


