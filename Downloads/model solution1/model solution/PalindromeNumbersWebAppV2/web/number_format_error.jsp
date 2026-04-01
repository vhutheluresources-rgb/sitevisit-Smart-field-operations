<%-- 
    Document   : number_format_error
    Created on : 08 Mar 2024, 10:35:50 AM
    Author     : MemaniV
--%>

<%@page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Number Format Error Page</title>
    </head>
    <body>
        <h1>Number format error</h1>
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

