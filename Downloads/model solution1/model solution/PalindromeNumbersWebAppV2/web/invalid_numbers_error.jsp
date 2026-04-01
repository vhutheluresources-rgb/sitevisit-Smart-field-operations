<%-- 
    Document   : invalid_numbers_error
    Created on : 08 Mar 2024, 10:34:15 AM
    Author     : MemaniV
--%>

<%@page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Invalid Numbers Exception Page</title>
    </head>
    <body>
        <h1>Invalid numbers exception</h1>
        <%
            String errorMsg = exception.getMessage();
        %>
        <p>
            <b>Error message --> <%=errorMsg%></b>
        </p>
        <p>
            Please click <a href="number_entry.html">here</a> to enter another 
            number or <a href="summary.jsp">here</a> to display summary.
        </p>
    </body>
</html>

